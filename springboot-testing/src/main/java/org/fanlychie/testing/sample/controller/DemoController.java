package org.fanlychie.testing.sample.controller;

import org.fanlychie.testing.sample.model.Message;
import org.fanlychie.testing.sample.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/info")
    public String info() {
        return "SpringBoot Testing";
    }

    @PostMapping("/echo")
    public String echo(String msg) {
        return msg;
    }

    @PostMapping(value = "/echo/json", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Message echoJson(@RequestBody Message message) {
        return message;
    }

    @PostMapping("/form")
    public String form(Message message) {
        return messageService.verify(message) ? "SUCCESS" : "FAIL";
    }

}