package org.fanlychie.property.sample.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by fanlychie on 2019/6/18.
 */
@Data
@Component
@ConfigurationProperties("administrators")
public class AdministratorsBean {

    private String username;

    private String password;

}