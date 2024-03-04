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

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    WarehouseService warehouseService;
    @Autowired
    public WarehouseController(WarehouseService warehouseService){
        this.warehouseService=warehouseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseResponseModel> getProductAvailability(@PathVariable(name = "id") String id){
        WarehouseDto warehouseDto = warehouseService.getProductAvailability(id);
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
        WarehouseResponseModel responseModel = mapper.map(warehouseService.createProductStock(dto),WarehouseResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }
    @PutMapping("/{id}")
    public ResponseEntity<WarehouseResponseModel> updateStock(@PathVariable(name = "id") String id, @RequestBody WarehouseRequestModel requestModel){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        if(warehouseService.getProductAvailability("id")==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        WarehouseDto dto=mapper.map(requestModel,WarehouseDto.class);
        WarehouseResponseModel responseModel = mapper.map(warehouseService.updateProductStock(dto),WarehouseResponseModel.class);

        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WarehouseResponseModel> deleteStock(@PathVariable(name = "id") String id){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        WarehouseResponseModel responseModel = mapper.map(warehouseService.deleteProductStock(id),WarehouseResponseModel.class);

        if(responseModel == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

}
