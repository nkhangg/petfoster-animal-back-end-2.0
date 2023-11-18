package com.poly.petfoster.service.impl.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.poly.petfoster.entity.Orders;
import com.poly.petfoster.repository.OrdersRepository;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.order_history.OrderFilter;
import com.poly.petfoster.service.admin.order.OrderFilterService;
import com.poly.petfoster.ultils.FormatUtils;

@Service
public class OrderFilterServiceImpl implements OrderFilterService {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    FormatUtils formatUtils;

    @Override
    public ApiResponse filterOrders(Optional<String> username, Optional<Integer> orderId, Optional<String> status,
            Optional<Date> minDate, Optional<Date> maxDate,  Optional<String> sort) {
        
        Date minDateValue = minDate.orElse(null);
        Date maxDateValue = maxDate.orElse(null);

        if (minDateValue == null && maxDateValue != null) {
            minDateValue = maxDateValue;
        }

        if (maxDateValue == null && minDateValue != null) {
            maxDateValue = minDateValue;
        }
        
        if(minDateValue != null && maxDateValue != null) {
            if(minDateValue.after(maxDateValue)) {
                return ApiResponse.builder()
                .message("The max date must after the min date!!!")
                .status(HttpStatus.CONFLICT.value())
                .errors("The max date must after the min date!!!")
                .build();
            }
        }

        List<Orders> orders = ordersRepository.filterOrders(username.orElse(null), orderId.orElse(null), status.orElse(""), minDateValue, maxDateValue, sort.orElse(null));
        
        List<OrderFilter> orderFilters = new ArrayList<>();

        orders.forEach(order -> {
            orderFilters.add(
                OrderFilter.builder()
                .orderId(order.getId())
                .username(order.getUser().getUsername())
                .total(order.getTotal().intValue())
                .placedDate(formatUtils.dateToString(order.getCreateAt(), "yyyy-MM-dd"))
                .status(order.getStatus())
                .build()
            );
        });

        return ApiResponse.builder()
        .message("Successfully!!!")
        .status(200)
        .errors(false)
        .data(orderFilters)
        .build();
    }
    
}
