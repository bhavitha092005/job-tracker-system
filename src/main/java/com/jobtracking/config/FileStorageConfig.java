package com.jobtracking.config;

import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FileStorageConfig {

    public static final Path RESUME_UPLOAD_DIR =
            Paths.get("uploads", "resumes").toAbsolutePath().normalize();
}