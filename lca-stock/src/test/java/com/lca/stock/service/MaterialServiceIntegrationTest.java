package com.lca.stock.service;

import com.lca.stock.dto.MaterialDTO;
import com.lca.stock.dto.UnitDTO;
import com.lca.stock.dto.UnitTypeDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MaterialServiceIntegrationTest {

    @Autowired
    private MaterialService materialService;

    @Test
    public void shouldSaveMaterialSuccessfully() {

        UnitDTO unitDTO = new UnitDTO();
        unitDTO.setId(1L);
        unitDTO.setName("ud");
        unitDTO.setType(UnitTypeDTO.INT);

        MaterialDTO materialDTO = new MaterialDTO();
        materialDTO.setName("Base plástico 2g");
        materialDTO.setUnit(unitDTO);

        MaterialDTO savedMaterial = materialService.saveMaterial(materialDTO);

        assertNotNull(savedMaterial);
        assertEquals("Base plástico 2g", savedMaterial.getName());
    }

    @Test
    public void shouldGetAllSuccessfully() {

        List<MaterialDTO> queryMaterial = materialService.getAll();
        MaterialDTO first = queryMaterial.get(0);

        assertNotNull(queryMaterial);
        assertEquals(1, queryMaterial.size());

        assertEquals(1L, first.getId());
        assertEquals("Base plástico 1g", first.getName());
        assertEquals("ud", first.getUnit().getName());

    }
}
