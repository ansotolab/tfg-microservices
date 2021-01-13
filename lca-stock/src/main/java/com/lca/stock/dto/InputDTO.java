package com.lca.stock.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class InputDTO {
    private Long id;

    private Float quantity;

    @NotBlank(message = "Quantity is required.")
    private MaterialDTO material;
}
