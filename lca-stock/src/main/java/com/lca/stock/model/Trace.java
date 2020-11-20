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
public class Trace {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) @NonNull
    private Long id;
    @NotBlank(message = "Quantity is required.") @NonNull
    private Float quantity;

    @ManyToOne
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    private Material material;

    @ManyToOne
    @JoinColumn(name = "input_lot_id", referencedColumnName = "id")
    private InputLot inputLot;

    @ManyToOne
    @JoinColumn(name = "output_id", referencedColumnName = "id")
    private Output output;
}
