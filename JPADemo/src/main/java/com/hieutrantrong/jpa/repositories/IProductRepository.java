package com.hieutrantrong.jpa.repositories;

import com.hieutrantrong.jpa.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductRepository extends JpaRepository<ProductEntity, String> {
    List<ProductEntity> findByNameContainingIgnoreCase(String search);

    ProductEntity findById(String id);
}
