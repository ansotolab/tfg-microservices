package com.lca.stock.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    @NotBlank(message = "Name is required.")
    private String name;

    private List<ProductMaterialDTO> materials;
}
