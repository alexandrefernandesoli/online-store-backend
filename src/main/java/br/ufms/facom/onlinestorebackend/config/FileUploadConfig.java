package br.ufms.facom.onlinestorebackend.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class FileUploadConfig {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostConstruct
    public void init() {
        // Create the upload directory if it doesn't exist
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
