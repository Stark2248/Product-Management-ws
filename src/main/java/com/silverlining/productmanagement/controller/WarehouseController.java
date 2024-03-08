package com.silverlining.productmanagement.controller;


import com.silverlining.productmanagement.dto.WarehouseDto;
import com.silverlining.productmanagement.httpmodels.WarehouseRequestModel;
import com.silverlining.productmanagement.httpmodels.WarehouseResponseModel;
import com.silverlining.productmanagement.service.WarehouseService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    WarehouseService warehouseService;
    @Autowired
    public WarehouseController(WarehouseService warehouseService){
        this.warehouseService=warehouseService;
    }

    @GetMapping("/stocks")
    public ResponseEntity<List<WarehouseResponseModel>> getAllProductStocks(){
        List<WarehouseDto> dtoList = warehouseService.getAllProductStock();
        if(dtoList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
        }
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<WarehouseResponseModel> responseModelList = new ArrayList<>();
        for(WarehouseDto dto: dtoList){
            WarehouseResponseModel responseModel = mapper.map(dto, WarehouseResponseModel.class);
            responseModelList.add(responseModel);
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(responseModelList);
    }

    @GetMapping("/{location}")
    public ResponseEntity<List<WarehouseResponseModel>> getProductAvailabilityByLocation(@PathVariable(name = "location") String location){
        List<WarehouseDto> warehouseDtoList = warehouseService.getProductStockByLocation(location);
        if(warehouseDtoList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
        }
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<WarehouseResponseModel> responseModelList = new ArrayList<>();
        for(WarehouseDto dto: warehouseDtoList){
            WarehouseResponseModel responseModel = mapper.map(dto, WarehouseResponseModel.class);
            responseModelList.add(responseModel);
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(responseModelList);

    }

    @GetMapping("/{location}/{id}")
    public ResponseEntity<WarehouseResponseModel> getProductAvailability(@PathVariable(name = "id") String id,
                                                                         @PathVariable(name = "location") String location) {
        WarehouseDto warehouseDto = warehouseService.getProductAvailability(id, location);
        if(warehouseDto==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        WarehouseResponseModel responseModel = mapper.map(warehouseDto,WarehouseResponseModel.class);

        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @PostMapping("/stock")
    public ResponseEntity<WarehouseResponseModel> createStock(@RequestBody WarehouseRequestModel requestModel){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        WarehouseDto dto=mapper.map(requestModel,WarehouseDto.class);



        WarehouseDto warehouseDto = warehouseService.createProductStock(dto);

        if(warehouseDto.getName()=="Already Exist"){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        WarehouseResponseModel responseModel = mapper.map(warehouseDto,
                WarehouseResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseModel);
    }
    @PutMapping("/{location}/{id}")
    public ResponseEntity<WarehouseResponseModel> updateStock(@PathVariable(name = "id") String id,
                                                              @PathVariable(name = "location") String location,
                                                              @RequestBody WarehouseRequestModel requestModel){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        if(warehouseService.getProductAvailability(id,location)==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        WarehouseDto dto=mapper.map(requestModel,WarehouseDto.class);
        dto.setSerialId(id);
        dto.setLocation(location);
        WarehouseResponseModel responseModel = mapper.map(warehouseService.updateProductStock(dto), WarehouseResponseModel.class);

        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @DeleteMapping("/{location}/{id}")
    public ResponseEntity<WarehouseResponseModel> deleteStock(@PathVariable(name = "id") String id,
                                                              @PathVariable(name = "location") String location){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        WarehouseResponseModel responseModel = mapper.map(warehouseService.deleteProductStock(id,location),WarehouseResponseModel.class);

        if(responseModel == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

}
