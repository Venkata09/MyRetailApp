# MyRetailApp

Cassandra related configuration. 

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
                                                                      

### Event drived version of the existing services.

Enhance the application with the event based architecture
Ex: Once an update is made on the product price, the series of events should be triggered and store the messages to KAFKA. And the downstream applications will be utilizing the information i.e. for performing the analytics.


![More Enhanced Version.](https://i2.wp.com/venkatad.files.wordpress.com/2018/03/enhanced_myretail_app.jpg?ssl=1&w=450)




