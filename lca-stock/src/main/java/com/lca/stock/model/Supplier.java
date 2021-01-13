package com.lca.stock.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Supplier {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) @NonNull
    private Long id;
    @NotBlank(message = "CIF is required.") @NonNull
    private String cif;
    @NotBlank(message = "Name is required.") @NonNull
    private String name;
    @NotBlank(message = "Address is required.") @NonNull
    private String address;
    @NotBlank(message = "City is required.") @NonNull
    private String city;
    @NonNull
    private String postalCode;
    @NotBlank(message = "Country is required.") @NonNull
    private String country;
    @NonNull
    private String email;
    @NonNull
    private String phone;
    @Lob @NonNull
    private String people;
    @Lob
    @NonNull
    private String observations;
}
