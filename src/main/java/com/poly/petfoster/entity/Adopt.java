package com.poly.petfoster.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Adopt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adoptId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    private Date registerAt;
    @Nationalized

    private String status;

    private Date adoptAt;
}