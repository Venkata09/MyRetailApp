package com.myRetail.app.controller;


import com.myRetail.app.exception.PriceNotFoundException;
import com.myRetail.app.exception.ProductNotFoundException;
import com.myRetail.app.model.Price;
import com.myRetail.app.model.Product;
import com.myRetail.app.service.KafkaSender;
import com.myRetail.app.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author vdokku
 */


@RestController
@RequestMapping("/api")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    @GetMapping(value = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProductDetails(@PathVariable("id") String productId) throws ProductNotFoundException {

        Product product = null;
        product = productService.getProductDetailsByProductId(productId);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping(value = "/product/{id}")
    public ResponseEntity<Price> updatePriceForProduct(@PathVariable("id") String productId, @RequestBody Price newPriceObj) throws PriceNotFoundException, ProductNotFoundException {

        Price updatedPriceObject = null;
        if (newPriceObj == null) {
            throw new IllegalArgumentException();
        }
        boolean isAValidProduct = productService.isValidProduct(newPriceObj.getProductId());

        if (!isAValidProduct) {
            throw new ProductNotFoundException(HttpStatus.NOT_FOUND.value(), "Product detail not available for product id: " + productId);
        }

        updatedPriceObject = productService.updatePrice(newPriceObj);
        return new ResponseEntity<>(updatedPriceObject, HttpStatus.OK);
    }

}
