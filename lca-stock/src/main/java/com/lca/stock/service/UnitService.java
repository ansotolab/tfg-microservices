package com.lca.stock.service;

import com.lca.stock.dto.UnitDTO;
import com.lca.stock.dto.mapper.StockMapper;
import com.lca.stock.model.Unit;
import com.lca.stock.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UnitService {

    @Autowired
    public UnitRepository unitRepository;

    @Autowired
    private StockMapper stockMapper;

    public List<UnitDTO> getAll() {
        List<Unit> units = new ArrayList<>();
        unitRepository.findAll().iterator().forEachRemaining(units::add);
        return stockMapper.unitToDTO(units);
    }
}
