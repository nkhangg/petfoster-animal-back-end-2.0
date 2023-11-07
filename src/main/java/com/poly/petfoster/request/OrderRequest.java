package com.poly.petfoster.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.poly.petfoster.constant.RespMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    
    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String fullname;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String address;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String phone;

    @NotNull(message = RespMessage.NOT_EMPTY)
    private Long shippingFee;

    @Valid
    @NotEmpty(message = RespMessage.NOT_EMPTY)
    List<OrderProduct> orderProducts;

}
