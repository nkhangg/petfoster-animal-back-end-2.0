package com.poly.petfoster.ultils;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.petfoster.config.JwtProvider;
import com.poly.petfoster.constant.Constant;
import com.poly.petfoster.entity.Addresses;
import com.poly.petfoster.entity.OrderDetail;
import com.poly.petfoster.entity.Orders;
import com.poly.petfoster.entity.ShippingInfo;
import com.poly.petfoster.entity.Shipping.Province;
import com.poly.petfoster.repository.AddressRepository;
import com.poly.petfoster.repository.OrdersRepository;
import com.poly.petfoster.repository.ShippingInfoRepository;
import com.poly.petfoster.request.order.OrderItem;
import com.poly.petfoster.request.order.OrderRequest;
import com.poly.petfoster.request.shipping.GHNPostRequest;
import com.poly.petfoster.request.shipping.ShippingProductRequest;
import com.poly.petfoster.response.ApiResponse;

import net.minidev.json.JSONArray;

public class GiaoHangNhanhUltils {
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    ShippingInfoRepository shippingInfoRepository;

    public ApiResponse create(Orders orderRequest) {

        // HttpRequest postRequest = HttpRequest.newBuilder()
        // .uri(new URI(Constant.GHN_CREATE))
        // .setHeader("Token", Constant.GHN_TOKEN)
        // // .POST(BodyPublishers.ofString("{\"a\" : \"abc\"}"))
        // .build();
        // HttpClient httpClient = HttpClient.newHttpClient();
        // HttpResponse<String> postResponse =HttpClient.send(postRequest,
        // BodyHandlers.ofString());

        ShippingInfo shippingInfo = orderRequest.getShippingInfo();

        // create a items object
        ArrayList<ShippingProductRequest> list = new ArrayList<>();
        for (OrderDetail item : orderRequest.getOrderDetails()) {
            list.add(ShippingProductRequest.builder()
                    .name(item.getProductRepo().getProduct().getName())
                    .code(item.getProductRepo().getProduct().getId())
                    .quantity(item.getQuantity())
                    .price(item.getPrice().intValue())
                    .build());
        }
        Integer provinceID = getProvinceID(shippingInfo.getProvince());
        System.out.println("Province ID");

        System.out.println(provinceID);
        // create a post object
        GHNPostRequest post = GHNPostRequest.builder()
                .to_name(shippingInfo.getFullName())
                .to_phone(shippingInfo.getPhone())
                .to_address(shippingInfo.getAddress())
                .to_ward_code("20308")
                .to_district_id(1444)
                .cod_amount(orderRequest.getTotal().intValue())
                .items(list)
                .build();

        System.out.println(post);

        // request url
        String url = Constant.GHN_CREATE;

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Token", Constant.GHN_TOKEN);
        headers.set("ShopId", Constant.GHN_SHOPID);

        // build the request
        HttpEntity<GHNPostRequest> request = new HttpEntity<>(post, headers);

        // send POST request
        ResponseEntity<GHNPostRequest> response = restTemplate.postForEntity(url,
        request, GHNPostRequest.class);

        // check response
        if (response.getStatusCode() == HttpStatus.OK) {
        System.out.println("Post Created");
        System.out.println(response.getBody());
        } else {
        System.out.println("Request Failed");
        System.out.println(response.getStatusCode());
        }

        return null;
    }

    public Integer getProvinceID(String provinceName) {
        // request url
        String url = Constant.GHN_GETPROVINCE;

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Token", Constant.GHN_TOKEN);
        headers.set("ShopId", Constant.GHN_SHOPID);

        // create a map for post parameters
        Map<String, Object> map = new HashMap<>();
        map.put("provinceName", provinceName);
        // build the request
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);

        // send POST request
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        ObjectMapper mapper = new ObjectMapper();
        // JsonNode root;
        try {
            //  root = mapper.readTree(response.getBody());
            // String data = root.path("data").toString();

            JSONObject jsonObject = new JSONObject(response);
            // ObjectMapper map2 = new ObjectMapper();
            System.out.println("jsonObject");
            System.out.println(jsonObject);
            List<Province> list = mapper.readValue(jsonObject.getJSONObject("body").getJSONArray("data").toString(), mapper.getTypeFactory().constructCollectionType(List.class, Province.class));
            System.out.println("list");
            System.out.println(list);
            for (Province item : list) {
                if(item.getProvinceName().equals(provinceName)){
                    System.out.println("ID");
                    System.out.println(item.getProvinceID());
                    return item.getProvinceID();
                }
            }
           
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // check response
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Get Province");
            // System.out.println(response.getBody());

            // Province[] listProvince = restTemplate.getForObject(url, Province[].class);
            // System.out.println(listProvince);
            // for (Province item : listProvince) {
            // if(item.getProvinceName().equalsIgnoreCase(provinceName)){
            // return item.getProvinceID();
            // }
            // }
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }

        return null;
    }

}
