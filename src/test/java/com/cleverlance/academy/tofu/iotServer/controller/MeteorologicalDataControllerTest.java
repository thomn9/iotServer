package com.cleverlance.academy.tofu.iotServer.controller;

import com.cleverlance.academy.tofu.iotServer.model.MeteorologicalData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class MeteorologicalDataControllerTest {

    @Autowired
    private MockMvc mvc;
    MeteorologicalDataController meteorologicalDataController;

    @Test
    void saveAndGetMeteorologicalData() throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        //testing first post
        MeteorologicalData testMeteorologicalDataOne = new MeteorologicalData(29.08f,81.9f);
        String testMeteorologicalDataOneJson = ow.writeValueAsString(testMeteorologicalDataOne);

        mvc.perform(post("/meteorological-data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testMeteorologicalDataOneJson))
                .andExpect(status().isOk());

        //testing second post
        MeteorologicalData testMeteorologicalDataTwo = new MeteorologicalData(25.1f,78.22f);
        String testMeteorologicalDataTwoJson = ow.writeValueAsString(testMeteorologicalDataTwo);

        mvc.perform(post("/meteorological-data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testMeteorologicalDataTwoJson))
                .andExpect(status().isOk());

        //testing get and expecting data from previous posts
        mvc.perform(get("/meteorological-data")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].temperature", is(29.08)))
                .andExpect(jsonPath("$[0].humidity", is(81.9)))
                .andExpect(jsonPath("$[1].temperature", is(25.1)))
                .andExpect(jsonPath("$[1].humidity", is(78.22)));

    }
}