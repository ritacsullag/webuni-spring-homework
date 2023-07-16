package com.csullagrita.school.config;

import com.csullagrita.school.model.PaymentMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.ConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class JmsConfig {
    @Bean
    public MessageConverter jacksonMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        //Because: Caused by: org.springframework.jms.support.converter.MessageConversionException: Failed to resolve type id [com...]
        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("JMS_TYPE", PaymentMessage.class);
        converter.setTypeIdMappings(typeIdMappings);

        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("JMS_TYPE");
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        ((SingleConnectionFactory) connectionFactory).setClientId("school");
        factory.setSubscriptionDurable(true);
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}
