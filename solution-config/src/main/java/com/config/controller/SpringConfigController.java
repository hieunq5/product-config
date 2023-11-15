package com.config.controller;

import com.config.entity.SpringConfig;
import com.config.service.SpringConfigService;
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
    public SpringConfig getConfig(@PathVariable String appName, @PathVariable String activeProfile) {
        return springConfigService.read(appName, activeProfile);
    }
}
