package com.poly.petfoster.request.pet;

import java.util.Date;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateaPetRequest {

    private String petId;

    private String petName;

    private Boolean sex;

    private String petColor;

    private String age;

    private Boolean isSpay;

    private Optional<Date> createAt;

    private Optional<Date> fosterAt;

    private String descriptions;

    private String adoptStatus;

    private MultipartFile avatar;

}
