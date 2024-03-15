package com.silverlining.productmanagement.service;

import com.silverlining.productmanagement.dto.WarehouseDto;
import com.silverlining.productmanagement.models.Products;
import com.silverlining.productmanagement.models.Warehouse;
import com.silverlining.productmanagement.repository.ProductRepository;
import com.silverlining.productmanagement.repository.WarehouseRepository;
import net.bytebuddy.dynamic.DynamicType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;



@ExtendWith(MockitoExtension.class)
class WarehouseServiceImplTest {

    @Mock
    WarehouseRepository warehouseRepository;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    WarehouseServiceImpl service;

    @Test
    void getAllProductStock() {

        Products product = new Products();
        product.setSerialId("Id1");
        product.setName("P1");
        product.setDescription("P1");
        product.setPrice(100);

        Warehouse warehouse= new Warehouse(product, 15,"BANGALORE");
        List<Warehouse> warehouseList = new ArrayList<>();
        warehouseList.add(warehouse);
        warehouse = new Warehouse(product, 20,"MUMBAI");
        warehouseList.add(warehouse);

        Mockito.when(warehouseRepository.findAll()).thenReturn(warehouseList);

        List<WarehouseDto> dtoList = service.getAllProductStock();

        Assertions.assertEquals(2,dtoList.size());
        Assertions.assertNotNull(dtoList);


    }

    @Test
    void getProductStockByLocation() {

        Products product = new Products();
        product.setSerialId("Id1");
        product.setName("P1");
        product.setDescription("P1");
        product.setPrice(100);

        Warehouse warehouse= new Warehouse(product, 15,"BANGALORE");
        List<Warehouse> warehouseList = new ArrayList<>();
        warehouseList.add(warehouse);
        product.setSerialId("Id2");
        product.setName("P2");
        product.setDescription("P2");
        product.setPrice(150);
        warehouse = new Warehouse(product, 20,"BANGALORE");
        warehouseList.add(warehouse);

        Mockito.when(warehouseRepository.findByLocation("BANGALORE")).thenReturn(warehouseList);

        List<WarehouseDto> dtoList = service.getProductStockByLocation("BANGALORE");

        Assertions.assertNotNull(dtoList);
        Assertions.assertEquals(2,dtoList.size());


    }

    @Test
    void getProductAvailability() {

        Products product = new Products();
        product.setSerialId("Id1");
        product.setName("P1");
        product.setDescription("P1");
        product.setPrice(100);

        Warehouse warehouse= new Warehouse(product, 15,"BANGALORE");

        Optional<Products> optionalProduct = Optional.of(product);

        Mockito.when(productRepository.findById("Id1")).thenReturn(optionalProduct);


        Mockito.when(warehouseRepository.findBySerialIdAndLocation("Id1","BANGALORE")).thenReturn(warehouse);

        WarehouseDto dto = service.getProductAvailability("Id1","BANGALORE");

        Assertions.assertNotNull(dto);
        Assertions.assertEquals("Id1",dto.getSerialId());
        Assertions.assertEquals("BANGALORE",dto.getLocation());
        Assertions.assertEquals(15,dto.getQuantity());
        Assertions.assertEquals("P1",dto.getName());


    }

    @Test
    void createProductStock() {
        Products product = new Products();
        product.setSerialId("Id1");
        product.setName("P1");
        product.setDescription("P1");
        product.setPrice(100);

        Warehouse warehouse= new Warehouse(product, 15,"BANGALORE");

        Optional<Products> optionalProduct = Optional.of(product);

        Mockito.when(productRepository.findById("Id1")).thenReturn(optionalProduct);

        Mockito.when(warehouseRepository.save(Mockito.any(Warehouse.class))).thenReturn(warehouse);

        WarehouseDto dto = new WarehouseDto("Id1", "", 15, "BANGALORE");

        WarehouseDto returnedValue = service.createProductStock(dto);

        Assertions.assertNotNull(returnedValue);
        Assertions.assertEquals("P1", returnedValue.getName());


    }

    @Test
    void updateProductStock() {

        Products product = new Products();
        product.setSerialId("Id1");
        product.setName("P1");
        product.setDescription("P1");
        product.setPrice(100);


        Optional<Products> optionalProduct = Optional.of(product);
        Mockito.when(productRepository.findById("Id1")).thenReturn(optionalProduct);

        Mockito.doAnswer((i)->{
            System.out.println(i.getArguments()[0]+"is updated");
            return null;
        }).when(warehouseRepository).updateQuantityBySerialIdAndLocation(20,"Id1","BANGALORE");

        WarehouseDto dto = new WarehouseDto("Id1","",20,"BANGALORE");

        WarehouseDto returnValue = service.updateProductStock(dto);

        Assertions.assertNotNull(returnValue);
        Assertions.assertEquals("P1", returnValue.getName());
        Assertions.assertEquals(20, returnValue.getQuantity());

    }

    @Test
    void deleteProductStock() {

        Products product = new Products();
        product.setSerialId("Id1");
        product.setName("P1");
        product.setDescription("P1");
        product.setPrice(100);

        Warehouse warehouse= new Warehouse(product, 15,"BANGALORE");
        Mockito.when(warehouseRepository.findBySerialIdAndLocation("Id1","BANGALORE")).thenReturn(warehouse);


        Optional<Products> optionalProduct = Optional.of(product);
        Mockito.when(productRepository.findById("Id1")).thenReturn(optionalProduct);


        Mockito.doAnswer((i)->{
            System.out.println(i.getArguments()[0]+" is getting deleted ");
            return null;
        }).when(warehouseRepository).deleteBySerialIdAndLocation("Id1", "BANGALORE");

        WarehouseDto dto = service.deleteProductStock("Id1","BANGALORE");

        Assertions.assertNotNull(dto);
        Assertions.assertEquals("Id1", dto.getSerialId());
        Assertions.assertEquals("P1", dto.getName());
        Assertions.assertEquals(15,dto.getQuantity());

    }

    @Test
    void getQuantityBySerialIdAndLocation() {
        Products product = new Products();
        product.setSerialId("Id1");
        product.setName("P1");
        product.setDescription("P1");
        product.setPrice(100);

        Warehouse warehouse= new Warehouse(product, 15,"BANGALORE");
        Mockito.when(warehouseRepository.findBySerialIdAndLocation("Id1","BANGALORE")).thenReturn(warehouse);

        Optional<Products> optionalProduct = Optional.of(product);
        Mockito.when(productRepository.findById("Id1")).thenReturn(optionalProduct);

        int quantity = service.getQuantityBySerialIdAndLocation("Id1","BANGALORE");
        Assertions.assertEquals(15,quantity);

        quantity = service.getQuantityBySerialIdAndLocation("Id2","BANGALORE");
        Assertions.assertEquals(-1,quantity);

        quantity = service.getQuantityBySerialIdAndLocation("Id3","BANGALORE");
        Assertions.assertEquals(-1,quantity);

    }

    @Test
    void getProductStock() {

        Products product = new Products();
        product.setSerialId("Id1");
        product.setName("P1");
        product.setDescription("P1");
        product.setPrice(100);

        Warehouse warehouse= new Warehouse(product, 15,"BANGALORE");
        List<Warehouse> warehouseList = new ArrayList<>();
        warehouseList.add(warehouse);
        warehouse = new Warehouse(product, 20,"MUMBAI");
        warehouseList.add(warehouse);

        Mockito.when(warehouseRepository.findBySerialId("Id1")).thenReturn(warehouseList);

        Optional<Products> optionalProduct = Optional.of(product);
        Mockito.when(productRepository.findById("Id1")).thenReturn(optionalProduct);

        List<WarehouseDto> dtoList = service.getProductStock("Id1");

        Assertions.assertNotNull(dtoList);
        Assertions.assertEquals(2,dtoList.size());





    }
}