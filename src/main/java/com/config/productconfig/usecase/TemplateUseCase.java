package com.config.productconfig.usecase;

import com.config.productconfig.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemplateUseCase {

    private final TemplateService templateService;

    public ResponseEntity<byte[]> downloadTemplate(String productId, String templateId, String templateType, String language) {
        if ("global".equalsIgnoreCase(templateType)) {
            return templateService.getGlobalTemplate(templateId, language);
        } else if ("include".equalsIgnoreCase(templateType)) {
            return templateService.getProductInclusionTemplate(productId, templateId, language);
        }

        return templateService.getProductTemplate(productId, templateId, language);
    }

}