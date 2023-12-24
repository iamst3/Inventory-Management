package com.saloni.spring.boot.productservice.repository;

import com.saloni.spring.boot.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}