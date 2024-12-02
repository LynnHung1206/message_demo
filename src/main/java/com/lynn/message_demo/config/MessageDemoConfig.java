package com.lynn.message_demo.config;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.jackson.ModelObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Lynn on 2024/11/26
 */
@Configuration
public class MessageDemoConfig {
  @Bean
  public ObjectMapper lineObjectMapper(){
    return ModelObjectMapper.createNewObjectMapper()
        .configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
  }


}
