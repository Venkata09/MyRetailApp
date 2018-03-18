package com.myRetail.app.controller;




import com.myRetail.app.exception.PriceNotFoundException;
import com.myRetail.app.exception.ProductNotFoundException;
import com.myRetail.app.model.Price;
import com.myRetail.app.model.Product;
import com.myRetail.app.service.ProductService;
import org.apache.commons.lang3.StringUtils;
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

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProductDetails(@PathVariable("id") String productId) throws ProductNotFoundException {

        Product product = null;
        try {
            product = productService.getProductDetailsByProductId(productId);
        } catch (Exception ex) {
            LOGGER.error("Product Not Found :-- " + ex);
            throw new ProductNotFoundException();
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> updatePriceForProduct(@PathVariable("id") String productId, @RequestBody Price newPriceObj) throws PriceNotFoundException {

        if (StringUtils.isNotBlank(productId) && productId.equalsIgnoreCase(newPriceObj.getProductId())) {
            productService.updatePrice(newPriceObj);
        } else {
            LOGGER.error("Product ID " + productId + " not is not inline with Price "
                    + newPriceObj.getProductId());
            throw new PriceNotFoundException();
        }
        return new ResponseEntity<String>("Successfully updated the product price : ", HttpStatus.OK);

    }


}
