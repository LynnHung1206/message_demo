package com.lynn.message_demo.config;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.jackson.ModelObjectMapper;
import com.lynn.message_demo.properties.SelfLineProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Lynn on 2024/11/26
 */
@Configuration
@EnableConfigurationProperties(SelfLineProperties.class)
public class MessageDemoConfig {
  @Bean
  public ObjectMapper lineObjectMapper(){
    return ModelObjectMapper.createNewObjectMapper()
        .configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
  }


}
