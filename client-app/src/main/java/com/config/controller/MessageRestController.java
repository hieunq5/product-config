package com.config.controller;

import com.config.service.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequiredArgsConstructor
public class MessageRestController {

    private final ConfigService messageConfigService;

    @RequestMapping("/both")
    public String getInBothProfiles() {
        return messageConfigService.getInBothProfiles();
    }

    @RequestMapping("/default")
    public String getOnlyInDefaultProfile() {
        return messageConfigService.getOnlyInDefaultProfile();
    }

    @RequestMapping("/local")
    public String getOnlyInLocalProfile() {
        return messageConfigService.getOnlyInLocalProfile();
    }
}
