package com.config.productconfig.controller;

import com.config.productconfig.usecase.TemplateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products/{productId}/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateUseCase templateUseCase;

    @GetMapping("/{templateId}")
    public ResponseEntity<byte[]> getTemplate(@PathVariable String productId,
                                              @PathVariable String templateId,
                                              @RequestParam(value = "templateType", required = false) String templateType,
                                              @RequestParam(value = "language") String language) {
        return templateUseCase.downloadTemplate(productId, templateId, templateType, language);
    }

}
