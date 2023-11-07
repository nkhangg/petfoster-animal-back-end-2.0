package com.poly.petfoster.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product {
    
    @Id
    @Column(name = "product_id")
    private String id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_desc")
    private String desc;

    @ManyToOne
    @JoinColumn(name = "type_id")
    @JsonIgnore
    private ProductType productType;

    private Boolean isActive;

    // brand of product
    private String brand;

    @CreationTimestamp
    @Column(name = "create_at")
    private Date createAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductRepo> productsRepo = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Imgs> imgs = new ArrayList<>();

}

