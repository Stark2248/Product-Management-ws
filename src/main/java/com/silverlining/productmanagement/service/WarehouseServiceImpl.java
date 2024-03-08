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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class WarehouseServiceImpl implements WarehouseService{

    private final WarehouseRepository warehouseRepository;

    private final ProductRepository productRepository;

    @Autowired
    public WarehouseServiceImpl(WarehouseRepository warehouseRepository,ProductRepository productRepository){
        this.warehouseRepository=warehouseRepository;
        this.productRepository=productRepository;
    }


    @Override
    public List<WarehouseDto> getAllProductStock() {
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        List<WarehouseDto> dtoList = new ArrayList<>();
        for(Warehouse warehouse: warehouseList){
            WarehouseDto dto = new WarehouseDto();
            dto.setSerialId(warehouse.getProduct().getSerialId());
            dto.setLocation(warehouse.getLocation());
            dto.setQuantity(warehouse.getQuantity());
            dto.setName(warehouse.getProduct().getName());
            dtoList.add(dto);

        }
        return dtoList;
    }

    @Override
    public List<WarehouseDto> getProductStockByLocation(String location) {
        List<Warehouse> listWarehouseByLocation = warehouseRepository.findByLocation(WarehouseLocation.findByName(location).name());
        if(listWarehouseByLocation != null){
            List<WarehouseDto> listDto = new ArrayList<>();
            for(Warehouse warehouse:listWarehouseByLocation){
                WarehouseDto dto= new WarehouseDto();
                dto.setName(warehouse.getProduct().getName());
                dto.setLocation(warehouse.getLocation().toString());
                dto.setQuantity(warehouse.getQuantity());
                listDto.add(dto);

            }
            return listDto;
        }
        return Collections.emptyList();
    }

    @Override
    public WarehouseDto getProductAvailability(String serialId, String location) {
        WarehouseLocation loc = WarehouseLocation.findByName(location);
        if(loc == null){
            WarehouseDto dto = new WarehouseDto();
            dto.setName("Wrong Location!!");
            return dto;
        }
        Optional<Products> productOptional = productRepository.findById(serialId);
        if(productOptional.isPresent()){
            Products product = productOptional.get();

            Warehouse warehouse = warehouseRepository.findBySerialIdAndLocation(product.getSerialId(),loc.name());
            if(warehouse == null) {
                return null;
            }

            WarehouseDto warehouseDto = new WarehouseDto();


            warehouseDto.setSerialId(warehouse.getProduct().getSerialId());
            warehouseDto.setQuantity(warehouse.getQuantity());
            warehouseDto.setName(product.getName());
            warehouseDto.setLocation(warehouse.getLocation());

            return warehouseDto;
        }
        return null;
    }

    @Override
    public WarehouseDto createProductStock(WarehouseDto warehouseDto) {

        WarehouseDto dto1 = getProductAvailability(warehouseDto.getSerialId(),WarehouseLocation.findByName(warehouseDto.getLocation()).name());
        if(dto1!=null){
            dto1.setName("Already Exist");
            return dto1;
        }

        Optional<Products> productopt = productRepository.findById(warehouseDto.getSerialId());
        if(productopt.isPresent()){
            Products product =  productopt.get();
            ModelMapper mapper=new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            Warehouse warehouse=mapper.map(warehouseDto,Warehouse.class);
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
        ModelMapper mapper=new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Optional<Products> optionalProduct = productRepository.findById(warehouseDto.getSerialId());
        if(optionalProduct.isPresent()){
            warehouseRepository.updateQuantityBySerialIdAndLocation(warehouseDto.getQuantity(),
                    warehouseDto.getSerialId(),
                    WarehouseLocation.findByName(warehouseDto.getLocation()).toString());
            warehouseDto.setName(optionalProduct.get().getName());
        }

        return warehouseDto;
    }

    @Override
    public WarehouseDto deleteProductStock(String serialId, String location) {
        Optional<Products> optionalProduct= productRepository.findById(serialId);
        if( optionalProduct.isPresent()) {
            Warehouse ware = warehouseRepository.findBySerialIdAndLocation(serialId,WarehouseLocation.findByName(location).name());
            if(ware!=null){

                warehouseRepository.deleteBySerialIdAndLocation(serialId,WarehouseLocation.findByName(location).toString());

                WarehouseDto dto = new WarehouseDto();
                dto.setName(optionalProduct.get().getName());
                dto.setQuantity(ware.getQuantity());
                dto.setSerialId(serialId);


                return dto;
            }
        }
        return null;
    }


}
