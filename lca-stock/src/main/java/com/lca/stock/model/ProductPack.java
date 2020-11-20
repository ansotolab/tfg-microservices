package com.lca.stock.model;

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
public class ProductPack {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) @NonNull
    private Long id;
    @NotBlank(message = "Quantity is required.") @NonNull
    private Float quantity;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Product parent;

    @ManyToOne
    @JoinColumn(name = "child_id", referencedColumnName = "id")
    private Product child;
}
