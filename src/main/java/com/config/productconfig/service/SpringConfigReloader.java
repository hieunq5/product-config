package com.config.productconfig.service;

import org.springframework.stereotype.Service;

@Service
public class SpringConfigReloader {

//    @PostConstruct
    public void reloadSpringConfig() {
        // read global config
        // refresh client config: call actuator/refresh -> notify
    }

    // ConditionalOnBean/Property -> stubbedMode

}
