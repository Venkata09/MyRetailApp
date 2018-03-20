# MyRetailApp

Cassandra related configuration. 

CREATE KEYSPACE myretailapp
  WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };
  
CREATE TABLE myretailapp.price (
productid text,
price text,
currency text,
PRIMARY KEY (productid)
);  


insert into myretailapp.price(productid, price, currency) values ('13860428', '500', 'INR');

cqlsh:myretailapp> select * from myretailapp.price;

 productid | currency | price
-----------+----------+-------
  13860428 |      INR |   500

(1 rows)
cqlsh:myretailapp> select * from myretailapp.price;

                                                                      
