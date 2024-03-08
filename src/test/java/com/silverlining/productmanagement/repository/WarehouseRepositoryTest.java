package com.silverlining.productmanagement.repository;

import com.silverlining.productmanagement.models.Products;
import com.silverlining.productmanagement.models.Warehouse;
import com.silverlining.productmanagement.models.WarehouseLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WarehouseRepositoryTest {

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    public void setUp(){
        Warehouse warehouse = new Warehouse();
        Products product1 = new Products();
        product1.setSerialId("Id1");
        product1.setName("P1");
        product1.setDescription("P1");
        product1.setPrice(125);
        productRepository.save(product1);
        warehouse.setProduct(product1);
        warehouse.setId(1);
        //warehouse.setLocation(WarehouseLocation.BANGALORE);
        warehouse.setQuantity(20);
        warehouseRepository.save(warehouse);
    }

    @Test
    void getWarehouseDetailByserialId() {

        Products product1 = new Products();
        product1.setSerialId("Id1");
        product1.setName("P1");
        product1.setDescription("P1");
        product1.setPrice(125);

        //Warehouse warehouse = warehouseRepository.findBySerialIdAndLocation(product1.getSerialId(),WarehouseLocation.BANGALORE);
        //for(WarehouseDto warehouse: warehouseList)
        //    System.out.println(warehouse.getProduct().getSerialId()+"-"+warehouse.getQuantity()+"-"+warehouse.getLocation());
        String r= WarehouseLocation.findByName("BLR").name();
        System.out.println(r);
    }
}