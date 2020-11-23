package com.lca.stock.controller;

import com.lca.stock.model.Material;
import com.lca.stock.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/materials")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("")
    public List<Material> getCustomers() {
        return materialService.getAll();
    }

    @PostMapping("")
    public Material createCustomer(@Valid @RequestBody Material material) {
        return materialService.saveMaterial(material);
    }
}
