package org.fanlychie.kafka.producer;

import org.fanlychie.kafka.KafkaApplication;
import org.fanlychie.kafka.model.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KafkaApplication.class})
public class KafkaMessageProducerTest {

    @Autowired
    private KafkaMessageProducer kafkaMessageProducer;

    @Test
    public void testSend() throws InterruptedException {
        Message message = new Message();
        message.setId(10086);
        message.setContext("Hello Kafka");
        kafkaMessageProducer.send(message);
        TimeUnit.SECONDS.sleep(6);
    }

}