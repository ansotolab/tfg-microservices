package com.lca.stock.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UnitDTO {
    private Long id;

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Type is required.")
    private UnitTypeDTO type;
}
