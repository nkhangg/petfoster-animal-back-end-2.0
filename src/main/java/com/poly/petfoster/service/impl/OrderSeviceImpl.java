package com.poly.petfoster.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.poly.petfoster.config.JwtProvider;
import com.poly.petfoster.constant.PatternExpression;
import com.poly.petfoster.constant.RespMessage;
import com.poly.petfoster.entity.OrderDetail;
import com.poly.petfoster.entity.Orders;
import com.poly.petfoster.entity.Product;
import com.poly.petfoster.entity.ProductRepo;
import com.poly.petfoster.entity.ShippingInfo;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.repository.OrderDetailRepository;
import com.poly.petfoster.repository.OrdersRepository;
import com.poly.petfoster.repository.ProductRepoRepository;
import com.poly.petfoster.repository.ProductRepository;
import com.poly.petfoster.repository.ShippingInfoRepository;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.request.OrderProduct;
import com.poly.petfoster.request.OrderRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.order_history.OrderHistory;
import com.poly.petfoster.response.order_history.OrderHistoryResponse;
import com.poly.petfoster.response.order_history.OrderProductItem;
import com.poly.petfoster.service.OrderService;
import com.poly.petfoster.ultils.FormatUtils;
import com.poly.petfoster.ultils.PortUltil;

@Service
public class OrderSeviceImpl implements OrderService {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PortUltil portUltil;

    @Autowired
    FormatUtils formatUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShippingInfoRepository shippingInfoRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductRepoRepository productRepoRepository;

    @Autowired
    TakeActionServiceImpl takeActionServiceImpl;

//     @Override
//     public ApiResponse createOrder(String jwt, OrderRequest orderRequest) {

//         Map<String, String> errorsMap = new HashMap<>();
//         User user = userRepository.findByUsername(jwtProvider.getUsernameFromToken(jwt)).orElse(null);

//         if (user == null) {
//             errorsMap.put("user", "user not found");
//             return ApiResponse.builder()
//                     .message("Unauthenrized")
//                     .status(HttpStatus.UNAUTHORIZED.value())
//                     .errors(errorsMap).build();
//         }

//         if (PatternExpression.NOT_SPECIAL_SPACE.matcher(orderRequest.getFullname()).find()) {
//             errorsMap.put("fullname", "full name must not contains special characters!");
//             return ApiResponse.builder()
//                     .message(HttpStatus.BAD_REQUEST.toString())
//                     .errors(errorsMap)
//                     .build();
//         }

//         if (orderRequest.getShippingFee() < 0) {
//             errorsMap.put("shippingFee", "Shipping fee must larger than 0");
//             return ApiResponse.builder()
//                     .message(HttpStatus.BAD_REQUEST.toString())
//                     .errors(errorsMap)
//                     .build();
//         }

//         if (!PatternExpression.IS_PHONE_VALID.matcher(orderRequest.getPhone()).matches()) {
//             errorsMap.put("phone", "phone number must start with 0 and has 10 digits!");
//             return ApiResponse.builder()
//                     .message(HttpStatus.BAD_REQUEST.toString())
//                     .errors(errorsMap)
//                     .build();
//         }

//         ShippingInfo shippingInfo = shippingInfoRepository.save(this.createShippingInfo(user, orderRequest));

//         Orders order = Orders.builder()
//                 .shippingInfo(shippingInfo)
//                 .build();
//         ordersRepository.save(order);

//         List<OrderDetail> orderDetails = new ArrayList<>();
//         Double total = 0.0;
//         for (OrderProduct orderProduct : orderRequest.getOrderProducts()) {

//             if (orderProduct.getQuantity() < 0) {
//                 errorsMap.put("quantity", "quantity must larger than 0");
//                 return ApiResponse.builder()
//                         .message(HttpStatus.BAD_REQUEST.toString())
//                         .errors(errorsMap)
//                         .build();
//             }

//             Product product = productRepository.findById(orderProduct.getProductId()).orElse(null);
//             ProductRepo productRepo = productRepoRepository.findProductRepoByIdAndSize(orderProduct.getProductId(),
//                     orderProduct.getSize());

//             if (productRepo.getQuantity() < orderProduct.getQuantity()) {
//                 errorsMap.put("quantity", "quantity are not enought, please try another one!!!");
//                 return ApiResponse.builder()
//                         .message(HttpStatus.BAD_REQUEST.toString())
//                         .errors(errorsMap)
//                         .build();
//             }

//             OrderDetail orderDetail = this.createOrderDetail(order, orderProduct);
//             orderDetails.add(orderDetail);

//             // ProductRepo productRepo =
//             // productRepoRepository.findProductRepoByIdAndSize(orderDetail.getProduct().getId(),
//             // orderDetail.getSize());

//             productRepo.setQuantity(productRepo.getQuantity() -
//                     orderDetail.getQuantity());
//             productRepoRepository.save(productRepo);

//             total += orderDetail.getTotal();
//         }

//         order.setTotal(total);
//         order.setOrderDetails(orderDetails);
//         order.setStatus("Placed");
//         ordersRepository.save(order);

//         return ApiResponse.builder()
//                 .message("order successfuly!!!")
//                 .status(200)
//                 .errors(false)
//                 .data(orderDetails).build();
//     }

//     public ShippingInfo createShippingInfo(User user, OrderRequest orderRequest) {

//         return ShippingInfo.builder()
//                 .user(user)
//                 .fullName(orderRequest.getFullname())
//                 .address(orderRequest.getAddress())
//                 .phone(orderRequest.getPhone())
//                 .shipFee(orderRequest.getShippingFee()).build();

//     }

//     public OrderDetail createOrderDetail(Orders order, OrderProduct orderProduct) {

//         Product product = productRepository.findById(orderProduct.getProductId()).orElse(null);
//         ProductRepo productRepo = productRepoRepository.findProductRepoByIdAndSize(orderProduct.getProductId(),
//                 orderProduct.getSize());

//         OrderDetail orderDetail = orderDetailRepository.save(
//                 OrderDetail.builder()
//                         .order(order)
//                         .product(product)
//                         .size(orderProduct.getSize())
//                         .quantity(orderProduct.getQuantity())
//                         .total(orderProduct.getQuantity() * productRepo.getOutPrice())
//                         .build());

//         return orderDetail;
//     }

    @Override
    public ApiResponse orderHistory(String jwt, Optional<Integer> page) {

        User user = userRepository.findByUsername(jwtProvider.getUsernameFromToken(jwt)).orElse(null);

        if (user == null) {
            return ApiResponse.builder()
                    .message("Invalid Token!!!")
                    .status(400)
                    .errors("Invalid token")
                    .build();
        }

        List<Orders> ordersHistory = ordersRepository.orderHistory(user.getId());

        if (ordersHistory.isEmpty()) {
            return ApiResponse.builder().message("No data available").status(200).errors(false).data(ordersHistory)
                    .build();
        }

        List<OrderHistory> data = new ArrayList<>();

        for (Orders order : ordersHistory) {

            List<OrderDetail> orderDetails = order.getOrderDetails();
            List<OrderProductItem> products = new ArrayList<>();

            for (OrderDetail orderDetail : orderDetails) {
                products.add(createOrderProductItem(orderDetail));
            }

            OrderHistory orderHistory = OrderHistory.builder()
                    .id(order.getId())
                    .datePlace(formatUtils.dateToString(order.getCreateAt()))
                    .state(order.getStatus())
                    .stateMessage(order.getStatus())
                    .total(order.getTotal())
                    .products(products)
                    .build();

            data.add(orderHistory);
        }

        Pageable pageable = PageRequest.of(page.orElse(0), 3);
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), data.size());

        if (startIndex >= endIndex) {
            return ApiResponse.builder()
                    .message(RespMessage.NOT_FOUND.getValue())
                    .data(null)
                    .errors(true)
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        List<OrderHistory> visibleProducts = data.subList(startIndex, endIndex);
        Page<OrderHistory> pagination = new PageImpl<OrderHistory>(visibleProducts, pageable, data.size());

        return ApiResponse.builder()
                .message("Successfully")
                .status(200).errors(false).data(OrderHistoryResponse.builder().data(pagination.getContent())
                        .pages(pagination.getTotalPages()).build())
                .build();
    }

    public OrderProductItem createOrderProductItem(OrderDetail orderDetail) {
        String image = "";
       
        if (!orderDetail.getProductRepo().getProduct().getImgs().isEmpty()) {
            image = orderDetail.getProductRepo().getProduct().getImgs().get(0).getNameImg();
        }

        return OrderProductItem
                .builder()
                .id(orderDetail.getProductRepo().getProduct().getId())
                .size(orderDetail.getProductRepo().getSize())
                .image(portUltil.getUrlImage(image))
                .name(orderDetail.getProductRepo().getProduct().getName())
                .brand(orderDetail.getProductRepo().getProduct().getBrand().getBrand())
                .price(orderDetail.getProductRepo().getOutPrice().intValue())
                .quantity(orderDetail.getQuantity())
                .repo(orderDetail.getProductRepo().getQuantity())
                .build();
    }

}
