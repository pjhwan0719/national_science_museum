package com.avad.nationalScienceMuseum.file.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    /**
     * PJH
     * - file upload
     * @param files
     */
    public String fileUpload(MultipartFile[] files, String filePath);

    /**
     * PJH
     * - file download
     * @param filePath
     * @return
     */
    public ResponseEntity<Object> fileDownload(String filePath);
}
