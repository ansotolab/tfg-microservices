package com.lca.customer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Customer {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY) @NonNull
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
    @Lob @NonNull
    private String observations;

    @JsonIgnoreProperties("customer")
    @OneToOne
    @JoinColumn(name = "detail_id", referencedColumnName = "id")
    private ProductDetail detail;
}
