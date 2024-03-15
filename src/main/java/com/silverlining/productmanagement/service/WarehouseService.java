package com.silverlining.productmanagement.service;

import com.silverlining.productmanagement.dto.WarehouseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WarehouseService {

    //List<WarehouseDto> getProductStocks(List<String> serialId);

    int getQuantityBySerialIdAndLocation(String serialId, String location);

    List<WarehouseDto> getProductStock(String serialId);

    List<WarehouseDto> getAllProductStock();

    List<WarehouseDto> getProductStockByLocation(String location);

    WarehouseDto getProductAvailability(String serialId, String location);

    WarehouseDto createProductStock(WarehouseDto warehouseDto);

    WarehouseDto updateProductStock(WarehouseDto warehouseDto);

    WarehouseDto deleteProductStock(String serialId, String location);


}
