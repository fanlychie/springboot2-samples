package org.fanlychie.testing.sample.service;

import org.fanlychie.testing.sample.model.Message;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MessageService {

    public boolean verify(Message message) {
        return message != null && StringUtils.hasText(message.getContent());
    }

}