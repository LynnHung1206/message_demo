package com.lynn.message_demo.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: Lynn on 2024/12/31
 */
@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "self.line")
public class SelfLineProperties {

  private String auth;

  private String token;

}
