package com.myRetail.app.controller;

import com.myRetail.app.client.ProductDataClient;
import com.myRetail.app.controller.ProductController;
import com.myRetail.app.model.Price;
import com.myRetail.app.model.Product;
import com.myRetail.app.repository.PriceRepository;
import com.myRetail.app.service.KafkaSender;
import com.myRetail.app.service.ProductService;
import com.myRetail.app.service.impl.ProductServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;


/**
 * @author vdokku
 */

@WebMvcTest(value = ProductController.class)
@RunWith(SpringRunner.class)
public class MyRetailAppControllerUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductServiceImpl productService;

    @Mock
    PriceRepository priceRepositoryMock;

    @Before
    public void setUp() {
        Mockito.reset(priceRepositoryMock);
        MockitoAnnotations.initMocks(this);
    }


    String endPointURL = "/api/product/13860428";
    Product mockProduct = new Product("13860428", "The Big Lebowski (Blu-ray)",
            new Price("13860428", "100", "USD"));
    String postPriceForProduct = "{\"productId\":\"13860428\",\"price\":\"50\",\"currency\":\"USD\"}";
    Price newPrice = new Price("13860428", "500", "INR");

    @Test
    public void getProductDetails() throws Exception {

        when(productService.getProductDetailsByProductId(Mockito.anyString())).thenReturn(mockProduct);

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, endPointURL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        String expectedProductJson = "{\"id\":\"13860428\",\"title\":\"The Big Lebowski (Blu-ray)\",\"price\":{\"productId\":\"13860428\",\"price\":\"100\",\"currency\":\"USD\"}}";
        JSONAssert.assertEquals(expectedProductJson, action.andReturn().getResponse().getContentAsString(), false);
    }
}
