package com.config.productconfig.controller;

import com.config.productconfig.entity.SpringConfig;
import com.config.productconfig.service.SpringConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/custom-spring-configs/")
public class SpringConfigController {

    private final SpringConfigService springConfigService;

    @RequestMapping("/{appName}/{activeProfile}")
    public SpringConfig getSpringConfig(@PathVariable String appName, @PathVariable String activeProfile) {
        return springConfigService.read(appName, activeProfile);
    }
}
