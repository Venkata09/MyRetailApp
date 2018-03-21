package com.myRetail.app.service;

import com.myRetail.app.client.ProductDataClient;
import com.myRetail.app.controller.ProductController;
import com.myRetail.app.model.Price;
import com.myRetail.app.model.Product;
import com.myRetail.app.repository.PriceRepository;
import com.myRetail.app.service.impl.ProductServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author vdokku
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyRetailAppServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);


    @Autowired
    ProductService productServiceImpl;

    @MockBean
    PriceRepository priceRepositoryMock;

    @MockBean
    ProductDataClient productDataClient;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void validateProductInDB() throws Exception {
        Price newPrice = new Price("13860428", "100", "USD");

        when(priceRepositoryMock.findPriceByProductId("13860428")).thenReturn(newPrice);

        when(productDataClient.gerProductDetails("13860428")).thenReturn("The Big Lebowski ");

        boolean isProductInDB = productServiceImpl.isValidProduct("13860428");
        assertEquals(true, isProductInDB);
    }


    @Test
    public void validateProductInDBNegativeTestCase() throws Exception {
        Price newPrice = new Price("13860428", "100", "USD");

        when(priceRepositoryMock.findPriceByProductId("13860428")).thenReturn(newPrice);

        when(productDataClient.gerProductDetails("13860428")).thenReturn("The Big Lebowski ");

        boolean isProductInDB = productServiceImpl.isValidProduct("13860429");
        assertEquals(false, isProductInDB);
    }


    @Test
    public void updatePriceInDB() throws Exception {
        Price priceObj = new Price("13860428", "100", "USD");

        when(priceRepositoryMock.findPriceByProductId("13860428")).thenReturn(priceObj);
        when(productDataClient.gerProductDetails("13860428")).thenReturn("The Big Lebowski ");

        Price newUpdatedPrice = productServiceImpl.updatePrice(new Price("13860428", "110", "USD"));

        assertEquals("110", newUpdatedPrice.getPrice());
        assertEquals("USD", newUpdatedPrice.getCurrency());

        Price newUpdatedPriceAndCurrency = productServiceImpl.updatePrice(new Price("13860428", "6000", "INR"));

        assertEquals("6000", newUpdatedPriceAndCurrency.getPrice());
        assertEquals("INR", newUpdatedPriceAndCurrency.getCurrency());

    }

}
