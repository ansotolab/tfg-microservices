package com.lca.stock.service;

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

    public Material saveMaterial(Material material) {
        return materialRepository.save(material);
    }

    public List<Material> getAll() {
        List<Material> customers = new ArrayList<>();
        materialRepository.findAll().iterator().forEachRemaining(customers::add);
        return customers;
    }
}
