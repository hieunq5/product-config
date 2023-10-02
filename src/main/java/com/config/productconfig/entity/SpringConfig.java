package com.config.productconfig.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpringConfig {
    private String name;
    private List<String> profiles;
    private String label;
    private String version;
    private String state;
    private List<PropertySource> propertySources;
}
