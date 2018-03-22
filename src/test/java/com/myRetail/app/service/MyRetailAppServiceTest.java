package com.myRetail.app.service;

import com.myRetail.app.client.ProductDataClient;
import com.myRetail.app.model.Price;
import com.myRetail.app.repository.PriceRepository;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
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

    private static final String HELLOWORLD_TOPIC = "helloworld.t";

    @ClassRule
    public static KafkaEmbedded embeddedKafka =
            new KafkaEmbedded(1, true, HELLOWORLD_TOPIC);

    @MockBean
    KafkaSender sender;


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
