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

import com.netmagic.spectrum.commons.ViewName;
import com.netmagic.spectrum.exception.RabbitmqException;
import com.netmagic.spectrum.service.RabbitmqProducer;

@Service
public class RabbitmqProducerImpl implements RabbitmqProducer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqProducerImpl.class);

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TopicExchange topicExchaneName;

    private Queue queue;

    @Override
    public String sendMessage(String queueName, String message) {
        try {
            if ( queueName != null && !queueName.isEmpty() ) {
                queue = new Queue(queueName);
                rabbitAdmin.declareQueue(queue);
                rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(topicExchaneName).with(queueName));
                rabbitTemplate.convertAndSend(topicExchaneName.getName(), queueName, message);
                logger.info(" MESSAGE SENT TO QUEUE = {}", queueName);
                return ViewName.SENT.getViewName();
            } else {
                logger.warn(" unable to send queue is null or empty");
                return ViewName.WARNING.getViewName();
            }
        } catch (Exception ex) {
            logger.error("ERROR OCCURED WHILE SENDING MESSAGE : ", ex);
            throw new RabbitmqException("Error in sending message to the queue or connection failed");
        }
    }
}
