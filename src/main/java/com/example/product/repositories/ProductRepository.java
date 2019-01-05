package com.example.product.repositories;

import com.example.product.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {

    List<ProductEntity> findByPriceBetween(int lowPrice,int hightPrice);
    ProductEntity findById(int id);
}
