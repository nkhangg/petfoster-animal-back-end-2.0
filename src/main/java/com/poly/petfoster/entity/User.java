package com.poly.petfoster.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "Users")
public class User {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "user_id")
	private String id;

	private String username;

	private String fullname;

	private Date birthday;

	private Boolean gender;

	private String phone;

	private String address;

	private String avatar;

	private String email;

	@JsonIgnore
	private String password;

	private String role;

	@Column(name = "create_at")
	@CreationTimestamp
	private Date createAt;

	@JsonIgnore
	private Boolean isActive;

	@JsonIgnore
	private Boolean isEmailVerified;

	@JsonIgnore
	private String token;

	@JsonIgnore
	@CreationTimestamp
	private Date tokenCreateAt;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Adopt> adopts = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ShippingInfo> shippingInfos = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Favorite> favorites = new ArrayList<>();

}
