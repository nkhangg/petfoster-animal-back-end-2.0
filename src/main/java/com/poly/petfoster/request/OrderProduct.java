package com.poly.petfoster.request;

import javax.validation.constraints.NotBlank;
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
public class OrderProduct {
    
    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String productId;

    @NotNull(message = RespMessage.NOT_EMPTY)
    private Integer size;

    @NotNull(message = RespMessage.NOT_EMPTY)
    private Integer quantity;

}
