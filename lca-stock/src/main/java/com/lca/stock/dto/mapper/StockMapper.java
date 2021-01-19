package com.lca.stock.dto.mapper;

import com.lca.stock.dto.*;
import com.lca.stock.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StockMapper {

    // UNIT TYPE
    UnitTypeDTO unitTypeToDTO(UnitType unitType);

    UnitType dtoToUnitType(UnitTypeDTO unitTypeDTO);

    // UNIT
    UnitDTO unitToDTO(Unit unit);

    Unit dtoToUnit(UnitDTO unitDTO);

    List<UnitDTO> unitToDTO(List<Unit> unit);

    List<Unit> dtoToUnit(List<UnitDTO> unitDTO);

    // MATERIAL
    MaterialDTO materialToDTO(Material material);

    Material dtoToMaterial(MaterialDTO materialDTO);

    List<MaterialDTO> materialToDTO(List<Material> material);

    List<Material> dtoToMaterial(List<MaterialDTO> materialDTO);

    // INPUT
    InputDTO inputToDTO(Input input);

    Input dtoToInput(InputDTO inputDTO);

    // PICKUP
    PickupDTO pickupToDTO(Pickup pickup);

    Pickup dtoToPickup(PickupDTO pickupDTO);

    List<PickupDTO> pickupToDTO(List<Pickup> pickups);

    List<Pickup> dtoToPickup(List<PickupDTO> pickups);

    // SUPPLIER
    @Mapping(source = "pickupList", target = "pickups")
    SupplierDTO supplierToDTO(Supplier supplier);

    @Mapping(source = "pickups", target = "pickupList")
    Supplier dtoToSupplier(SupplierDTO supplierDTO);

    // PRODUCT MATERIAL
    ProductMaterialDTO productMaterialToDTO(ProductMaterial productMaterial);

    ProductMaterial dtoToProductMaterial(ProductMaterialDTO productMaterialDTO);

    // PRODUCT
    ProductDTO productToDTO(Product product);

    Product dtoToProduct(ProductDTO productDTO);

    // OUTPUT
    OutputDTO outputToDTO(Output output);

    Output dtoToOutput(OutputDTO dto);

    // SHIPMENT
    ShipmentDTO shipmentToDTO(Shipment shipment);

    Shipment dtoToShipment(Shipment shipment);
}
