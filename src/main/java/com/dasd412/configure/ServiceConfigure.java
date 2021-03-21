package com.dasd412.configure;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;

@Configuration
public class ServiceConfigure {

  @Bean
  public MessageSourceAccessor messageSourceAccessor(MessageSource messageSource) {
    //messages.properties 읽어 오기
    MessageSourceAccessor messageSourceAccessor = new MessageSourceAccessor(messageSource);
    return messageSourceAccessor;
  }
}
