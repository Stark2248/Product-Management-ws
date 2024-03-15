package com.silverlining.productmanagement.controller;

import com.silverlining.productmanagement.dto.ProductDto;
import com.silverlining.productmanagement.httpmodels.ProductRequestModel;
import com.silverlining.productmanagement.httpmodels.ProductResponseModel;
import com.silverlining.productmanagement.service.ProductService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService=productService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponseModel>> getAllProducts(){
        List<ProductDto> list = productService.getAllProducts();
        if(list == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
        }
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<ProductResponseModel> retlist = new ArrayList<>();
        list.forEach(productDto ->{
            ProductResponseModel responseModel = mapper.map(productDto,ProductResponseModel.class);
            retlist.add(responseModel);
        });
        return ResponseEntity.status(HttpStatus.OK).body(retlist);
    }

    @PostMapping(value = "/all", consumes ={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ProductResponseModel>> getProductsByIds(@RequestBody List<String> serialIds){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<ProductDto> dtoList = productService.getProductsByIds(serialIds);
        if(dtoList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
        }
        List<ProductResponseModel> responseModelList = new ArrayList<>();
        for(ProductDto dto : dtoList){
            ProductResponseModel responseModel = mapper.map(dto,ProductResponseModel.class);
            responseModelList.add(responseModel);
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(responseModelList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseModel> getProductById(@PathVariable(name = "id") String id){
        ProductDto productDto = productService.getProductById(id);
        if(productDto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ProductResponseModel retVal = mapper.map(productDto,ProductResponseModel.class);

        return ResponseEntity.status(HttpStatus.OK).body(retVal);

    }

    @PostMapping("/product")
    public ResponseEntity<ProductResponseModel> createProduct(@RequestBody ProductRequestModel requestModel){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ProductDto productDto = mapper.map(requestModel,ProductDto.class);
        ProductDto ret = productService.createProduct(productDto);
        ProductResponseModel responseModel = mapper.map(ret,ProductResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseModel> updateProduct(@PathVariable(name = "id") String serialId, @RequestBody ProductRequestModel requestModel){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ProductDto productDto = mapper.map(requestModel,ProductDto.class);
        ProductDto ret= productService.updateProduct(serialId,productDto);
        if(ret==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        ProductResponseModel responseModel = mapper.map(ret,ProductResponseModel.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponseModel> deleteProduct(@PathVariable(name = "id") String serialId){
        ProductDto productDto= productService.deleteProductById(serialId);
        if(productDto==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ProductResponseModel responseModel=mapper.map(productDto,ProductResponseModel.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

}
