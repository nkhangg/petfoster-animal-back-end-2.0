package com.poly.petfoster.controller.admin.orders;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/admin/orders")
public class OrdersController {

    @GetMapping("")
    public String getOrders(Model model) {

        model.addAttribute("message", "Khang");

        return "orders";
    }

}
