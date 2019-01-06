package com.hieutrantrong.jpa.controllers;

import com.hieutrantrong.jpa.entities.ProductEntity;
import com.hieutrantrong.jpa.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private IProductRepository productRepo;

    @GetMapping
    public ResponseEntity getAll() {
        if(productRepo.findAll().isEmpty())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        return new ResponseEntity(productRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("find")
    public ResponseEntity getByNameCaseInsensitive(@RequestBody Map<String, String> body) {
        String search = body.get("search");

        List<ProductEntity> list = productRepo.findByNameContainingIgnoreCase(search);

        if(list.isEmpty())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity createProduct(@RequestBody Map<String, String> body) {
        ProductEntity productEntity = getProductEntityFromRequest(body);
        productRepo.save(productEntity);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity updateProduct(@RequestBody Map<String, String> body) {
        if (productRepo.findById(body.get("id")) == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        else {
            ProductEntity productEntity = getProductEntityFromRequest(body);
            productRepo.save(productEntity);
//            productRepo.saveAndFlush()
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    //problem
    @PutMapping("remove/{id}")
    public ResponseEntity removeProduct(@PathVariable String id) {
        if(productRepo.findById(id) == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        else {
            ProductEntity productEntity = productRepo.findByIdIs(id);
            productEntity.setStatus(0);
            productRepo.save(productEntity);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    //problem
    @GetMapping("find/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        if(productRepo.findById(id) == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(productRepo.findById(id), HttpStatus.OK);
    }

//    @GetMapping("find/price/range")
//    public ResponseEntity findProductInRange(@RequestBody Map<String, String> body) {
//
//    }
    //Show product
    //Thêm
    //Sửa
    //Xoá
    //Kiếm theo id
    //Search by price range

    //================private methods==================

    private ProductEntity getProductEntityFromRequest(Map<String, String> body) {
        String id = body.get("id");
        String name = body.get("name");
        String description = body.get("description");
        Float price = Float.parseFloat(body.get("price"));
        Integer status = Integer.parseInt(body.get("status"));
//        Timestamp createdTime = Timestamp.valueOf(body.get("createdTime"));
//        Timestamp editedTime = Timestamp.valueOf(body.get("editedTime"));

        Timestamp createdTime = new Timestamp(System.currentTimeMillis());
        Timestamp editedTime = new Timestamp(System.currentTimeMillis());

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(id);
        productEntity.setName(name);
        productEntity.setDescription(description);
        productEntity.setPrice(price);
        productEntity.setStatus(status);
        productEntity.setCreatedTime(createdTime);
        productEntity.setEditedTime(editedTime);

        return productEntity;
    }
}
