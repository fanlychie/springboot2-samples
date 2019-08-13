package org.fanlychie.testing.sample.service;

import org.springframework.stereotype.Service;

@Service
public class MessageService {

    public String hello() {
        return "Hello";
    }

}