package com.poly.petfoster.response.pet_details;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetDetail {
    private String id;
    private String breed;
    private String name;
    private Boolean sex;
    private String type;
    private Date fostered;
    private String size;
    private Integer fosterDate;
    private Boolean like;
    private Integer likes;
    private String color;
    private Boolean sterilizated;
    private Boolean vaccinated;
    private String img;
    private String description;
    private List<String> imgs;
}
