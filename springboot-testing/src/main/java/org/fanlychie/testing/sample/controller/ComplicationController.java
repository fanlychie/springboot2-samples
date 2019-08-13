package org.fanlychie.testing.sample.controller;

import org.fanlychie.testing.sample.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ComplicationController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/example1")
    public Map<String, Object> example1() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", messageService.hello());
        return map;
    }

}
