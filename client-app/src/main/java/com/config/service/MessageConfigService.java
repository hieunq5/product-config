package com.config.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * Beans are only initiated once the app is get started.
 * When calling actuator/refresh, beans are not reload.
 */
@Service
@ConditionalOnProperty(prefix = "config", name = "data-provider", havingValue = "default", matchIfMissing = true)
public class MessageConfigService implements ConfigService {

    @Value("${config.in-both-profiles:in-both-profiles CLIENT}")
    private String inBothProfiles;

    @Value("${config.only-in-default-profile:only-in-default-profile}")
    private String onlyInDefaultProfile;

    @Value("${config.only-in-local-profile:only-in-local-profile}")
    private String onlyInLocalProfile;

    @Override
    public String getInBothProfiles() {
        return this.inBothProfiles;
    }

    @Override
    public String getOnlyInDefaultProfile() {
        return this.onlyInDefaultProfile;
    }

    @Override
    public String getOnlyInLocalProfile() {
        return this.onlyInLocalProfile;
    }
}
