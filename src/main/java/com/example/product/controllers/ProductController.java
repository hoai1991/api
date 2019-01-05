package com.example.product.controllers;

import com.example.product.entities.ProductEntity;
import com.example.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity getAllProduct(){
        if (productRepository.findAll().isEmpty()) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(productRepository.findAll(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createProduct(@RequestBody Map<String,String> body){
        int id = Integer.parseInt(body.get("id"));
        if(productRepository.findById(id) != null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        String name = body.get("name");
        String descreption = body.get("descreption");
        int price = Integer.parseInt(body.get("price"));
        boolean status = body.get("status").equals("Activity");
        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        ProductEntity product = new ProductEntity();
        product.setId(id);
        product.setName(name);
        product.setDescription(descreption);
        product.setPrice(price);
        product.setStatus(status);
        product.setCreatetime(createTime);
        productRepository.save(product);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity updateProduct(@PathVariable int id, @RequestBody Map<String,String> body){
        String name = body.get("name");
        String descreption = body.get("descreption");
        int price = Integer.parseInt(body.get("price"));
        boolean status = body.get("status").equals("Activity");
        Timestamp editedTime = new Timestamp(System.currentTimeMillis());
        ProductEntity product = productRepository.findById(id);
        product.setName(name);
        product.setDescription(descreption);
        product.setPrice(price);
        product.setStatus(status);
        product.setEditedtime(editedTime);
        productRepository.save(product);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public  ResponseEntity deleteProduct(@PathVariable int id){
        ProductEntity product = productRepository.findById(id);
        if (product != null) {
            productRepository.delete(product);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("{id}")
    public ResponseEntity searchProductByID(@PathVariable int id){
        ProductEntity product = productRepository.findById(id);
        if (product != null) {
            return new ResponseEntity(product,HttpStatus.FOUND);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("searchPriceRanger")
    public ResponseEntity searchByPriceRanger(@RequestBody Map<String,Integer> body){
        int priceFrom = body.get("priceFrom");
        int priceTo = body.get("priceTo");
        List<ProductEntity> resultList = productRepository.findByPriceBetween(priceFrom, priceTo);
        if (resultList.isEmpty())return new ResponseEntity(HttpStatus.NOT_FOUND);
        return new ResponseEntity(resultList,HttpStatus.OK);
    }
}