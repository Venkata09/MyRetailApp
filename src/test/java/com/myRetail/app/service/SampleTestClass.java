package com.myRetail.app.service;


import com.myRetail.app.client.ProductDataClient;
import com.myRetail.app.model.Price;
import com.myRetail.app.repository.PriceRepository;
import kafka.server.KafkaConfig;
import kafka.server.KafkaServerStartable;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


/**
 * @author vdokku
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class SampleTestClass {

/*
    private static final String TOPIC = "test-topic";
    private static final String BROKERHOST = "127.0.0.1";
    private static final String BROKERPORT = "9092";
    private static final String ZKPORT = "2181";

    private String nodeId = "0";
    private String zkConnect = "localhost:" + ZKPORT;
    private KafkaServerStartable server;
    KafkaProducer<Integer, byte[]> producer;

*/


    private static final String HELLOWORLD_TOPIC = "helloworld.t";

    @ClassRule
    public static KafkaEmbedded embeddedKafka =
            new KafkaEmbedded(1, true, HELLOWORLD_TOPIC);

    @Autowired
    private KafkaSender sender;


    @Autowired
    ProductService productServiceImpl;

    @MockBean
    PriceRepository priceRepositoryMock;

    @MockBean
    ProductDataClient productDataClient;

    @MockBean
    KafkaSender kafkaSenderMock;

//
//    @Before
//    public void setup() throws IOException {
//        //zookeeper
//        startZK();
//        //start kafka
//        startKafka();
//        // setup producer
//        setupProducer();
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        server.shutdown();
//        server.awaitShutdown();
//    }
//
//    private static void startZK() throws IOException {
//        final File zkTmpDir = File.createTempFile("zookeeper", "test");
//        zkTmpDir.delete();
//        zkTmpDir.mkdir();
//
//        new Thread() {
//            @Override
//            public void run() {
//                ZooKeeperServerMain.main(new String[]{ZKPORT, zkTmpDir.getAbsolutePath()});
//            }
//        }.start();
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//        }
//    }
//
//    private void startKafka() {
//        Properties props = new Properties();
//        props.put("broker.id", nodeId);
//        props.put("port", BROKERPORT);
//        props.put("zookeeper.connect", zkConnect);
//        props.put("host.name", "127.0.0.1");
//        KafkaConfig conf = new KafkaConfig(props);
//        server = new KafkaServerStartable(conf);
//        server.startup();
//    }
//
//    private void setupProducer() {
//        Properties producerProps = new Properties();
//        producerProps.setProperty("bootstrap.servers", BROKERHOST + ":" + BROKERPORT);
//        producerProps.setProperty("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
//        producerProps.setProperty("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
//        producer = new KafkaProducer<>(producerProps);
//    }
//

    @Test
    public void validateProductInDBNegativeTestCase() throws Exception {
        Price newPrice = new Price("13860428", "100", "USD");

        when(priceRepositoryMock.findPriceByProductId("13860428")).thenReturn(newPrice);

        when(productDataClient.gerProductDetails("13860428")).thenReturn("The Big Lebowski ");


        boolean isProductInDB = productServiceImpl.isValidProduct("13860429");
        assertEquals(false, isProductInDB);
    }


    @Test
    public void testReceive() throws Exception {
        sender.sendData("Hello Spring Kafka!");
    }

}
