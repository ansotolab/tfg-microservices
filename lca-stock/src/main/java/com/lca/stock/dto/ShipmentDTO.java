package com.lca.stock.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
public class ShipmentDTO {
    private Long id;
    @NotBlank(message = "Date is required.")
    private Date date;

    private List<OutputDTO> outputs;

    private CustomerDTO customer;
}
