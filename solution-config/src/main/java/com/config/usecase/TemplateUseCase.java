package com.config.usecase;

import com.config.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemplateUseCase {

    private final TemplateService templateService;

    public ResponseEntity<byte[]> downloadTemplate(String productId,
                                                   String mainTemplateId,
                                                   String templateType,
                                                   String templateId,
                                                   String language) {
        if ("global".equalsIgnoreCase(templateType)) {
            return templateService.getGlobalTemplate(templateId, language);
        } else if ("include".equalsIgnoreCase(templateType)) {
            return templateService.getProductInclusionTemplate(productId, mainTemplateId, templateId, language);
        }

        // by default: main template
        return templateService.getProductTemplate(productId, mainTemplateId, language);
    }

}
