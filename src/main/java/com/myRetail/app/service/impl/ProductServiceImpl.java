package com.myRetail.app.service.impl;

import com.myRetail.app.client.ProductDataClient;
import com.myRetail.app.exception.PriceNotFoundException;
import com.myRetail.app.exception.ProductNotFoundException;
import com.myRetail.app.model.Price;
import com.myRetail.app.model.Product;
import com.myRetail.app.repository.PriceRepository;
import com.myRetail.app.service.KafkaSender;
import com.myRetail.app.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * @author vdokku
 */

@Service
public class ProductServiceImpl implements ProductService {
    protected static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    ProductDataClient productDataClient;

    @Autowired
    KafkaSender kafkaSender;


    @Override
    public Product getProductDetailsByProductId(String productId) throws ProductNotFoundException {
        String productName = null;
        Product product = null;

        try {
            productName = productDataClient.gerProductDetails(productId);

            Price price = priceRepository.findPriceByProductId(productId);
            if (price == null) {
                LOGGER.debug("Price information is not found in DB !");
                throw new ProductNotFoundException(HttpStatus.NOT_FOUND.value(),
                        "Price information is not found in DB");
            }
            product = new Product(productId, productName, price);
        } catch (ProductNotFoundException ex) {
            throw ex;
        }
        return product;
    }

    @Override
    public boolean isValidProduct(String productId) {
        Price priceObjectInDB = priceRepository.findPriceByProductId(productId);
        if (priceObjectInDB != null) {
            return true;
        }
        return false;
    }

    @Override
    public Price updatePrice(Price newPriceObj) throws PriceNotFoundException {
        if (newPriceObj != null && StringUtils.isNotBlank(newPriceObj.getProductId())) {
            priceRepository.save(newPriceObj);
            kafkaSender.sendData(newPriceObj.toString());
            return newPriceObj;
        } else {
            throw new PriceNotFoundException(HttpStatus.NO_CONTENT.value(), "Price Information is not found for Product " + newPriceObj.getProductId());
        }
    }
}
