package org.fanlychie.property.sample.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created by fanlychie on 2019/6/18.
 */
@Data
@Component
@ConfigurationProperties("contact")
@Validated // 开启校验
public class ContactJSR303Bean {

    @NotBlank(message = "联系人姓名不能为空")
    private String name;

    @Email(message = "联系人邮箱不能为空")
    private String mail;

    @Size(min = 10, max = 18, message = "联系人地址长度应在[10,18]区间")
    private String addr;

}