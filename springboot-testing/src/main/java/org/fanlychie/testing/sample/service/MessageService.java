package org.fanlychie.testing.sample.service;

import org.fanlychie.testing.sample.model.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    public Message hello() {
        Message message = new Message();
        message.setId(1001);
        message.setContent("Hello");
        return message;
    }

}