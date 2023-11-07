package com.poly.petfoster.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pet {
    
    @Id
    private String petId;

    private String petName;

    @ManyToOne
    @JoinColumn(name = "breed_id")
    @JsonIgnore
    private PetBreed petBreed;

    private Boolean sex;

    private String petColor;

    private String age;

    private Boolean isSpay;

    private Date createAt;

    private Date fosterAt;

    private String descriptions;

    private String adoptStatus;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Imgs> imgs = new ArrayList<>();

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Favorite> favorites = new ArrayList<>();
}
