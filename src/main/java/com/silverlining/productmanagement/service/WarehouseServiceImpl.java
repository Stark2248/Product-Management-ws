package com.silverlining.productmanagement.service;

import com.silverlining.productmanagement.dto.WarehouseDto;
import com.silverlining.productmanagement.models.Products;
import com.silverlining.productmanagement.models.Warehouse;
import com.silverlining.productmanagement.models.WarehouseLocation;
import com.silverlining.productmanagement.repository.ProductRepository;
import com.silverlining.productmanagement.repository.WarehouseRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    private final ProductRepository productRepository;

    @Autowired
    public WarehouseServiceImpl(WarehouseRepository warehouseRepository, ProductRepository productRepository) {
        this.warehouseRepository = warehouseRepository;
        this.productRepository = productRepository;
    }


//    @Override
//    public List<WarehouseDto> getProductStocks(List<String> serialId) {
//        List<WarehouseDto> dtoList = new ArrayList<>();
//        serialId.stream().forEach(serial ->{
//            List<WarehouseDto> productdtos = this.getProductStock(serial);
//            dtoList.addAll(productdtos);
//        });
//        return dtoList;
//    }

    @Override
    public int getQuantityBySerialIdAndLocation(String serialId, String location) {
        WarehouseLocation loc = WarehouseLocation.findByName(location);
        if (loc == null) {
            return -1;
        }
        Optional<Products> productOptional = productRepository.findById(serialId);

        return productOptional.map(product -> {
            WarehouseDto dto = getWarehouseDto(product, loc);
            if(dto == null){
                return -1;
            }
            return dto.getQuantity();
        }).orElse(-1);
    }

    private static WarehouseDto getDto(Warehouse warehouse) {
        WarehouseDto dto = new WarehouseDto();
        dto.setSerialId(warehouse.getProduct().getSerialId());
        dto.setName(warehouse.getProduct().getName());
        dto.setLocation(warehouse.getLocation());
        dto.setQuantity(warehouse.getQuantity());
        return dto;
    }

    @Override
    public List<WarehouseDto> getProductStock(String serialId) {
        Optional<Products> optionalProduct = productRepository.findById(serialId);
        return optionalProduct.map(product -> getWarehouseDtoList(serialId)).orElse(Collections.emptyList());
    }

    private List<WarehouseDto> getWarehouseDtoList(String serialId) {
        List<Warehouse> warehouseList = warehouseRepository.findBySerialId(serialId);

        return warehouseList.stream().map(WarehouseServiceImpl::getDto).toList();

    }

    @Override
    public List<WarehouseDto> getAllProductStock() {
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        return warehouseList.stream().map(WarehouseServiceImpl::getDto).toList();
    }

    @Override
    public List<WarehouseDto> getProductStockByLocation(String location) {
        List<Warehouse> listWarehouseByLocation = warehouseRepository.findByLocation(WarehouseLocation.findByName(location).name());
        if (!CollectionUtils.isEmpty(listWarehouseByLocation)) {
            return listWarehouseByLocation.stream().map(WarehouseServiceImpl::getDto).toList();
        }
        return Collections.emptyList();
    }

    @Override
    public WarehouseDto getProductAvailability(String serialId, String location) {
        WarehouseLocation loc = WarehouseLocation.findByName(location);
        if (loc == null) {
            return null;
        }
        Optional<Products> productOptional = productRepository.findById(serialId);

        return productOptional.map(product -> getWarehouseDto(product, loc))
                              .orElse(null);
    }

    private WarehouseDto getWarehouseDto(Products product, WarehouseLocation loc) {
        Warehouse warehouse = warehouseRepository.findBySerialIdAndLocation(product.getSerialId(), loc.name());
        if (warehouse == null) {
            return null;
        }

        WarehouseDto warehouseDto = new WarehouseDto();


        warehouseDto.setSerialId(warehouse.getProduct().getSerialId());
        warehouseDto.setQuantity(warehouse.getQuantity());
        warehouseDto.setName(product.getName());
        warehouseDto.setLocation(warehouse.getLocation());
        return warehouseDto;
    }

    @Override
    public WarehouseDto createProductStock(WarehouseDto warehouseDto) {

        WarehouseDto dto1 = getProductAvailability(warehouseDto.getSerialId(), WarehouseLocation.findByName(warehouseDto.getLocation()).name());
        if (dto1 != null) {

            return null;
        }

        Optional<Products> optionalProducts = productRepository.findById(warehouseDto.getSerialId());
        if (optionalProducts.isPresent()) {
            Products product = optionalProducts.get();
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            Warehouse warehouse = mapper.map(warehouseDto, Warehouse.class);
            warehouse.setProduct(product);

            warehouse.setLocation(WarehouseLocation.findByName(warehouseDto.getLocation()).name());
            warehouseDto.setName(product.getName());
            warehouseRepository.save(warehouse);
            warehouseDto.setLocation(WarehouseLocation.findByName(warehouseDto.getLocation()).name());
            return warehouseDto;
        }
        return null;

    }

    @Override
    public WarehouseDto updateProductStock(WarehouseDto warehouseDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Optional<Products> optionalProduct = productRepository.findById(warehouseDto.getSerialId());
        if (optionalProduct.isPresent()) {
            warehouseRepository.updateQuantityBySerialIdAndLocation(warehouseDto.getQuantity(),
                    warehouseDto.getSerialId(),
                    WarehouseLocation.findByName(warehouseDto.getLocation()).toString());
            warehouseDto.setName(optionalProduct.get().getName());
        }

        return warehouseDto;
    }

    @Override
    public WarehouseDto deleteProductStock(String serialId, String location) {
        Optional<Products> optionalProduct = productRepository.findById(serialId);
        if (optionalProduct.isPresent()) {
            Warehouse ware = warehouseRepository.findBySerialIdAndLocation(serialId, WarehouseLocation.findByName(location).name());
            if (ware != null) {

                warehouseRepository.deleteBySerialIdAndLocation(serialId, WarehouseLocation.findByName(location).toString());

                WarehouseDto dto = new WarehouseDto();
                dto.setName(optionalProduct.get().getName());
                dto.setQuantity(ware.getQuantity());
                dto.setSerialId(serialId);
                dto.setLocation(WarehouseLocation.findByName(location).name());


                return dto;
            }
        }
        return null;
    }


}
