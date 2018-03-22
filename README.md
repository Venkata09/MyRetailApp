# MyRetailApp

Technical stack used:
1. Java 
1. Cassandra 
1. Spring-boot
1. Kafka

Cassandra related configuration. 

This configuration is needed inorder to pass the Integration testing. 

```
CREATE KEYSPACE myretailapp
  WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };
  
CREATE TABLE myretailapp.price (
productid text,
price text,
currency text,
PRIMARY KEY (productid)
);  

insert into myretailapp.price(productid, price, currency) values ('13860428', '50', 'USD');

cqlsh:myretailapp> select * from myretailapp.price;

productid | currency | price
-----------+----------+-------
 13860428 |      USD |   50

(1 rows)

cqlsh:myretailapp> select * from myretailapp.price;
```
                                                                      
### Recommendations
1) Use of Eureka server will provide load balancing which decreases the latency and improves fault-tolerance.
   1. To implement this Start a Eureka Server instance.
   1. Register multiple instances of the redsky.
   1. Enable Eureka client discovery in client application (MyRetailApp), based on the availability zone client application will contact one of the instances of redsky application.
2)	Use Kafka for storing updates for price. So that the downstream application will be notified. This encourages the event driven architecture.

### Event drived version of the existing services.

Enhance the application with the event based architecture

Ex: Once an update is made on the product price, the series of events should be triggered and store the messages to KAFKA. And the downstream applications such as inventory or stock services will get this update by listening to kafka. With this design, microservices will be better communicated independently in a decouple way. 


![More Enhanced Version.](https://i2.wp.com/venkatad.files.wordpress.com/2018/03/enhanced_myretail_app.jpg?ssl=1&w=450)



#### Post Request

Post request on updating the price. Updating the price to 53

![UpdateRequest.](https://i1.wp.com/venkatad.files.wordpress.com/2018/03/postrequest_updateprice.png?ssl=1&w=450)

Post request Successfully updated the price to 53. 

![UpdateRequest_SuccessFul.](https://i0.wp.com/venkatad.files.wordpress.com/2018/03/postrequest_successfull.png?ssl=1&w=450)

Price update is sent to Kafka. 

![UpdateRequestStoredToKafka.](https://i1.wp.com/venkatad.files.wordpress.com/2018/03/postrequest_updatetokafka.png?ssl=1&w=450)