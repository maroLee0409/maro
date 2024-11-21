package com.global.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.global.action.Action;
import com.global.action.View;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class InvalidateBoardSessionAction implements Action{

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 요청의 내용을 JSON 형식으로 처리
        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();  // 응답을 위한 JSON 객체 생성
        HttpSession session = request.getSession(false);
        
        // 세션이 존재하는 경우에만 처리
        if (session != null) {
            List<String> uploadedFiles = (List<String>) session.getAttribute("uploadedFiles");

            if (uploadedFiles != null) {
                for (String filePath : uploadedFiles) {
                    File file = new File(filePath);
                    if (file.exists()) {
                        file.delete();  // 파일 삭제
                    }
                }
                session.removeAttribute("uploadedFiles");  // 세션에서 파일 목록 제거
                jsonResponse.addProperty("success", true);
                jsonResponse.addProperty("message", "Uploaded files successfully deleted.");
            } else {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "No files to delete.");
            }
        } else {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Session not found.");
        }

        // JSON 응답 작성
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(jsonResponse));
        out.flush();
	
		return null;
	}

}
