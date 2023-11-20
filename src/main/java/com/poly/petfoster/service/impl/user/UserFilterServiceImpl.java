package com.poly.petfoster.service.impl.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.poly.petfoster.entity.Orders;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.repository.OrdersRepository;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.order_history.OrderFilter;
import com.poly.petfoster.response.users.UserFilter;
import com.poly.petfoster.service.admin.order.OrderFilterService;
import com.poly.petfoster.service.admin.user.UserFilterService;
import com.poly.petfoster.service.user.UserService;
import com.poly.petfoster.ultils.FormatUtils;

@Service
public class UserFilterServiceImpl implements UserFilterService {

        @Autowired
        UserRepository userRepository;

        @Override
        public ApiResponse filterUsers(Optional<String> username, Optional<String> fullname, Optional<String> email,
                        Optional<String> gender, Optional<String> phone, Optional<String> role) {

                List<User> users = userRepository.filterUsers(username.orElse(null), fullname.orElse(null),
                                email.orElse(null), gender.orElse(null), phone.orElse(null), role.orElse(null));

                List<UserFilter> userFilters = new ArrayList<>();

                users.forEach(user -> {
                        userFilters.add(
                                        UserFilter.builder().username(user.getUsername())
                                                        .fullname(user.getFullname())
                                                        .email(user.getEmail())
                                                        .gender(user.getGender())
                                                        .phone(user.getPhone())
                                                        .role(user.getAuthorities().get(0).getRole().getRole())
                                                        .build());
                });

                return ApiResponse.builder()
                                .message("Successfully!!!")
                                .status(200)
                                .errors(false)
                                .data(userFilters)
                                .build();
        }

}
