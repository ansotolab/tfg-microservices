package com.lca.stock.dto;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class SupplierDTO {
    private Long id;
    @NotBlank(message = "CIF is required.")
    private String cif;
    @NotBlank(message = "Name is required.")
    private String name;
    @NotBlank(message = "Address is required.")
    private String address;
    @NotBlank(message = "City is required.")
    private String city;
    @NonNull
    private String postalCode;
    @NotBlank(message = "Country is required.")
    private String country;

    private String email;

    private String phone;
    @Lob
    private String people;
    @Lob
    private String observations;

    private List<PickupDTO>  pickups;
}
