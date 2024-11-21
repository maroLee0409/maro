package com.global.board.utils;

import com.oreilly.servlet.MultipartRequest;
import com.global.board.model.BoardFileUploadDTO;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class FileUploadUtils {

    // 파일 저장 경로 생성 및 업로드 처리
    public static List<BoardFileUploadDTO> processUploadedFiles(MultipartRequest multi, Integer userNo, String userId) {
        List<BoardFileUploadDTO> uploadedFiles = new ArrayList<>();

        // 현재 날짜 정보를 가져옴
        java.util.Calendar cal = java.util.Calendar.getInstance();
        int year = cal.get(java.util.Calendar.YEAR);
        int month = cal.get(java.util.Calendar.MONTH) + 1;  // 0부터 시작하므로 +1
        int day = cal.get(java.util.Calendar.DAY_OF_MONTH);

        // 파일 저장 경로 생성: /년/월/일/유저번호/유저아이디/이미지 또는 첨부파일
        String basePath = "/resources/board/board_upload_files/";
        String userPath = year + "/" + month + "/" + day + "/" + userNo + "/" + userId + "/";
        
        // 업로드 파일 유형별 경로 (이미지 or 첨부파일)
        String imageDir = basePath + userPath + "images/";
        String attachDir = basePath + userPath + "attachments/";

        // 디렉토리 생성
        createDirectory(imageDir);
        createDirectory(attachDir);

        // 파일 업로드 처리
        Enumeration files = multi.getFileNames();
        while (files.hasMoreElements()) {
            String fileInputName = (String) files.nextElement();
            String savedFileName = multi.getFilesystemName(fileInputName);
            String originalFileName = multi.getOriginalFileName(fileInputName);
            String fileType = multi.getContentType(fileInputName);

            if (savedFileName != null) {
                String savePath;
                if (fileType.startsWith("image")) {
                    savePath = imageDir + savedFileName;
                } else {
                    savePath = attachDir + savedFileName;
                }

                // 파일 정보를 DTO에 저장
                BoardFileUploadDTO fileDTO = new BoardFileUploadDTO();
                fileDTO.setFileUrl(savePath);
                fileDTO.setFileName(originalFileName);
                fileDTO.setFileSize(new File(savePath).length());
                fileDTO.setFileType(fileType);

                uploadedFiles.add(fileDTO);
            }
        }

        return uploadedFiles;
    }

    // 디렉토리 생성 유틸리티 메서드
    private static void createDirectory(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();  // 디렉토리가 존재하지 않으면 생성
        }
    }
}
