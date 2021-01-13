package com.lca.stock.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OutputDTO {
    private Long id;
    @NotBlank(message = "Quantity is required.")
    private Float quantity;

    private ProductDTO product;
}
