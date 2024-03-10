package com.silverlining.productmanagement.repository;

import com.silverlining.productmanagement.models.Products;
import com.silverlining.productmanagement.models.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Integer> {


    List<Warehouse> findBySerialId(Products product);


    List<Warehouse> findByLocation(String location);


    @Query("SELECT w FROM Warehouse w WHERE w.product.serialId = :serialId AND w.location = :location")
    Warehouse findBySerialIdAndLocation(@Param("serialId") String serialId, @Param("location") String location);

    @Transactional
    @Modifying
    @Query(value = "Update Warehouse w set w.quantity = ?1 WHERE w.serial_Id = ?2 AND w.location = ?3", nativeQuery = true)
    void updateQuantityBySerialIdAndLocation(int quantity, String serialId, String location);

    @Transactional
    @Modifying
    @Query(value = "Delete from Warehouse w Where w.serial_Id = ?1 AND w.location = ?2", nativeQuery = true)
    void deleteBySerialIdAndLocation(String serialId, String location);
}
