package org.fanlychie.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.fanlychie.kafka.constant.TopicConstant;
import org.fanlychie.kafka.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class KafkaMessageProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void send(Message message) {
        String data = null;
        try {
            data = new ObjectMapper().writeValueAsString(message);
        } catch (JsonProcessingException e) {
            log.error("对象转JSON异常", e);
        }
        ListenableFuture<SendResult<String, Object>> listenableFuture = kafkaTemplate.send(TopicConstant.TOPIC_TEST1, data);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("消息发送失败", ex);
            }
            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info(">>>>>>>> 消息发送成功: {}", result);
            }
        });

    }

}