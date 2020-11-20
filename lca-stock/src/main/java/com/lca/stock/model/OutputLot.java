package com.lca.stock.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.result.Outputs;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class OutputLot {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) @NonNull
    private Long id;
    @NotBlank(message = "Name is required.") @NonNull
    private String name;

    private Long customerId;

    @OneToMany(mappedBy = "outputLot", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Output> outputs;
}
