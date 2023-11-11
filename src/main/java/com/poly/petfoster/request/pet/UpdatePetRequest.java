package com.poly.petfoster.request.pet;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.poly.petfoster.constant.RespMessage;
import com.poly.petfoster.entity.PetBreed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePetRequest {

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String petId;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String petName;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private PetBreed petBreed;

    @NotNull(message = RespMessage.NOT_EMPTY)
    private Boolean sex;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String petColor;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String age;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private Boolean isSpay;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fosterAt;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String descriptions;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String adoptStatus;

    private MultipartFile avatar;

}
