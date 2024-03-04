package com.silverlining.productmanagement.service;

import com.silverlining.productmanagement.dto.WarehouseDto;
import com.silverlining.productmanagement.models.Warehouse;
import com.silverlining.productmanagement.repository.WarehouseRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;

public class WarehouseServiceImpl implements WarehouseService{

    WarehouseRepository warehouseRepository;

    @Autowired
    public WarehouseServiceImpl(WarehouseRepository warehouseRepository){
        this.warehouseRepository=warehouseRepository;
    }

    @Override
    public WarehouseDto getProductAvailability(String serialId) {
        return warehouseRepository.getWharehouseDetailById(serialId);
    }

    @Override
    public WarehouseDto createProductStock(WarehouseDto warehouseDto) {
        ModelMapper mapper=new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        warehouseRepository.save(mapper.map(warehouseDto,Warehouse.class));
        return warehouseDto;
    }

    @Override
    public WarehouseDto updateProductStock(WarehouseDto warehouseDto) {
        ModelMapper mapper=new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        warehouseRepository.save(mapper.map(warehouseDto,Warehouse.class));
        return warehouseDto;
    }

    @Override
    public WarehouseDto deleteProductStock(String serialId) {
        WarehouseDto dto = warehouseRepository.getWharehouseDetailById(serialId);
        if(dto != null){
            warehouseRepository.deleteById(serialId);
            return dto;
        }
        return null;
    }


}
