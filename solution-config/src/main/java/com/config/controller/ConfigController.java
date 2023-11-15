package com.config.controller;

import com.config.service.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/configs")
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigService configService;

    @GetMapping
    public ResponseEntity<byte[]> getConfig(@RequestParam(value = "path") String path) {
        return configService.getConfig(path);
    }

    @GetMapping("/refresh")
    public void refresh() {
        // read client actuator endpoint url
        // call to refresh
        configService.refresh("http://localhost:8080/actuator/");
    }

}
