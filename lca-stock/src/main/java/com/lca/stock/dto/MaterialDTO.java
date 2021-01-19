package com.lca.stock.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MaterialDTO {
    private Long id;

    @NotBlank(message = "Name is required.")
    private String name;

    private UnitDTO unit;
}
