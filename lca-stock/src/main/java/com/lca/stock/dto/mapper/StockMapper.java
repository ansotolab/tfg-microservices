package com.lca.stock.dto.mapper;

import com.lca.stock.dto.*;
import com.lca.stock.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface StockMapper {

    UnitTypeDTO unitTypeToDTO(UnitType unitType);

    UnitDTO unitToDTO(Unit unit);

    MaterialDTO materialToDTO(Material material);

    InputDTO inputToDTO(Input input);

    PickupDTO pickupToDTO(Pickup pickup);

    @Mapping(source = "supplier.pickupList", target = "pickups")
    SupplierDTO supplierToDTO(Supplier supplier);

    ProductMaterialDTO productMaterialToDTO(ProductMaterial productMaterial);

    ProductDTO productToDTO(Product product);

    OutputDTO outputToDTO(Output output);

    ShipmentDTO shipmentToDTO(Shipment shipment);
}
