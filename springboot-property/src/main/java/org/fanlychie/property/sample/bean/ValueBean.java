package org.fanlychie.property.sample.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by fanlychie on 2019/6/18.
 */
@Data
@Component
public class ValueBean {

    @Value("${project}")
    private String project;

    @Value("${author-email}")
    private String authorEmail;

}