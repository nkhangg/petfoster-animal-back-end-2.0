package com.poly.petfoster.request.pets;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PetResquest {
    private String name;
    private String color;
    private String isSpay;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fosterAt;
    private String description;
    private String size; // size <=> age
    private Boolean sex;
    private String breed; // => breed id
}
