package com.netmagic.spectrum.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netmagic.spectrum.exception.RabbitmqException;
import com.netmagic.spectrum.service.RabbitmqProducer;

@Service
public class RabbitmqProducerImpl implements RabbitmqProducer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqProducerImpl.class);

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange topicExchaneName;

    private Queue queue;

    @Override
    public void sendMessage(String queueName, String message) {
        try {
            queue = new Queue(queueName);
            rabbitAdmin.declareQueue(queue);
            rabbitAdmin.declareExchange(topicExchaneName);
            rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(topicExchaneName).with(queueName));
            template.convertAndSend(topicExchaneName.getName(), queueName, message);
        } catch (Exception ex) {
            logger.error("ERROR OCCURED WHILE SENDING MESSAGE : ", ex);
            throw new RabbitmqException("Error in sending message to the queue or connection failed");
        }
    }
}
