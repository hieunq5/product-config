package com.config.service;

public interface ConfigService {

    String getInBothProfiles();

    String getOnlyInDefaultProfile();

    String getOnlyInLocalProfile();
}
