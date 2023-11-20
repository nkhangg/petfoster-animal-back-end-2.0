package com.poly.petfoster.service.impl.order;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
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

        order.setStatus(updateStatusRequest.getStatus());
        ordersRepository.save(order);
        
        Payment payment = order.getPayment();
        if(updateStatusRequest.getStatus().equalsIgnoreCase(OrderStatus.DELIVERED.getValue())) {
            payment.setIsPaid(true);
            payment.setPayAt(new Date());
            paymentRepository.save(order.getPayment());
        }

        return ApiResponse.builder()
                .message("Successfully")
                .status(200)
                .errors(false)
                .build();
    }
    
}
