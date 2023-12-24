package com.saloni.spring.boot.productservice.service;

import com.saloni.spring.boot.productservice.dto.ProductRequest;
import com.saloni.spring.boot.productservice.dto.ProductResponse;
import com.saloni.spring.boot.productservice.model.Product;
import com.saloni.spring.boot.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    //This method receives a ProductRequest object, which contains the data for creating a new product.
    // It constructs a new Product object using the builder pattern and sets its properties based on the values from
    // the productRequest.
    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(this::mapToProductResponse).toList();
    }
    public ProductResponse getProductById(String id) {
        Product product = getProductByIdOrThrowException(id);
        return mapToProductResponse(product);
    }

    public void updateProduct(String id, ProductRequest productRequest) {
        Product product = getProductByIdOrThrowException(id);
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        productRepository.save(product);
        log.info("Product {} is updated", product.getId());
    }

    public void deleteProduct(String id) {
        Product product = getProductByIdOrThrowException(id);
        productRepository.delete(product);
        log.info("Product {} is deleted", product.getId());
    }
    private Product getProductByIdOrThrowException(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}