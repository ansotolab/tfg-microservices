package com.lca.stock.service;

import com.lca.stock.dto.MaterialDTO;
import com.lca.stock.dto.UnitDTO;
import com.lca.stock.dto.UnitTypeDTO;
import com.lca.stock.model.Material;
import com.lca.stock.model.Unit;
import com.lca.stock.model.UnitType;
import com.lca.stock.repository.UnitRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MaterialServiceIntegrationTest {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private UnitRepository unitRepository;

    @Test
    public void shouldSaveMaterialSuccessfully() {
        Unit unit = new Unit();
        unit.setId(1L);
        unit.setName("ud");
        unit.setType(UnitType.INT);

        UnitDTO unitDTO = new UnitDTO();
        unitDTO.setId(1L);
        unitDTO.setName("ud");
        unitDTO.setType(UnitTypeDTO.INT);

        MaterialDTO materialDTO = new MaterialDTO();
        materialDTO.setName("Base plástico 1g");
        materialDTO.setUnit(unitDTO);

        unitRepository.save(unit);
        MaterialDTO savedMaterial = materialService.saveMaterial(materialDTO);

        assertNotNull(savedMaterial);
        assertEquals(savedMaterial.getName(), "Base plástico 1g");
    }
}
