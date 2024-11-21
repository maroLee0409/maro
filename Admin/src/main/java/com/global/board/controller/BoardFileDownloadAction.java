package com.global.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;

public class BoardFileDownloadAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// 요청에서 파일 URL을 가져옴
		String fileUrl = request.getParameter("fileUrl");

		// 파일 경로를 실제 서버 경로로 변환
		String realPath = request.getServletContext().getRealPath(fileUrl);
		File file = new File(realPath);

		// 파일이 존재하는지 확인
		if (!file.exists()) {
			// 파일이 없으면 404 에러 반환
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
			return null;
		}

		// 파일의 MIME 타입을 설정
		String mimeType = Files.probeContentType(file.toPath());
		if (mimeType == null) {
			mimeType = "application/octet-stream";  // 기본 MIME 타입
		}

		// 응답 헤더 설정
		response.setContentType(mimeType);
		response.setContentLengthLong(file.length());

		// 다운로드할 파일 이름 설정 (헤더)
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=\"" + file.getName() + "\"";
		response.setHeader(headerKey, headerValue);

		// 파일을 응답의 OutputStream으로 전송
		try (FileInputStream inStream = new FileInputStream(file);
		     OutputStream outStream = response.getOutputStream()) {

			byte[] buffer = new byte[4096];
			int bytesRead = -1;

			while ((bytesRead = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			outStream.flush();
		}

		return null; // View가 필요하지 않음
	}
}
