package com.config.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;


/**
 * Beans are only initiated once the app is get started.
 * When calling actuator/refresh, beans are not reload.
 */
@Service
@ConditionalOnProperty(prefix = "config", name = "data-provider", havingValue = "stubbed", matchIfMissing = true)
public class StubbedMessageConfigService implements ConfigService {

    @Override
    public String getInBothProfiles() {
        return "inBothProfiles";
    }

    @Override
    public String getOnlyInDefaultProfile() {
        return "onlyInDefaultProfile";
    }

    @Override
    public String getOnlyInLocalProfile() {
        return "onlyInLocalProfile";
    }
}
