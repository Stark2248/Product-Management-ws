package com.silverlining.productmanagement.repository;

import com.silverlining.productmanagement.models.Products;
import com.silverlining.productmanagement.models.Warehouse;
import com.silverlining.productmanagement.models.WarehouseLocation;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
class WarehouseRepositoryTest {

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    EntityManager entityManager;

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
        warehouse.setLocation(WarehouseLocation.BANGALORE.name());
        warehouse.setQuantity(20);
        warehouseRepository.save(warehouse);

        product1 = new Products();
        product1.setSerialId("Id2");
        product1.setName("P2");
        product1.setDescription("P2");
        product1.setPrice(140);
        productRepository.save(product1);

        warehouse.setProduct(product1);
        warehouse.setId(2);
        warehouse.setLocation(WarehouseLocation.MUMBAI.name());
        warehouse.setQuantity(20);
        warehouseRepository.save(warehouse);

        product1 = new Products();
        product1.setSerialId("Id3");
        product1.setName("P3");
        product1.setDescription("P3");
        product1.setPrice(110);
        productRepository.save(product1);

        warehouse.setProduct(product1);
        warehouse.setId(3);
        warehouse.setLocation(WarehouseLocation.MUMBAI.name());
        warehouse.setQuantity(10);
        warehouseRepository.save(warehouse);
    }





    @Test
    void getWarehouseDetailByserialId() {

        Products product1 = new Products();
        product1.setSerialId("Id1");
        product1.setName("P1");
        product1.setDescription("P1");
        product1.setPrice(125);

        Warehouse warehouse = warehouseRepository.findBySerialIdAndLocation("Id1","BANGALORE");

        Assertions.assertNotNull(warehouse);
        Assertions.assertEquals("Id1",warehouse.getProduct().getSerialId());
        Assertions.assertEquals(20, warehouse.getQuantity());
        Assertions.assertEquals("P1",warehouse.getProduct().getName());

    }


    @Test
    void findByLocation() {
        List<Warehouse> warehouseList = warehouseRepository.findByLocation("MUMBAI");

        Assertions.assertEquals(2,warehouseList.size());
        Assertions.assertEquals("Id2",warehouseList.get(0).getProduct().getSerialId());
    }

    /*@Test
    @Commit
    void updateQuantityBySerialIdAndLocation() {

        warehouseRepository.updateQuantityBySerialIdAndLocation(100,"Id1","BANGALORE");

        entityManager.flush();

        Warehouse warehouse = warehouseRepository.findBySerialIdAndLocation("Id1", "BANGALORE");

        Assertions.assertEquals(100, warehouse.getQuantity());

    }*/

    @Test
    void deleteBySerialIdAndLocation() {

        warehouseRepository.deleteBySerialIdAndLocation("Id1","BANGALORE");

        Warehouse warehouse = warehouseRepository.findBySerialIdAndLocation("Id1", "BANGALORE");

        Assertions.assertNull(warehouse);

    }
}