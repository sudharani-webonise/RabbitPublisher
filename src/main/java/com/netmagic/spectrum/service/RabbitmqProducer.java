package com.netmagic.spectrum.service;

import org.springframework.stereotype.Service;

import com.netmagic.spectrum.exception.RabbitmqException;

@Service
public interface RabbitmqProducer {

    /**
     * This method is binds the routing key as queue with exchange topic define
     * by the system and sends the message to queue.
     * 
     * @param queueName
     * @param message
     * @throws RabbitmqException
     * 
     * @author Sudharani
     */

    String sendMessage(String queueName, String message) throws RabbitmqException;

}
