package com.saloni.spring.boot.inventoryservice;

import com.saloni.spring.boot.inventoryservice.dto.InventoryResponse;
import com.saloni.spring.boot.inventoryservice.model.Inventory;
import com.saloni.spring.boot.inventoryservice.repository.InventoryRepository;
import com.saloni.spring.boot.inventoryservice.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceTest {

    private InventoryService inventoryService;

    @Mock
    private InventoryRepository inventoryRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        inventoryService = new InventoryService(inventoryRepository);
    }

    @Test
    void testIsInStock_WithInStockItems_ReturnsInventoryResponseList() {
        // Mock data
        List<String> skuCodes = Arrays.asList("iphone-13", "iphone13-red");
        List<Inventory> inventories = Arrays.asList(
                new Inventory("iphone-13", 10),
                new Inventory("iphone13-red", 0)
        );

        // Mock repository method
        when(inventoryRepository.findBySkuCodeIn(skuCodes)).thenReturn(inventories);

        // Invoke the service method
        List<InventoryResponse> result = inventoryService.isInStock(skuCodes);

        // Verify the result
        assertEquals(2, result.size());
        assertTrue(result.get(0).isInStock());
        assertFalse(result.get(1).isInStock());

        // Verify that the repository method was called with the correct arguments
        verify(inventoryRepository).findBySkuCodeIn(skuCodes);
    }

    @Test
    void testIsInStock_WithEmptySkuCodes_ReturnsEmptyList() {
        // Invoke the service method with empty skuCodes
        List<InventoryResponse> result = inventoryService.isInStock(Collections.emptyList());

        // Verify the result
        assertTrue(result.isEmpty());

        // Verify that the repository method was not called
        verifyNoInteractions(inventoryRepository);
    }

    @Test
    void testIsInStock_WithNonExistingSkuCodes_ReturnsEmptyList() {
        // Mock data
        List<String> skuCodes = Arrays.asList("iphone-13", "iphone13-red");

        // Mock repository method
        when(inventoryRepository.findBySkuCodeIn(skuCodes)).thenReturn(Collections.emptyList());

        // Invoke the service method
        List<InventoryResponse> result = inventoryService.isInStock(skuCodes);

        // Verify the result
        assertTrue(result.isEmpty());

        // Verify that the repository method was called with the correct arguments
        verify(inventoryRepository).findBySkuCodeIn(skuCodes);
    }
}
