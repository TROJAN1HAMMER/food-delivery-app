package com.example.food;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testOrderSuccess() throws Exception {
        String json = "{\"orderId\":\"1\", \"item\":\"Pizza\"}";
        mockMvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON).content(json))
               .andExpect(status().isCreated());
    }

    @Test
    void testDuplicateOrderFailure() throws Exception {
        String json = "{\"orderId\":\"2\", \"item\":\"Burger\"}";
        mockMvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON).content(json));
        // Duplicate call
        mockMvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON).content(json))
               .andExpect(status().isConflict());
    }
}