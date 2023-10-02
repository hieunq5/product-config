package com.config.productconfig.service;

import com.config.productconfig.utils.CompressUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerErrorException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ConfigService {

    private static final String CONTENT_DISPOSITION_HEADER = "Content-Disposition";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    private static final String TMP_FOLDER_NAME = "tmp";
    private static final String OUTPUT_ZIP_FILE_NAME = "test.zip";

    public ResponseEntity<byte[]> getGlobalConfigs() {
        Path directoryToZipPath = Paths.get("src", "main", "resources", "global-configs");
        String directoryToZip = directoryToZipPath.toAbsolutePath().toString();
        return compressDirectory(directoryToZip);
    }

    public ResponseEntity<byte[]> getGlobalConfig(String configFileName) {
        Path directoryToZipPath = Paths.get("src", "main", "resources", "global-configs", configFileName);
        String directoryToZip = directoryToZipPath.toAbsolutePath().toString();
        return compressDirectory(directoryToZip);
    }

    public ResponseEntity<byte[]> getProductConfigs(String product) {
        Path directoryToZipPath = Paths.get("src", "main", "resources", "product-configs", product);
        String directoryToZip = directoryToZipPath.toAbsolutePath().toString();
        return compressDirectory(directoryToZip);
    }


    public void refresh(String clientActuatorUrl) {
        String refreshEndpointUrl = String.join("/", clientActuatorUrl, "refresh");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        restTemplate.exchange(refreshEndpointUrl, HttpMethod.GET, requestEntity, String.class);
    }

    private ResponseEntity<byte[]> compressDirectory(String directoryToZip) {
        Path tmpFolderPath = Paths.get(TMP_FOLDER_NAME);
        File tmpFolder = new File(tmpFolderPath.toAbsolutePath().toString());
        if (!tmpFolder.exists()) {
            tmpFolder.mkdirs();
        }

        Path tmpZipFilePath = Paths.get(TMP_FOLDER_NAME, OUTPUT_ZIP_FILE_NAME);
        String tmpZipFile = tmpZipFilePath.toAbsolutePath().toString();

        CompressUtils.zipDirectory(directoryToZip, tmpZipFile);

        File zipFile = new File(tmpZipFile);
        try {
            InputStream targetStream = new FileInputStream(zipFile);
            return toResponseEntity(targetStream, OUTPUT_ZIP_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ResponseEntity<byte[]> toResponseEntity(InputStream targetStream, String fileName) {
        HttpHeaders headers = new HttpHeaders();

        headers.add(CONTENT_DISPOSITION_HEADER, "attachment; filename=" + fileName);
        headers.add(CONTENT_TYPE_HEADER, "application/zip");

        try {
            return new ResponseEntity<>(IOUtils.toByteArray(targetStream), headers, HttpStatus.OK);
        } catch (IOException exception) {
            throw new ServerErrorException("Unable to read attachment.", exception);
        }
    }

}
