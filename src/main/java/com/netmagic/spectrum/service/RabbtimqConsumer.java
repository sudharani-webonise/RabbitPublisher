package com.netmagic.spectrum.service;

import org.springframework.stereotype.Service;

import com.netmagic.spectrum.exception.RabbitmqException;

@Service
public interface RabbtimqConsumer {

    /**
     * Method is used publish queue dynamically on rabbitmq listener
     * 
     * @param queueName
     * @return
     * @throws RabbitmqException
     */
    String publishQueue(String queueName) throws RabbitmqException;

}
