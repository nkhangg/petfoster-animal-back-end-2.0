package com.poly.petfoster.response;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserProfile {
    @Id
    private String id;
    private String username;
    private String fullname;
    private String email;
    private String phone;
    private boolean gender;
    private Date birthday;
}
