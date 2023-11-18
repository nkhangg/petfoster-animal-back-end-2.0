package com.poly.petfoster.controller.admin.orders;

import java.util.Date;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.petfoster.response.ApiResponse;

@Controller
@RequestMapping("/api/admin/orders")
public class OrdersController {

    @GetMapping("")
    public String getOrders(Model model) {

        model.addAttribute("message", "Khang");

        return "orders";
    }

}
