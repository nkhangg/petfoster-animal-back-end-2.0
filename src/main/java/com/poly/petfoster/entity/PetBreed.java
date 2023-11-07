package com.poly.petfoster.entity;

import java.util.ArrayList;
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
public class PetBreed {

    @Id
    private String breedId;

    @ManyToOne
    @JoinColumn(name = "type_id")
    @JsonIgnore
    private PetType petType;

    private String breedName;

    @OneToMany(mappedBy = "petBreed", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pet> pets = new ArrayList<>();
}
