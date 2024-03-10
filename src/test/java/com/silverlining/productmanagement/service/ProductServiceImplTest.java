package com.silverlining.productmanagement.service;

import com.silverlining.productmanagement.dto.ProductDto;
import com.silverlining.productmanagement.models.Products;
import com.silverlining.productmanagement.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl service;


    @Test
    void getAllProducts() {

        List<Products> productsList =new ArrayList<>();

        Products product = new Products();
        product.setSerialId("Id1");
        product.setName("P1");
        product.setDescription("P1");
        product.setPrice(100);

        productsList.add(product);

        product.setSerialId("Id2");
        product.setName("P2");
        product.setDescription("P2");
        product.setPrice(101);

        productsList.add(product);

        Mockito.when(productRepository.findAll()).thenReturn(productsList);

        List<ProductDto> dtoList = service.getAllProducts();


        Assertions.assertNotNull(dtoList);
        Assertions.assertEquals(2, dtoList.size());

    }

    @Test
    void getProductById() {
        Products product = new Products();
        product.setSerialId("Id1");
        product.setName("P1");
        product.setDescription("P1");
        product.setPrice(100);

        Optional<Products> optionalProduct = Optional.of(product);
        Mockito.when(productRepository.findById("Id1")).thenReturn(optionalProduct);

        ProductDto dto = service.getProductById("Id1");

        Assertions.assertNotNull(dto);
        Assertions.assertEquals("Id1",dto.getSerialId());
        Assertions.assertEquals("P1",dto.getName());
        Assertions.assertEquals("P1",dto.getDescription());
        Assertions.assertEquals(100,dto.getPrice());

        dto = service.getProductById("Id2");

        Assertions.assertNull(dto);
    }

    @Test
    void createProduct() {
        Products product = new Products();
        product.setSerialId("Id1");
        product.setName("P1");
        product.setDescription("P1");
        product.setPrice(100);

        ProductDto productDto = new ProductDto();
        productDto.setName("P1");
        productDto.setDescription("P1");
        productDto.setPrice(120);

        Mockito.when(productRepository.save(Mockito.any(Products.class))).thenReturn(product);

        ProductDto dto = service.createProduct(productDto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals("Id1", dto.getSerialId());

    }

    @Test
    void updateProduct() {

        Products product = new Products();
        product.setSerialId("Id1");
        product.setName("P1");
        product.setDescription("P1");
        product.setPrice(100);
        Products product2 = new Products();
        product2.setSerialId("Id1");
        product2.setName("P2");
        product2.setDescription("P2");
        product2.setPrice(101);

        Optional<Products> optionalProduct = Optional.of(product);

        Mockito.when(productRepository.findById("Id1")).thenReturn(optionalProduct);

        Mockito.when(productRepository.save(Mockito.any(Products.class))).thenReturn(product2);

        ProductDto productDto = new ProductDto();
        productDto.setName("P2");
        productDto.setDescription("P2");
        productDto.setPrice(101);

        ProductDto dto = service.updateProduct("Id1", productDto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(101, dto.getPrice());
        Assertions.assertEquals("Id1", dto.getSerialId());

    }

    @Test
    void deleteProductById() {

        Products product = new Products();
        product.setSerialId("Id1");
        product.setName("P1");
        product.setDescription("P1");
        product.setPrice(100);

        Optional<Products> optionalProduct = Optional.of(product);
        Mockito.when(productRepository.findById("Id1")).thenReturn(optionalProduct);



        Mockito.doAnswer((i)->{
            System.out.println(i.getArguments()[0]+" is getting deleted ");
            return null;
        }).when(productRepository).deleteById("Id1");

        ProductDto dto = service.deleteProductById("Id1");

        Assertions.assertNotNull(dto);
        Assertions.assertEquals("P1",dto.getName());
        Assertions.assertEquals("P1",dto.getDescription());

    }
}