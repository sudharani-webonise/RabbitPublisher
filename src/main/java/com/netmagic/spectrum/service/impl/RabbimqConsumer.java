package com.netmagic.spectrum.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * 
 * The class RabbimqConsumer implements {@link MessageListener} this
 * continuously listen to the rabbitmq and notifies the user if any new message
 * caught from any queue which are the SimpleMessageListenerContainer beans
 * listening to.
 * 
 * @author webonise
 * @version Spectrum 1.0.0
 */
public class RabbimqConsumer implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbimqConsumer.class);

    @Override
    public void onMessage(Message message) {
        logger.info("From QUEUE == {} ", message.getMessageProperties().getReceivedRoutingKey());
        logger.info("Message == {} ", new String(message.getBody()));
        logger.info(message.toString());
    }

}