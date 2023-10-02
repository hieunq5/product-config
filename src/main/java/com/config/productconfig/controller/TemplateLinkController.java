package com.config.productconfig.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@RestController
@RequestMapping("/links/")
@RequiredArgsConstructor
public class TemplateLinkController {

    @GetMapping("/generate")
    public ResponseEntity<String> getTemplate(@RequestParam(value = "product", required = false) String product,
                                              @RequestParam(value = "template", required = false) String template,
                                              @RequestParam(value = "language", required = false) String language) {
        String link = MessageFormat.format("http://localhost:8888/products/{0}/templates/{1}?language={2}", product, template, language);
        return new ResponseEntity<>(link, new HttpHeaders(), HttpStatus.OK);
    }

}
