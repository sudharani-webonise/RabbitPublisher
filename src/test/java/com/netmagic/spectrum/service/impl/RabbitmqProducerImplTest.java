package com.netmagic.spectrum.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.netmagic.spectrum.commons.ViewName;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ RabbitmqProducerImpl.class })
public class RabbitmqProducerImplTest {

    private static final String TOPIC_EXCHANGE_NAME = "Demo";

    private static final String QUEUE_NAME = "test";

    private static final String MESSAGE = "hi tesing!";

    private RabbitmqProducerImpl rabbitmqProducer;

    private RabbitAdmin rabbitAdminMock;

    private RabbitTemplate templateMock;

    private TopicExchange topicExchaneNameMock;

    private CachingConnectionFactory connectionFactory;

    private Queue queue;

    @Before
    public void setUp() {
        rabbitmqProducer = PowerMockito.spy(new RabbitmqProducerImpl());
        queue = PowerMockito.spy(new Queue(QUEUE_NAME));
        connectionFactory = PowerMockito.spy(new CachingConnectionFactory());
        rabbitAdminMock = PowerMockito.spy(new RabbitAdmin(connectionFactory));
        templateMock = PowerMockito.spy(new RabbitTemplate(connectionFactory));
        topicExchaneNameMock = PowerMockito.spy(new TopicExchange(TOPIC_EXCHANGE_NAME));
        Whitebox.setInternalState(rabbitmqProducer, "rabbitAdmin", rabbitAdminMock);
        Whitebox.setInternalState(rabbitmqProducer, "template", templateMock);
        Whitebox.setInternalState(rabbitmqProducer, "topicExchaneName", topicExchaneNameMock);
    }

    @Test
    public void testSentToEmptyqueue() {
        String viewName = rabbitmqProducer.sendMessage(Mockito.anyString(), Mockito.anyString());
        assertEquals(viewName, ViewName.WARNING.getViewName());
    }

    @Test
    public void testSentToqueue() {
        rabbitAdminMock.declareQueue(queue);
        rabbitAdminMock.declareExchange(topicExchaneNameMock);
        rabbitAdminMock.declareBinding(BindingBuilder.bind(queue).to(topicExchaneNameMock).with(QUEUE_NAME));
        templateMock.convertAndSend(topicExchaneNameMock.getName(), QUEUE_NAME, MESSAGE);
        String viewName = rabbitmqProducer.sendMessage(QUEUE_NAME, MESSAGE);
        assertEquals(viewName, ViewName.SENT.getViewName());
    }
}
