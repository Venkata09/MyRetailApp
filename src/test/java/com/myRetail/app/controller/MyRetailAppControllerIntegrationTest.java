package com.myRetail.app.controller;

import com.myRetail.app.MyRetailAppApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * @author vdokku
 */

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = MyRetailAppApplication.class
)
@AutoConfigureMockMvc
@PropertySource({"classpath:application.yaml",
        "classpath:application-test.properties"})
public class MyRetailAppControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;


    String endPointURL = "/api/product/13860428";


    /**
     * Reterives the data from Cassandra and compare with the expected.
     *
     * @throws Exception
     */
    @Test
    public void contextLoads() throws Exception {

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, endPointURL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        String expectedProductJson = "{\"id\":\"13860428\",\"title\":\"The Big Lebowski (Blu-ray)\",\"price\":{\"productId\":\"13860428\",\"price\":\"50\",\"currency\":\"USD\"}}";
        JSONAssert.assertEquals(expectedProductJson, action.andReturn().getResponse().getContentAsString(), false);
    }


}
