package com.config.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private static final String CONTENT_DISPOSITION_HEADER = "Content-Disposition";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";


    @Value("${solution-config.root.path}")
    private String clientConfigRootPath;

    @Value("${product.product-config-folder-name}")
    private String productConfigFolderName;

    @Value("${document-template.global-template-folder-name}")
    private String globalTemplateFolderName;

    @Value("${document-template.product-template-folder-name}")
    private String productTemplateFolderName;

    @Value("${document-template.product-inclusion-template-folder-name}")
    private String productInclusionTemplateFolderName;

    public ResponseEntity<byte[]> getGlobalTemplate(@NonNull String templateId, @NonNull String language) {
        language = language.trim().toUpperCase();
        String templateName = String.join(".", templateId, "docx");
        Path templatePath = Paths.get(clientConfigRootPath, productConfigFolderName, globalTemplateFolderName, language, templateName);
        return toResponseEntity(templatePath, templateName);
    }

    public ResponseEntity<byte[]> getProductTemplate(@NonNull String productId, @NonNull String templateId, @NonNull String language) {
        language = language.trim().toUpperCase();
        String templateName = String.join(".", templateId, "docx");
        Path templatePath = Paths.get(clientConfigRootPath, productConfigFolderName, productId, productTemplateFolderName, language, templateName);
        return toResponseEntity(templatePath, templateName);
    }

    public ResponseEntity<byte[]> getProductInclusionTemplate(@NonNull String productId, @NonNull String mainTemplateId, @NonNull String templateId, @NonNull String language) {
        // validate @mainTemplateId
        language = language.trim().toUpperCase();
        String templateName = String.join(".", templateId, "docx");
        Path templatePath = Paths.get(clientConfigRootPath, productConfigFolderName, productId, productTemplateFolderName, language, productInclusionTemplateFolderName, templateName);
        return toResponseEntity(templatePath, templateName);
    }

    private ResponseEntity<byte[]> toResponseEntity(Path filePath, String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_DISPOSITION_HEADER, "attachment; filename=" + fileName);
        headers.add(CONTENT_TYPE_HEADER, "application/msword");

        try {
            File file = new File(filePath.toAbsolutePath().toString());
            InputStream targetStream = new FileInputStream(file);
            return new ResponseEntity<>(IOUtils.toByteArray(targetStream), headers, HttpStatus.OK);
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new ServerErrorException("Unable to read attachment.", exception);
        }
    }

}
