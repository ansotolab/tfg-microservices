package com.lca.stock.conf;

import com.lca.stock.model.Material;
import com.lca.stock.model.Unit;
import com.lca.stock.model.UnitType;
import com.lca.stock.repository.MaterialRepository;
import com.lca.stock.repository.UnitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PopulateDatabase implements InitializingBean {

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private MaterialRepository materialRepository;


    Logger logger = LoggerFactory.getLogger(PopulateDatabase.class);

    @Override
    @Transactional
    public void afterPropertiesSet() throws Exception {
        logger.info("Bootstrapping data...");

        Unit unit = new Unit();
        unit.setId(1L);
        unit.setName("ud");
        unit.setType(UnitType.INT);

        unit = unitRepository.save(unit);

        Material material = new Material();
        material.setId(1L);
        material.setName("Base plástico 1g");
        material.setUnit(unit);

        material = materialRepository.save(material);

        logger.info("...Bootstrapping completed");
    }
}
