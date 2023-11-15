package com.config.service;

import com.config.utils.CompressUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${solution-config.root.path}")
    private String clientConfigRootPath;

    public ResponseEntity<byte[]> getConfig(String path) {
        Path directoryToZipPath = Paths.get(clientConfigRootPath, path);
        File file = new File(directoryToZipPath.toAbsolutePath().toString());
        return file.isDirectory() ? getDirectoryAsByte(file) : getFileAsByte(file);
    }

    public void refresh(String clientActuatorUrl) {
        String refreshEndpointUrl = String.join("/", clientActuatorUrl, "refresh");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        restTemplate.exchange(refreshEndpointUrl, HttpMethod.GET, requestEntity, String.class);
    }

    private ResponseEntity<byte[]> getFileAsByte(File file) {
        try {
            InputStream targetStream = new FileInputStream(file);
            return toResponseEntity(targetStream, file.getName());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ResponseEntity<byte[]> getDirectoryAsByte(File file) {
        Path tmpFolderPath = Paths.get(TMP_FOLDER_NAME);
        File tmpFolder = new File(tmpFolderPath.toAbsolutePath().toString());
        if (!tmpFolder.exists()) {
            tmpFolder.mkdirs();
        }

        Path tmpZipFilePath = Paths.get(TMP_FOLDER_NAME, OUTPUT_ZIP_FILE_NAME);
        String tmpZipFile = tmpZipFilePath.toAbsolutePath().toString();

        CompressUtils.zipDirectory(file.getAbsolutePath(), tmpZipFile);

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
