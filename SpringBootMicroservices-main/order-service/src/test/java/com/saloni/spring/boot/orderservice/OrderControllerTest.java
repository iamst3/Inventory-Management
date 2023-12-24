package com.saloni.spring.boot.orderservice;

import com.saloni.spring.boot.orderservice.dto.OrderRequest;
import com.saloni.spring.boot.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.concurrent.CompletableFuture;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPlaceOrder() throws Exception {
        // Mock order request
        OrderRequest orderRequest = new OrderRequest();
        // Set up any required properties in the order request

        // Mock order service response
        String expectedResponse = "Order placed successfully";
        Mockito.when(orderService.placeOrder(Mockito.any(OrderRequest.class))).thenReturn(expectedResponse);

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"property\": \"value\"}")) // Replace with the actual JSON content of the order request
                .andExpect(MockMvcResultMatchers.status().isCreated());
//                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
    }
}
