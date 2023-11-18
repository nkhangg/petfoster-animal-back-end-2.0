package com.poly.petfoster.controller.admin.orders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import com.poly.petfoster.response.order_history.OrderDetails;
import com.poly.petfoster.service.order.OrderService;

@Controller
@RequestMapping("/admin/orders")
public class OrdersController {
    @Autowired
    OrderService orderService;

    @GetMapping("")
    public String getOrderTable(Model model) {
        List<OrderDetails> orderDetailsList = orderService.orderDetails_table("All");
        model.addAttribute("list", orderDetailsList);
        System.out.println(orderDetailsList);
        return "orders";
    }
}
