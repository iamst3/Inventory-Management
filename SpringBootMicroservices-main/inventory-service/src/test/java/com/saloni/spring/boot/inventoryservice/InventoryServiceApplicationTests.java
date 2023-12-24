package com.saloni.spring.boot.inventoryservice;

import com.saloni.spring.boot.inventoryservice.controller.InventoryController;
import com.saloni.spring.boot.inventoryservice.dto.InventoryResponse;
import com.saloni.spring.boot.inventoryservice.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class InventoryServiceApplicationTests {

	private MockMvc mockMvc;

	@Mock
	private InventoryService inventoryService;

	private InventoryController inventoryController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		inventoryController = new InventoryController(inventoryService);
		mockMvc = MockMvcBuilders.standaloneSetup(inventoryController).build();
	}

	@Test
	public void testIsInStock() throws Exception {
		// Mock data
		List<String> skuCodes = Arrays.asList("iphone-13", "iphone13-red");
		List<InventoryResponse> inventoryResponses = Arrays.asList(
				new InventoryResponse("iphone-13", true),
				new InventoryResponse("iphone13-red", false)
		);

		// Mock service method
		when(inventoryService.isInStock(skuCodes)).thenReturn(inventoryResponses);

		// Perform the GET request and validate the response
		mockMvc.perform(get("/api/inventory")
						.param("skuCode", "iphone-13")
						.param("skuCode", "iphone13-red"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$[0].skuCode").value("iphone-13"))
				.andExpect(jsonPath("$[0].inStock").value(true))
				.andExpect(jsonPath("$[1].skuCode").value("iphone13-red"))
				.andExpect(jsonPath("$[1].inStock").value(false));

		// Verify that the service method was called with the correct arguments
		verify(inventoryService).isInStock(skuCodes);
	}

}
