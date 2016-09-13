package com.netmagic.spectrum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

import com.netmagic.spectrum.rabbitmq.RabbitMQConfig;

@SpringBootApplication
@Import({ WebConfig.class, RabbitMQConfig.class })
public class Application extends SpringBootServletInitializer {
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
