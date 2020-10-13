package com.lca.customer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ProductDetail {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Lob
    @NonNull
    @NotBlank
    private String text;

    @JsonIgnoreProperties(value = "detail", allowSetters = true)
    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @NonNull
    private Customer customer;
}
