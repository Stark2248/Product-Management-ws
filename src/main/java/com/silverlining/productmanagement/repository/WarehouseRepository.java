package com.silverlining.productmanagement.repository;

import com.silverlining.productmanagement.models.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Integer> {


    //@Query("SELECT w.serialId.serialId, w.serialId.name, w.quantity FROM Warehouse w")
    //List<Warehouse> getWarehouseDetails();

    //List<Warehouse> findAll();
    List<Warehouse> findByLocation(String location);


    @Query("SELECT w FROM Warehouse w WHERE w.product.serialId = :serialId AND w.location = :location")
    Warehouse findBySerialIdAndLocation(@Param("serialId") java.lang.String serialId, @Param("location") String location);

    @Query("Update Warehouse w set w.quantity = :quantity WHERE w.product.serialId = :serialId AND w.location = :location")
    void updateQuantityBySerialIdAndLocation(@Param("quantity")int quantity, @Param("serialId") java.lang.String serialId, @Param("location") String location);

    @Query("Delete from Warehouse w Where w.product.serialId = :serialId AND w.location = :location")
    void deleteBySerialIdAndLocation(@Param("serialId") java.lang.String serialId, @Param("location") String location);
}
