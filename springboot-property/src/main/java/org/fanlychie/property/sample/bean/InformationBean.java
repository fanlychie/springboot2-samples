package org.fanlychie.property.sample.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by fanlychie on 2019/6/18.
 */
@Data
@Component
@PropertySource("classpath:information.properties")
@ConfigurationProperties("my")
public class InformationBean {

    private String name;

    private String mail;

}