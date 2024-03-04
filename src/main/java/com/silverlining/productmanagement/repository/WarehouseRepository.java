package com.silverlining.productmanagement.repository;

import com.silverlining.productmanagement.dto.WarehouseDto;
import com.silverlining.productmanagement.models.Warehouse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends CrudRepository<Warehouse,String> {


    @Query("Select serialId,name,quantity from Products JOIN Warehouse ON Products.serialId = Warehouse.serialId")
    List<WarehouseDto> getWharehouseDetails();

    @Query("Select serialId,name,quantity from Products JOIN Warehouse ON Products.serialId = Warehouse.serialId WHERE SerialId = :serial")
    WarehouseDto getWharehouseDetailById(String serial);

}
