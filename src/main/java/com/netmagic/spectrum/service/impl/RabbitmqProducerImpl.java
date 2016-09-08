package com.netmagic.spectrum.service.impl;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netmagic.spectrum.service.RabbitmqProducer;

@Service
public class RabbitmqProducerImpl implements RabbitmqProducer {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange topicExchaneName;

    @Override
    public void sendMessage(String queueName, String message) {
        Queue queue = new Queue(queueName);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(topicExchaneName);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(topicExchaneName).with(queueName));
        template.convertAndSend(topicExchaneName.getName(), queueName, message);
    }

}
