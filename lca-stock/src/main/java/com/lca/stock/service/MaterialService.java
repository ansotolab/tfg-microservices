package com.lca.stock.service;

import com.lca.stock.dto.MaterialDTO;
import com.lca.stock.dto.mapper.StockMapper;
import com.lca.stock.model.Material;
import com.lca.stock.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private UnitService unitService;

    @Autowired
    private StockMapper stockMapper;

    public MaterialDTO saveMaterial(MaterialDTO materialDTO) {
        // If unit id doesn't exist throws 404 exception.
        materialDTO.setUnit(unitService.getOne(materialDTO.getUnit().getId()));
        Material material = stockMapper.dtoToMaterial(materialDTO);
        return stockMapper.materialToDTO(materialRepository.save(material));
    }

    public List<MaterialDTO> getAll() {
        List<Material> materials = new ArrayList<>();
        materialRepository.findAll().iterator().forEachRemaining(materials::add);
        return stockMapper.materialToDTO(materials);
    }
}
