package com.config.productconfig.service;

import com.config.productconfig.entity.PropertySource;
import com.config.productconfig.entity.SpringConfig;
import com.config.productconfig.utils.YAMLReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SpringConfigService {

    private static final String DEFAULT = "default";

    public SpringConfig read(String appName, String profileName) {
        PropertySource defaultPropertySource = readByProfileName(DEFAULT);
        PropertySource propertySource = readByProfileName(profileName);
        return SpringConfig.builder()
                           .name(appName)
                           .profiles(Arrays.asList(profileName, DEFAULT))
                           .label(null)
                           .version("v1")
                           .state(null)
                           .propertySources(Arrays.asList(propertySource, defaultPropertySource))
                           .build();
    }

    private PropertySource readByProfileName(String profileName) {
        String springProfileFileName = DEFAULT.equalsIgnoreCase(profileName) ? "application.yml" : "application-" + profileName + ".yml";
        Path applicationYmlPath = Paths.get("src", "main", "resources", "spring-configs", springProfileFileName);
        File file = new File(applicationYmlPath.toAbsolutePath().toString());
        YAMLReader yamlR = new YAMLReader(file);
        Map<String, Object> source = yamlR.readAsFlattenMap();
        return PropertySource.builder()
                             .name(profileName)
                             .source(source)
                             .build();
    }
}
