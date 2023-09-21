package com.config.productconfig.controller;

import com.config.productconfig.usecase.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/configs")
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigService configService;

    @GetMapping("/{product}/configs")
    public ResponseEntity<byte[]> getProductConfigs(@PathVariable String product) {
        return configService.getProductConfigs(product);
    }

}
