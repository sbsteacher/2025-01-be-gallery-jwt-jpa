package com.green.gallery_jwt_jpa.config.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Component //빈등록
public class MyFileUtils {
    private final String uploadPath;

    public String getUploadPath() {
        return uploadPath;
    }

    public MyFileUtils(@Value("${constants.file.directory}") String uploadPath) {
        log.info("MyFileUtils - 생성자: {}", uploadPath);
        this.uploadPath = uploadPath;
    }

    public void makeFolders(String path) {
        File file = new File(uploadPath, path);
        if(!file.exists()) {
            file.mkdirs();
        }
    }

    //파일명에서 확장자 추출 (. 포함)
    public String getExt(String fileName) {
        int lastIdx = fileName.lastIndexOf(".");
        return fileName.substring(lastIdx);
    }

    //랜덤파일명 생성
    public String makeRandomFileName() {
        return UUID.randomUUID().toString();
    }

    //랜덤파일명 + 확장자 생성하여 리턴
    public String makeRandomFileName(String originalFileName) {
        return makeRandomFileName() + getExt(originalFileName);
    }

    public String makeRandomFileName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        return makeRandomFileName(originalFileName);
    }

    //파일을 원하는 경로에 저장
    public void transferTo(MultipartFile mf, String path) throws IOException {
        Path transPath = Paths.get(String.format("%s/%s", uploadPath, path)).toAbsolutePath();
        log.info("transPath: {}", transPath.toString());
        mf.transferTo(transPath.toFile());
    }

    //폴더 삭제, e.g. "user/1"
    public void deleteFolder(String path, boolean deleteRootFolder) {
        File folder = new File(path);
        if(folder.exists() && folder.isDirectory()) { //폴더가 존재하면서 디렉토리인가?
            File[] includedFiles = folder.listFiles();

            for(File f : includedFiles) {
                if(f.isDirectory()) {
                    deleteFolder(f.getAbsolutePath(), true);
                } else {
                    f.delete();
                }
            }

            if(deleteRootFolder) {
                folder.delete();
            }
        }

    }
}
