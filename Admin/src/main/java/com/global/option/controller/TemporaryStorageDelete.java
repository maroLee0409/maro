package com.global.option.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;

public class TemporaryStorageDelete implements Action {

    private static final String TEMP_DIR = "/resources/board/board_temp_files";

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 웹 애플리케이션의 실제 경로에서 임시 파일 경로로 변환
        String tempDirPath = request.getServletContext().getRealPath(TEMP_DIR);

        // 임시 저장소 폴더 확인 (board_temp_files 폴더 존재 여부 확인)
        File tempDir = new File(tempDirPath);
        
        // board_temp_files 폴더가 존재하지 않을 경우
        if (!tempDir.exists() || !tempDir.isDirectory()) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("<script>alert('임시 파일을 삭제할 필요가 없습니다. (board_temp_files 폴더가 존재하지 않습니다)');location.href='main.go';</script>");
            return null;
        }

        // board_temp_files 폴더가 존재할 경우, 폴더 내 모든 파일 및 하위 폴더 삭제
        int deletedFileCount = deleteAllFilesInDirectory(tempDirPath);

        // 삭제 완료 후 알림 메시지 전송
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write("<script>alert('" + deletedFileCount + "개의 파일이 삭제되었습니다.');location.href='main.go';</script>");

        return null;
    }

    /**
     * 지정된 경로에서 모든 파일 및 하위 폴더를 삭제하는 메서드
     * @param dirPath 삭제할 디렉토리 경로
     * @return 삭제된 파일 개수
     */
    private int deleteAllFilesInDirectory(String dirPath) {
        int deletedFileCount = 0;
        try (Stream<Path> files = Files.walk(Paths.get(dirPath))) {
            deletedFileCount = (int) files
                .filter(Files::isRegularFile)  // 정규 파일만 선택
                .map(Path::toFile)
                .mapToInt(file -> file.delete() ? 1 : 0)  // 삭제하고 카운트
                .sum();
            
            // 모든 파일 삭제 후, 빈 디렉토리 삭제
            Files.walk(Paths.get(dirPath))
                 .sorted((a, b) -> b.compareTo(a))  // 하위 디렉토리부터 삭제
                 .map(Path::toFile)
                 .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deletedFileCount;
    }
}
