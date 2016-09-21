package com.netmagic.spectrum.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.netmagic.spectrum.commons.ViewName;
import com.netmagic.spectrum.exception.RabbitmqException;
import com.netmagic.spectrum.service.RabbitmqProducer;
import com.netmagic.spectrum.service.RabbtimqConsumer;

@Controller
public class RabbitmqController {

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqController.class);

    @Autowired
    private RabbitmqProducer rabbimqProducer;

    @Autowired
    private RabbtimqConsumer rabbitmqListener;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String rabbitmqProducer(@RequestParam("queueName") String queueName, @RequestParam("message") String message) {
        try {
            return rabbimqProducer.sendMessage(queueName, message);
        } catch (RabbitmqException ex) {
            logger.error("ERROR OCCURED WHILE SENDING MESSAGE : ", ex);
            return ViewName.ERROR.getViewName();
        }
    }

    @RequestMapping(value = "/listen/queue", method = RequestMethod.GET)
    public String publishQueue(@RequestParam("queueName") String queueName) {
        try {
            return rabbitmqListener.listenQueue(queueName);
        } catch (RabbitmqException ex) {
            logger.error("ERROR IN PUBLISHING MESSAGE: ", ex);
            return ViewName.ERROR.getViewName();
        }
    }
}
