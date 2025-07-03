package com.product.service.hexagonal.api.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7017412149013436314L;

    private transient Object data;
    private String status;
    private String message;
}
