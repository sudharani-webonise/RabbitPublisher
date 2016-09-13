package com.netmagic.spectrum.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netmagic.spectrum.exception.RabbitmqException;
import com.netmagic.spectrum.service.RabbtimqConsumer;

/**
 * 
 * The class RabbimqConsumer implements {@link MessageListener} this
 * continuously listen to the rabbitmq and notifies the user if any new message
 * caught from any queue which are the SimpleMessageListenerContainer beans
 * listening to.
 * 
 * @author Sudharani
 * 
 */
@Service
public class RabbtimqConsumerImpl implements RabbtimqConsumer, MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbtimqConsumerImpl.class);

    @Autowired
    private SimpleMessageListenerContainer messageListener;

    @Override
    public String publishQueue(String queueName) throws RabbitmqException {
        try {

            if ( queueName != null && !queueName.isEmpty() && !isQueueInListener(queueName) ) {
                messageListener.addQueueNames(queueName);
                return "home";
            } else {
                logger.warn("queue name cannot be null/empty or queue already in message listener");
                return "home";
            }
        } catch (RabbitmqException ex) {
            logger.error("ERROR IN PUBLISHING MESSAGE: ", ex);
            return "error";
        }

    }

    private boolean isQueueInListener(String queueName) {
        for ( String queue : messageListener.getQueueNames() ) {
            if ( queue.equals(queueName) ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onMessage(Message message) {
        try {
            if ( message == null ) {
                logger.info("no messgage received");
            }
            logger.info("From QUEUE == {} ", message.getMessageProperties().getReceivedRoutingKey());
            logger.info("Message == {} ", new String(message.getBody()));
            logger.info(message.toString());
        } catch (Exception ex) {
            logger.error("RABBIT-MQ BROKER SERVICE CONNECTION FAILED  : ", ex);
        }

    }
}
