package com.myRetail.app.service;

import com.myRetail.app.client.ProductDataClient;
import com.myRetail.app.controller.ProductController;
import com.myRetail.app.model.Price;
import com.myRetail.app.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * @author vdokku
 */

@WebMvcTest(value = ProductController.class)
@RunWith(SpringRunner.class)
public class MyRetailAppServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @Mock
    ProductDataClient productDataClientMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    Product mockProduct = new Product("13860428", "The Big Lebowski (Blu-ray)",
            new Price("13860428", "100", "USD"));

    Price mockPrice = new Price("13860428", "100", "USD");

    String js = "{\"productId\":\"13860428\",\"price\":\"100\",\"currency\":\"USD\"}";


    @Test
    public void getProductDetails() throws Exception {

        Mockito.when(productService.getProductDetailsByProductId(Mockito.anyString())).thenReturn(mockProduct);

        String endPointURL = "/api/product/13860428";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(endPointURL).accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expectedProductJson = "{\"id\":\"13860428\",\"title\":\"The Big Lebowski (Blu-ray)\",\"price\":{\"productId\":\"13860428\",\"price\":\"100\",\"currency\":\"USD\"}}";
        JSONAssert.assertEquals(expectedProductJson, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void updatePriceForProduct() throws Exception {


        Mockito.when(productService.updatePrice(Mockito.any(Price.class))).thenReturn(mockPrice);

        String endPointURL = "/api/product/13860428";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(endPointURL).accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println("----------------------------------------------");
        System.out.println(result.getResponse().getContentAsString());
        System.out.println("----------------------------------------------");

    }


}
