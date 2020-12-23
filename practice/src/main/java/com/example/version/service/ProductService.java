package com.example.version.service;

import java.util.List;

import javax.transaction.Transactional;

import com.example.version.model.Product;
import com.example.version.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository repo;
    
    public List<Product> listAll(){
        return repo.findAll();
    }
    
    public void save(Product product) {
        repo.save(product);
    }

    public Product get(Integer id) {
        return repo.findById(id).get();
    }

    public void del(Integer id) {
        repo.deleteById(id);
    }
}