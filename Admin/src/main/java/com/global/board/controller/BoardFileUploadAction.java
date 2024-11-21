package com.global.board.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.global.action.Action;
import com.global.action.View;
import com.google.gson.Gson;
import com.global.board.model.BoardFileUploadDTO;
import com.global.board.model.UploadResponse;
import com.global.user.model.UsersDTO;

public class BoardFileUploadAction implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Gson gson = new Gson();
        
        HttpSession session = request.getSession();
        UsersDTO user = (UsersDTO) session.getAttribute("user");

        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(gson.toJson(new UploadResponse(false, null, "Unauthorized")));
            return null;
        }

        // 사용자 정보 가져오기
        int userNo = user.getUserNo();
        String userId = user.getUserId();

        // 날짜별 경로 설정 (년/월/일)
        LocalDate now = LocalDate.now();
        String year = String.valueOf(now.getYear());
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());

        // 임시 저장소 경로 설정 (간단하게 /resources/board/board_temp_files 에만 저장)
        String tempUploadRoot = request.getServletContext().getRealPath("/resources/board/board_temp_files");
        String uploadDir = tempUploadRoot;
        
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        Part filePart = request.getPart("file");

        if (filePart != null && filePart.getSize() > 0) {
            String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;
            String filePath = Paths.get(uploadDir, uniqueFileName).toString();

            // 파일 저장
            filePart.write(filePath);
            
            // 임시 파일 URL 생성 (웹 루트 경로에서 시작)
            String contextPath = request.getContextPath();  // "/Admin"
            String fileUrl = contextPath + "/resources/board/board_temp_files/" + uniqueFileName;

            // 파일 정보를 DTO에 담기
            BoardFileUploadDTO fileDTO = new BoardFileUploadDTO();
            fileDTO.setFileUrl(fileUrl);
            fileDTO.setFileName(uniqueFileName);
            fileDTO.setFileSize(filePart.getSize());
            fileDTO.setFileType(filePart.getContentType());

            // 세션에 업로드된 파일 정보 저장
            List<BoardFileUploadDTO> uploadedFiles = (List<BoardFileUploadDTO>) session.getAttribute("uploadedFiles");
            if (uploadedFiles == null) {
                uploadedFiles = new ArrayList<>();
            }
            uploadedFiles.add(fileDTO);
            session.setAttribute("uploadedFiles", uploadedFiles);

            // Froala에 JSON 응답 전송
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String jsonResponse = gson.toJson(new UploadResponse(true, fileUrl, null));
            response.getWriter().write(jsonResponse);
        } else {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String jsonResponse = gson.toJson(new UploadResponse(false, null, "No file uploaded"));
            response.getWriter().write(jsonResponse);
        }

        return null;
    }
}
