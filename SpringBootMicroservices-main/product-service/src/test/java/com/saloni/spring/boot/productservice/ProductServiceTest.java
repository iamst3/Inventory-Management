package com.saloni.spring.boot.productservice;

import com.saloni.spring.boot.productservice.dto.ProductRequest;
import com.saloni.spring.boot.productservice.dto.ProductResponse;
import com.saloni.spring.boot.productservice.model.Product;
import com.saloni.spring.boot.productservice.repository.ProductRepository;
import com.saloni.spring.boot.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createProduct_ShouldSaveProductAndReturnId() {
        // Arrange
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Test Product");
        productRequest.setDescription("Test Description");
        productRequest.setPrice(BigDecimal.valueOf(9.99));

        // Act
        productService.createProduct(productRequest);

        // Assert
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getAllProducts_ShouldReturnListOfProducts() {
        // Arrange
        List<Product> products = new ArrayList<>();
        products.add(new Product("1", "Product 1", "Description 1", BigDecimal.valueOf(9.99)));
        products.add(new Product("2", "Product 2", "Description 2", BigDecimal.valueOf(19.99)));
        when(productRepository.findAll()).thenReturn(products);

        // Act
        List<ProductResponse> productResponses = productService.getAllProducts();

        // Assert
        assertEquals(products.size(), productResponses.size());
        assertEquals(products.get(0).getId(), productResponses.get(0).getId());
        assertEquals(products.get(0).getName(), productResponses.get(0).getName());
        assertEquals(products.get(0).getDescription(), productResponses.get(0).getDescription());
        assertEquals(products.get(0).getPrice(), productResponses.get(0).getPrice());
        assertEquals(products.get(1).getId(), productResponses.get(1).getId());
        assertEquals(products.get(1).getName(), productResponses.get(1).getName());
        assertEquals(products.get(1).getDescription(), productResponses.get(1).getDescription());
        assertEquals(products.get(1).getPrice(), productResponses.get(1).getPrice());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById_ExistingId_ShouldReturnProductResponse() {
        // Arrange
        String productId = "1";
        Product product = new Product(productId, "Product 1", "Description 1", BigDecimal.valueOf(9.99));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        ProductResponse productResponse = productService.getProductById(productId);

        // Assert
        assertEquals(product.getId(), productResponse.getId());
        assertEquals(product.getName(), productResponse.getName());
        assertEquals(product.getDescription(), productResponse.getDescription());
        assertEquals(product.getPrice(), productResponse.getPrice());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void getProductById_NonExistingId_ShouldThrowException() {
        // Arrange
        String productId = "1";
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productService.getProductById(productId));
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void updateProduct_ExistingId_ShouldUpdateProduct() {
        // Arrange
        String productId = "1";
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Updated Product");
        productRequest.setDescription("Updated Description");
        productRequest.setPrice(BigDecimal.valueOf(19.99));
        Product existingProduct = new Product(productId, "Product 1", "Description 1", BigDecimal.valueOf(9.99));
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // Act
        productService.updateProduct(productId, productRequest);

        // Assert
        verify(productRepository, times(1)).save(any(Product.class));
        assertEquals(productRequest.getName(), existingProduct.getName());
        assertEquals(productRequest.getDescription(), existingProduct.getDescription());
        assertEquals(productRequest.getPrice(), existingProduct.getPrice());
    }

    @Test
    void updateProduct_NonExistingId_ShouldThrowException() {
        // Arrange
        String productId = "1";
        ProductRequest productRequest = new ProductRequest();
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productService.updateProduct(productId, productRequest));
        verify(productRepository, times(0)).save(any(Product.class));
    }

    @Test
    void deleteProduct_ExistingId_ShouldDeleteProduct() {
        // Arrange
        String productId = "1";
        Product existingProduct = new Product(productId, "Product 1", "Description 1", BigDecimal.valueOf(9.99));
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).delete(existingProduct);
    }

    @Test
    void deleteProduct_NonExistingId_ShouldThrowException() {
        // Arrange
        String productId = "1";
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productService.deleteProduct(productId));
        verify(productRepository, times(0)).delete(any(Product.class));
    }
}
