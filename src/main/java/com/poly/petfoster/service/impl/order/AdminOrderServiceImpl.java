package com.poly.petfoster.service.impl.order;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.poly.petfoster.constant.OrderStatus;
import com.poly.petfoster.entity.Orders;
import com.poly.petfoster.entity.Payment;
import com.poly.petfoster.repository.OrdersRepository;
import com.poly.petfoster.repository.PaymentRepository;
import com.poly.petfoster.request.order.UpdateStatusRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.order.AdminOrderService;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public ApiResponse updateOrderStatus(Integer id, UpdateStatusRequest updateStatusRequest) {
        
        Orders order = ordersRepository.findById(id).orElse(null);
        if(order == null) {
            return ApiResponse.builder()
                    .message("order not found")
                    .status(404)
                    .errors("order not found")
                    .build();
        }

        if(order.getStatus().equalsIgnoreCase(OrderStatus.CANCELLED.getValue()) || order.getStatus().equalsIgnoreCase(OrderStatus.DELIVERED.getValue())) {
            return ApiResponse.builder()
                    .message("Cannot update the order has been delivered or cancelled")
                    .status(HttpStatus.FAILED_DEPENDENCY.value())
                    .errors("Cannot update the order has been delivered or cancelled")
                    .build();
        }

        String updateStatus;
        try {
            updateStatus = OrderStatus.valueOf(updateStatusRequest.getStatus().toUpperCase()).getValue();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .message(updateStatusRequest.getStatus() + " doesn't exists in the enum")
                    .status(404)
                    .errors(updateStatusRequest.getStatus() + " doesn't exists in the enum")
                    .build();
        }
        
        if(updateStatus.equalsIgnoreCase(OrderStatus.PLACED.getValue())) {
            return ApiResponse.builder()
                    .message("Cannot update back the status")
                    .status(HttpStatus.CONFLICT.value())
                    .errors("Cannot update back the status")
                    .build();
        }

        order.setStatus(updateStatus);
        ordersRepository.save(order);

        Payment payment = order.getPayment();
        if(updateStatus.equalsIgnoreCase(OrderStatus.DELIVERED.getValue())) {
            if(payment.getPaymentMethod().getId() == 1) {
                payment.setIsPaid(true);
                payment.setPayAt(new Date());
                paymentRepository.save(order.getPayment());
            }
        }

        return ApiResponse.builder()
                .message("Successfully")
                .status(200)
                .errors(false)
                .build();
    }
    
}
