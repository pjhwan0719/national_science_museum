package com.avad.nationalScienceMuseum.file.service.Impl;

import com.avad.nationalScienceMuseum.common.StatusCode;
import com.avad.nationalScienceMuseum.exception.CustomException;
import com.avad.nationalScienceMuseum.exception.domain.ErrorCode;
import com.avad.nationalScienceMuseum.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Override
    public String fileUpload(MultipartFile[] files, String filePath) {
        try {
            Files.createDirectories(Paths.get(filePath));
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    throw new CustomException(ErrorCode.FILE_EMPTY);
                }
                File f = new File(filePath, Objects.requireNonNull(file.getOriginalFilename()));
                file.transferTo(f);
            }
            return StatusCode.SUCCESS;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Object> fileDownload(String filePath) {
        try {
            Path fp = Paths.get(filePath);
            Resource resource = new InputStreamResource(Files.newInputStream(fp)); // 파일 resource 얻기
            File file = new File(filePath);
            HttpHeaders headers = new HttpHeaders();

            // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getName()).build());
            return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
