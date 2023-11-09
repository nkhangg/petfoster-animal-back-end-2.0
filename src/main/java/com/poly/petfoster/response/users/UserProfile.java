package com.poly.petfoster.response.users;

import java.util.Date;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private String id;
    private String username;
    private String fullname;
    private Date birthday;
    private boolean gender;
    private String phone;
    private String email;
    private String avatar;
    private String role;
    private Date createAt;

}
