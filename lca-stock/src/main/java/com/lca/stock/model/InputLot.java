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
public class InputLot {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) @NonNull
    private Long id;
    @NotBlank(message = "Name is required.") @NonNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private Supplier supplier;

    @OneToMany(mappedBy = "inputLot", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Input> inputs;

    @OneToMany(mappedBy = "inputLot", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Trace> traces;
}
