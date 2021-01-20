package com.lca.stock.service;

import com.lca.stock.dto.MaterialDTO;
import com.lca.stock.dto.UnitDTO;
import com.lca.stock.dto.UnitTypeDTO;
import com.lca.stock.dto.mapper.StockMapper;
import com.lca.stock.model.Material;
import com.lca.stock.model.Unit;
import com.lca.stock.model.UnitType;
import com.lca.stock.repository.MaterialRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class MaterialServiceTest {

    @Mock
    private MaterialRepository materialRepository;

    @Mock
    private UnitService unitService;

    @Mock
    private StockMapper stockMapper;

    @InjectMocks
    private MaterialService materialService;

    @Test
    public void shouldSaveMaterialSuccessfully() {
        Unit unit = new Unit();
        unit.setName("ud");
        unit.setType(UnitType.INT);

        Material material = new Material();
        material.setName("Base plástico 1g");
        material.setUnit(unit);

        UnitDTO unitDTO = new UnitDTO();
        unitDTO.setId(1L);
        unitDTO.setName("ud");
        unitDTO.setType(UnitTypeDTO.INT);

        MaterialDTO materialDTO = new MaterialDTO();
        materialDTO.setName("Base plástico 1g");
        materialDTO.setUnit(unitDTO);

        given(stockMapper.dtoToMaterial(materialDTO)).willReturn(material);
        given(stockMapper.materialToDTO(material)).willReturn(materialDTO);
        given(unitService.getOne(unitDTO.getId())).willReturn(unitDTO);
        given(materialRepository.save(material)).willAnswer(invocation -> invocation.getArgument(0));

        MaterialDTO savedMaterial = materialService.saveMaterial(materialDTO);

        assertThat(savedMaterial).isNotNull();
    }
}
