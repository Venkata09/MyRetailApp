package com.myRetail.app.repository;


import com.myRetail.app.model.Price;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


/**
 * @author vdokku
 */
public interface PriceRepository extends CrudRepository<Price, String> {


    @Query(value = "SELECT * FROM price WHERE productId=?0")
    Price findPriceByProductId(String productId);


    @Query(value = "SELECT * FROM price ")
    List<Price> findListOfPrices(String productId);


}
