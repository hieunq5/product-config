package com.config.controller;

import com.config.usecase.TemplateUseCase;
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

    @GetMapping("/{mainTemplateId}")
    public ResponseEntity<byte[]> getTemplate(@PathVariable String productId,
                                              @PathVariable String mainTemplateId,
                                              @RequestParam(value = "templateType", required = false) String templateType,
                                              @RequestParam(value = "templateId", required = false) String templateId,
                                              @RequestParam(value = "language", required = false) String language) {
        return templateUseCase.downloadTemplate(productId, mainTemplateId, templateType, templateId, language);
    }

}
