package com.poly.petfoster.constant;

public class Constant {

    public static final String CLIENT_URL = "http://localhost:3000/";
    public static final String SECRET_KEY = "dfhdsfjhjdfjsdhfdfhdsfjhjdfjsdhfdfhdsfjhjdfjsdhfdfhdsfjhjdfjsdhfdfhdsfjhjdfjsdhf";
    public static final String JWT_HEADER = "Authorization";
    public static final Integer TOKEN_EXPIRE_LIMIT = 5 * 60 * 1000;
    public static final String BASE_URL = "http://localhost:3000/login/";
    // public static final String BASE_URL = "http://localhost:8019/api/";

    // vn pay
    public static String VNP_PAY_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static String VNP_RETURN_URL = "http://localhost:3000/payment";
    public static String VNP_TMN_CODE = "EWMX8UAJ";
    public static String VNP_SECRET_KEY = "YGFMMZSPJSYYKBXRJPBPOSOEWZUEGKPF";
    public static String VNP_API_ARL = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";
}
