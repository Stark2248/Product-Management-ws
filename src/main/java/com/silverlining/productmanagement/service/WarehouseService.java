package com.silverlining.productmanagement.service;

import com.silverlining.productmanagement.dto.WarehouseDto;
import org.springframework.stereotype.Service;

@Service
public interface WarehouseService {

    WarehouseDto getProductAvailability(String serialId);

    WarehouseDto createProductStock(WarehouseDto warehouseDto);

    WarehouseDto updateProductStock(WarehouseDto warehouseDto);

    WarehouseDto deleteProductStock(String serialId);



}
