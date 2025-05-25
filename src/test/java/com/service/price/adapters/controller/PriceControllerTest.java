package com.service.price.adapters.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test1_10amOn14th() throws Exception {
        testPriceForDateTime("2020-06-14-10.00.00", 35.50, 1);
    }

    @Test
    void test2_4pmOn14th() throws Exception {
        testPriceForDateTime("2020-06-14-16.00.00", 25.45, 2);
    }

    @Test
    void test3_9pmOn14th() throws Exception {
        testPriceForDateTime("2020-06-14-21.00.00", 35.50, 1);
    }

    @Test
    void test4_10amOn15th() throws Exception {
        testPriceForDateTime("2020-06-15-10.00.00", 30.50, 3);
    }

    @Test
    void test5_9pmOn16th() throws Exception {
        testPriceForDateTime("2020-06-16-21.00.00", 38.95, 4);
    }

    private void testPriceForDateTime(String dateTime, double expectedPrice, int expectedPriceList) throws Exception {
        ResultActions result = mockMvc.perform(get("/api/prices")
                        .param("date", dateTime)
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(expectedPriceList))
                .andExpect(jsonPath("$.price").value(expectedPrice));

        String content = result.andReturn().getResponse().getContentAsString();
        System.out.println("Test for " + dateTime + ": " + content);
    }
}
