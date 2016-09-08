package com.netmagic.spectrum.rabbitmq;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * The class RabbitmqConfig is creates the connection factory bean and listener
 * bean which continuously listens to the rabbitmq connection factory and
 * notifies to {@link RabbimqConsumer}
 * 
 * @see EnableRabbit
 * 
 * @author sudharani
 */

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    private static final String USER_NAME = "guest";
    private static final String PASSWORD = "guest";
    private static final String RABBIT_HOST = "localhost";

    private static final String NM_TOPIC_NANE = "MyNetmagic";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(RABBIT_HOST);
        connectionFactory.setUsername(USER_NAME);
        connectionFactory.setPassword(PASSWORD);
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(NM_TOPIC_NANE);
    }

}
