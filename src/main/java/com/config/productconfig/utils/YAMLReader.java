package com.config.productconfig.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Ref: https://github.com/dsouvage/JavaYAMLtoFlatMap/tree/main
 */
public class YAMLReader {

    private final File ymlFile;

    public YAMLReader(File ymlFile) {
        this.ymlFile = ymlFile;
    }

    public Map<String, Object> readAsFlattenMap() {
        String fileContent = readFileAsString(ymlFile);
        Map<String, Object> map = convertStringToMap(fileContent);
        return JsonMapFlattener.flattenToStringMap(map);
    }

    private String readFileAsString(File file) {
        StringBuilder content = new StringBuilder();

        try (FileInputStream input = new FileInputStream(file)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    private Map<String, Object> convertStringToMap(String fileContent) {
        Yaml yaml = new Yaml();
        return yaml.load(fileContent);
    }
}
