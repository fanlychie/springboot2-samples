package org.fanlychie.property.sample.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fanlychie on 2019/6/18.
 */
@Data
@Component
@ConfigurationProperties("visitor")
public class VisitorBean {

    private String username;

    private String password;

    private String[] permissions;

    private String[] buttons;

    private List<String> mediaTypes;

    private List<Menu> menus;

    private Info info;

    @Data
    public static class Menu {

        private String name;

        private String url;

    }

    @Data
    public static class Info {

        private String appName;

        private String appVersion;

    }

}