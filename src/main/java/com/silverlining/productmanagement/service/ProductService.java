package com.silverlining.productmanagement.service;

import com.silverlining.productmanagement.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    List<ProductDto> getAllProducts();

    ProductDto getProductById(String serialID );

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto);

    ProductDto deleteProductById(String serialId);



}
