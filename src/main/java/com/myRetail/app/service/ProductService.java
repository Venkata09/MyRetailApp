package com.myRetail.app.service;

import com.myRetail.app.exception.PriceNotFoundException;
import com.myRetail.app.exception.ProductNotFoundException;
import com.myRetail.app.model.Price;
import com.myRetail.app.model.Product;
import org.springframework.stereotype.Service;

/**
 * @author vdokku
 */

@Service
public interface ProductService {

    Product getProductDetailsByProductId(String productId) throws ProductNotFoundException;

    boolean isValidProduct(String productId);

    Price updatePrice(Price newPriceObj) throws PriceNotFoundException;


}
