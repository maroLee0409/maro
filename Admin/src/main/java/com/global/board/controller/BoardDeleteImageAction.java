package com.global.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardFileUploadDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class BoardDeleteImageAction implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Gson 객체 생성 및 요청 JSON 파싱
        Gson gson = new Gson();
        JsonObject requestJson = gson.fromJson(request.getReader(), JsonObject.class);
        String imageUrl = requestJson.get("imageUrl").getAsString();

        // 이미지 URL에서 컨텍스트 경로 제거 (예: /Admin)
        String contextPath = request.getContextPath();  // "/Admin"
        if (imageUrl.startsWith(contextPath)) {
            imageUrl = imageUrl.substring(contextPath.length());
        }

        // 이미지 URL을 실제 파일 경로로 변환
        String realPath = request.getServletContext().getRealPath(imageUrl);
        File imageFile = new File(realPath);
        JsonObject jsonResponse = new JsonObject();

        // 세션에서 업로드된 파일 목록 가져오기
        HttpSession session = request.getSession();
        List<BoardFileUploadDTO> uploadedFiles = (List<BoardFileUploadDTO>) session.getAttribute("uploadedFiles");

        // 파일 경로가 null이거나 파일이 없을 경우 예외 처리
        if (realPath == null || imageFile == null || !imageFile.exists()) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "File not found: " + realPath);
        } else {
            // 파일 삭제 처리
            if (imageFile.delete()) {
                jsonResponse.addProperty("success", true);

                // 세션에서 삭제된 파일 정보 제거 (Iterator 사용)
                if (uploadedFiles != null) {
                    Iterator<BoardFileUploadDTO> iterator = uploadedFiles.iterator();
                    while (iterator.hasNext()) {
                        BoardFileUploadDTO file = iterator.next();
                        if (file.getFileUrl().equals(contextPath + imageUrl)) {
                            iterator.remove();  // 삭제
                        }
                    }
                    session.setAttribute("uploadedFiles", uploadedFiles);  // 세션에 변경 사항 반영
                }
            } else {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Failed to delete file: " + realPath);
            }
        }

        // JSON 응답 반환
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(jsonResponse));

        return null;  // View를 리턴할 필요 없음 (JSON 응답 처리)
    }
}
