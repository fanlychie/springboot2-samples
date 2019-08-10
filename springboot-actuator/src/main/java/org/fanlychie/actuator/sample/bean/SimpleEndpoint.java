package org.fanlychie.actuator.sample.bean;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fanlychie on 2019/6/23.
 */
@Component
@Endpoint(id = "simple")
public class SimpleEndpoint {

    @ReadOperation
    public Object simple(@Selector String message) {
        Map<String, Object> data = new HashMap<>();
        data.put("message", message);
        data.put("description", "it is a simple endpoint");
        return data;
    }

}