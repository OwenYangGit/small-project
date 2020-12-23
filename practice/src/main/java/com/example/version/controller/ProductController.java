package com.example.version.controller;

import java.util.List;
import java.util.NoSuchElementException;

import com.example.version.model.Product;
import com.example.version.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    public ProductService service;

    @GetMapping("/products")
    public List<Product> list() {
        return service.listAll();
    }
    
    @PostMapping("/product")
    public void add(@RequestBody Product product){
        service.save(product);
    }
    
    @DeleteMapping("/product/{id}")
    public List<Product> del(@PathVariable Integer id) {
        service.del(id);
        return service.listAll();
    }
    @PutMapping("/product/{id}")
    public ResponseEntity<?> update(@RequestBody Product product , @PathVariable Integer id) {
        try {
            Product existsProduct = service.get(id);
            existsProduct.setName(product.getName());
            existsProduct.setPrice(product.getPrice());
            service.save(existsProduct);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}