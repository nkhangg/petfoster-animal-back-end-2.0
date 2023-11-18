package com.poly.petfoster.response.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFilter {

    private String username;

    private String fullname;

    private String email;

    private Boolean gender;

    private String phone;

    private String role;

}
