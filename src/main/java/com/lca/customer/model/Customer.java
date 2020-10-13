package com.lca.customer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "CIF is required.")
    private String cif;
    @NotBlank(message = "Name is required.")
    private String name;
    @NotBlank(message = "Address is required.")
    private String address;
    @NotBlank(message = "City is required.")
    private String city;
    private String postalCode;
    @NotBlank(message = "Country is required.")
    private String country;
    private String email;
    private String phone;
    @Lob
    private String people;
    @Lob
    private String observations;

    @JsonIgnoreProperties("customer")
    @OneToOne
    @JoinColumn(name = "detail_id", referencedColumnName = "id")
    private ProductDetail detail;
}
