package com.daniel.zielinski.zipkin.config;

import brave.spring.rabbit.SpringRabbitTracing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "spring.zipkin", name = "enabled", havingValue = "true")
class ZipkinRabbitMqConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, SpringRabbitTracing springRabbitTracing) {
        log.info("Registering rabbitmq producer zipkin tracing");
        return springRabbitTracing.newRabbitTemplate(connectionFactory);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                               SpringRabbitTracing springRabbitTracing) {
        log.info("Registering rabbitmq consumer zipkin tracing");
        return springRabbitTracing.newSimpleRabbitListenerContainerFactory(connectionFactory);
    }
}