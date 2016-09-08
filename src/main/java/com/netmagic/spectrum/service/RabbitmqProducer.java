package com.netmagic.spectrum.service;

import org.springframework.stereotype.Service;

@Service
public interface RabbitmqProducer {

    void sendMessage(String queueName, String message);

}
