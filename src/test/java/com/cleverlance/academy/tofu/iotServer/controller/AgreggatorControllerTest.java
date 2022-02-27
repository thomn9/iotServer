package com.cleverlance.academy.tofu.iotServer.controller;

import com.cleverlance.academy.tofu.iotServer.model.MeteorologicalData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest
@AutoConfigureMockMvc
class AgreggatorControllerTest {

    @Autowired
    private MockMvc mvc;
    AgreggatorController agreggatorController;


    @Test
    void getIdentification() throws Exception {
        mvc.perform(get("/identification")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Futomaki T.O.F.U.")));
    }

    //fixme - saveMeteorologicalData() and getMeteorologicalData() tested in one @Test together because I wasn´t able
    // to set private field meteorologicalDataList with some mock data and in new @Test is gets reset to initial state (empty list)
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

    /*@Test
    void getMeteorologicalData() throws Exception {
        mvc.perform(get("/meteorological-data")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].humidity", is("Futomaki T.O.F.U.")));

    }*/
}