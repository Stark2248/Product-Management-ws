package com.silverlining.productmanagement.service;


import com.silverlining.productmanagement.dto.ProductDto;
import com.silverlining.productmanagement.models.Products;
import com.silverlining.productmanagement.repository.ProductRepository;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;

    @Autowired
    ProductServiceImpl(ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<ProductDto> retList=new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        for (Products a : productRepository.findAll()) {
            ProductDto p = mapper.map(a, ProductDto.class);
            retList.add(p);
        }
        if(retList.isEmpty())
            return null;
        return retList;
    }

    @Override
    public ProductDto getProductById(String serialID) {

        Optional<Products> optionalProduct=productRepository.findById(serialID);
        if(optionalProduct.isPresent()){
            Products product = optionalProduct.get();
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            return mapper.map(product,ProductDto.class);
        }

        return null;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Products product = mapper.map(productDto,Products.class);

        String id;
        if(StringUtils.isEmpty(product.getSerialId())){
            id= UUID.randomUUID().toString();
            product.setSerialId(id);
            productDto.setSerialId(id);
        }

        Products save = productRepository.save(product);

        return mapper.map(save,ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(String serialId ,ProductDto productDto) {


        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Optional<Products> optionalProduct=productRepository.findById(serialId);
        if(optionalProduct.isPresent()){
            Products product = mapper.map(productDto,Products.class);
            product.setSerialId(serialId);
            Products save = productRepository.save(product);

            return mapper.map(save,ProductDto.class);
        }

        return null;
    }

    @Override
    public ProductDto deleteProductById(String serialId) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Optional<Products> optionalProduct = productRepository.findById(serialId);
        if(optionalProduct.isPresent()){
            ProductDto retVal=mapper.map(optionalProduct.get(),ProductDto.class);
            productRepository.deleteById(serialId);
            return retVal;
        }
        return null;
    }
}
