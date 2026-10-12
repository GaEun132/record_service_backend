package com.petlog.record.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.coin.exchange}")
    private String coinExchange;

    @Value("${rabbitmq.coin.earn-queue}")
    private String coinEarnQueue;

    @Value("${rabbitmq.coin.earn-routing-key}")
    private String coinEarnRoutingKey;

    @Bean
    public DirectExchange coinExchange() {
        return new DirectExchange(coinExchange, true, false);
    }

    @Bean
    public Queue coinEarnQueue() {
        return new Queue(coinEarnQueue, true);
    }

    @Bean
    public Binding coinEarnBinding(Queue coinEarnQueue, DirectExchange coinExchange) {
        return BindingBuilder.bind(coinEarnQueue)
                .to(coinExchange)
                .with(coinEarnRoutingKey);
    }

    @Bean
    public MessageConverter rabbitMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter rabbitMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(rabbitMessageConverter);
        return rabbitTemplate;
    }
}
