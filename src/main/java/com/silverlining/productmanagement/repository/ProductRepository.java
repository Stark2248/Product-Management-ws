package com.silverlining.productmanagement.repository;

import com.silverlining.productmanagement.models.Products;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Products,String> {

}
