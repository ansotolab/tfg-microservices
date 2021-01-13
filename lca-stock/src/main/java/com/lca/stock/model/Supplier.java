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
    @NonNull
    private String cif;
    @NonNull
    private String name;
    @NonNull
    private String address;
    @NonNull
    private String city;
    @NonNull
    private String postalCode;
    @NonNull
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

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pickup> pickupList;
}
