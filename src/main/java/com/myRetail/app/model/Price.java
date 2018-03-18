package com.myRetail.app.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * @author vdokku
 */

@Table
public class Price {

    @PrimaryKey
    private String productId;
    private String price;
    private String currency;

    public Price(String productId, String price, String currency) {
        this.productId = productId;
        this.price = price;
        this.currency = currency;
    }


    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Price{" +
                "productId='" + productId + '\'' +
                ", price='" + price + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
