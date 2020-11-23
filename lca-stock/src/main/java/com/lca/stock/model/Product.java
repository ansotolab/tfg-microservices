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
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) @NonNull
    private Long id;
    @NotBlank(message = "Name is required.") @NonNull
    private String name;
    @NotBlank(message = "isPack is required.") @NonNull
    private Boolean isPack;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductMaterial> materials;

    @ManyToOne
    @JoinColumn(name = "variant_parent_id", referencedColumnName = "id")
    private Product variantParent;

    @OneToMany(mappedBy = "variantParent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> variants;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductPack> parentsPacks;

    @OneToMany(mappedBy = "child", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductPack> pack;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Output> outputs;
}
