package com.myRetail.app.client;


import com.google.gson.JsonObject;
import com.myRetail.app.config.RestTemplateConfiguration;
import com.myRetail.app.exception.ProductNotFoundException;
import com.myRetail.app.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author vdokku
 */


@Component
public class ProductDataClient {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ProductDataClient.class);

    @Autowired
    private RestTemplateConfiguration restTemplateConfiguration;

    @Value("${BASE_URL}")
    private String productDataURL;

    private String product_URI = "/v2/pdp/tcin/";

    public String gerProductDetails(String productId) throws ProductNotFoundException {

        String productName = null;

        try {
            UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(productDataURL + product_URI + productId)
                    .queryParam("excludes", "taxonomy,price,promotion,bulk_ship,rating_and_review_reviews," +
                            "rating_and_review_statistics,question_answer_statistics");


            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<?> entity = new HttpEntity<String>(header);
            ResponseEntity<String> responseEntity = restTemplateConfiguration.
                    restTemplate().exchange(urlBuilder.toUriString().trim(),
                    HttpMethod.GET, entity, String.class);

            String response = responseEntity.getBody();

            if (StringUtils.isNotBlank(response)) {
                JsonObject jsonObject = JsonUtils.generateJson(response);

                if (jsonObject.getAsJsonObject("product").getAsJsonObject("item").getAsJsonObject("product_description") != null) {
                    JsonObject productDescrition = jsonObject.getAsJsonObject("product").getAsJsonObject("item").getAsJsonObject("product_description");
                    productName = productDescrition.get("title").getAsString();
                } else {
                    LOGGER.debug(" Product Description is not available : ");
                    throw new ProductNotFoundException(HttpStatus.NO_CONTENT.value(), "Title does not exists for this product" + productId);
                }
            }
        } catch (RestClientException e) {
            LOGGER.debug("Exception accessing the RestService. URL : " + productDataURL + " for Product_ID : " + productId);
            throw new ProductNotFoundException(HttpStatus.NOT_FOUND.value(), "Product Remote API unavailable");
        }
        return productName;
    }
}
