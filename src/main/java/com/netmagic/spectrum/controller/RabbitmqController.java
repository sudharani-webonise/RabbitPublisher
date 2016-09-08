package com.netmagic.spectrum.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.netmagic.spectrum.service.RabbitmqProducer;

@Controller
public class RabbitmqController {

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqController.class);

    @Autowired
    private RabbitmqProducer rabbimqProducer;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String rabbitmqProducer(@RequestParam("queueName") String queueName, @RequestParam("message") String message) {

        logger.info("SENDING MESSAGE ");
        rabbimqProducer.sendMessage(queueName, message);
        logger.info("SENT MESSAGE ");
        return "sent";
    }

}
