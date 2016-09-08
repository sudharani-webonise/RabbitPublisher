package com.netmagic.spectrum.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netmagic.spectrum.service.impl.RabbimqConsumer;

/**
 * 
 * The class RabbitmqConfig is creates the connection factory and and rabbit
 * template which is used to send the message to queue with exchange topic bean
 * which is binds with using rabbit admin bean, and also a listener which
 * continuously listens to the defined queues.
 * 
 * @see EnableRabbit
 * 
 * @author webonise
 * @version Spectrum 1.0.0
 */

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);

    private static final String USER_NAME = "guest";
    private static final String PASSWORD = "guest";
    private static final String RABBIT_HOST = "localhost";

    private static final String QUEUE_NAME_TEST = "ticket";
    private static final String QUEUE_NAME_MYQUEUE = "outstanding";

    private static final String NM_TOPIC_NANE = "MyNetmagic";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(RABBIT_HOST);
        connectionFactory.setUsername(USER_NAME);
        connectionFactory.setPassword(PASSWORD);
        logger.info("connection successfully established");
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

    @Bean
    public Queue queueTest() {
        return new Queue(QUEUE_NAME_TEST);
    }

    @Bean
    public Queue queueMyQueue() {
        return new Queue(QUEUE_NAME_MYQUEUE);
    }

    @Bean
    public SimpleMessageListenerContainer listenerContainer() {
        SimpleMessageListenerContainer messageListener = new SimpleMessageListenerContainer();
        messageListener.setConnectionFactory(connectionFactory());
        messageListener.setQueues(queueTest(), queueMyQueue());
        messageListener.setMessageListener(new RabbimqConsumer());
        messageListener.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return messageListener;
    }

}
