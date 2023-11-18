package com.poly.petfoster.controller.admin.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.request.order.UpdateStatusRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.order.AdminOrderService;

@RestController
@RequestMapping("/api/admin/orders/")
public class AdminOrderController {
    
    @Autowired
    AdminOrderService adminOrderService;

    @PostMapping("/status/{id}")
    public ResponseEntity<ApiResponse> updateOrderStatus(@PathVariable Integer id, @RequestBody UpdateStatusRequest updateStatusRequest) {
        return ResponseEntity.ok(adminOrderService.updateOrderStatus(id, updateStatusRequest));
    }

}
