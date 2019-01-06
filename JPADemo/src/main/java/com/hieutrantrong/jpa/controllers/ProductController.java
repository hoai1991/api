package com.hieutrantrong.jpa.controllers;

import com.hieutrantrong.jpa.entities.ProductEntity;
import com.hieutrantrong.jpa.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private IProductRepository productRepo;

    @GetMapping
    public ResponseEntity getAll() {
        if(productRepo.findAll().isEmpty())return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(productRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("find")
    public ResponseEntity getByNameCaseInsensitive(@RequestBody Map<String, String> body) {
        String search = body.get("search");
        ProductEntity productEntity = productRepo.findByIdIs(search);
        if(productEntity == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(productEntity, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity createProduct(@RequestBody Map<String, String> body) {
        ProductEntity productEntity = getProductEntityFromRequest(body);
        Timestamp createdTime = new Timestamp(System.currentTimeMillis());
        productEntity.setCreatedTime(createdTime);
        productRepo.saveAndFlush(productEntity);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity updateProduct(@RequestBody Map<String, String> body) {
        if (productRepo.findById(body.get("id")) == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        ProductEntity productEntity = getProductEntityFromRequest(body);
        Timestamp editedTime = new Timestamp(System.currentTimeMillis());
        productEntity.setEditedTime(editedTime);
        productRepo.saveAndFlush(productEntity);
        return new ResponseEntity(HttpStatus.OK);
    }

    //problem
    @PutMapping("remove/{id}")
    public ResponseEntity removeProduct(@PathVariable String id) {
        if(productRepo.findById(id) == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        ProductEntity productEntity = productRepo.findByIdIs(id);
        productEntity.setStatus(0);
        productRepo.save(productEntity);
        return new ResponseEntity(HttpStatus.OK);
    }

    //problem
    @GetMapping("find/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        if(productRepo.findById(id) == null)return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(productRepo.findById(id), HttpStatus.OK);
    }

    @GetMapping("find/price/range")
    public ResponseEntity findProductInRange(@RequestBody Map<String, Float> body) {
        List<ProductEntity> seachList = productRepo.findByPriceBetween(body.get("Price1"),body.get("Price2"));
        if (seachList.isEmpty())return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(seachList,HttpStatus.FOUND);
    }

    //================private methods==================

    private ProductEntity getProductEntityFromRequest(Map<String, String> body) {
        String id = body.get("id");
        String name = body.get("name");
        String description = body.get("description");
        Float price = Float.parseFloat(body.get("price"));
        Integer status = Integer.parseInt(body.get("status"));
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(id);
        productEntity.setName(name);
        productEntity.setDescription(description);
        productEntity.setPrice(price);
        productEntity.setStatus(status);
        return productEntity;
    }
}
