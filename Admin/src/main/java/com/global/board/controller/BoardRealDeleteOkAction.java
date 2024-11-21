package com.global.board.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardDAO;
import com.global.board.model.BoardFileUploadDTO;
import com.global.utils.ScriptUtil;

public class BoardRealDeleteOkAction implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 게시글 번호를 파라미터로 받음
        String boardNoStr = request.getParameter("boardNo");
        int boardNo = Integer.parseInt(boardNoStr);
        
        // DAO 인스턴스 생성
        BoardDAO boardDao = BoardDAO.getInstance();

        // 1. 삭제될 게시글과 연관된 파일 정보를 먼저 가져옴
        List<BoardFileUploadDTO> files = boardDao.getFilesByBoardNo(boardNo);

        // 2. 게시글과 댓글을 DB에서 삭제
        boolean isDeleted = boardDao.deleteBoardAndReplies(boardNo);

        if (isDeleted) {
            // 3. 게시글 삭제 후, 연관된 파일들도 삭제 (DB 삭제 후 파일 시스템에서 삭제)
            deleteFilesFromSystem(request, files);

            // 성공 시 알림 후 게시글 목록으로 이동
            ScriptUtil.sendScript(response, "게시글 및 댓글 삭제 성공!", "boardList.do");
        } else {
            // 실패 시 오류 메시지 출력
            ScriptUtil.sendScript(response, "게시글 삭제 중 오류가 발생했습니다.", null);
        }

        return null;  // View는 필요하지 않음
    }

    // 실제 파일 시스템에서 파일 삭제하는 메서드
    private void deleteFilesFromSystem(HttpServletRequest request, List<BoardFileUploadDTO> files) {
        for (BoardFileUploadDTO file : files) {
            try {
                // 파일의 절대 경로를 가져옴
                String filePath = request.getServletContext().getRealPath(file.getFileUrl());

                // 파일 객체 생성 후 파일이 존재하면 삭제
                File fileToDelete = new File(filePath);
                if (fileToDelete.exists()) {
                    Files.delete(Paths.get(filePath)); // 파일 시스템에서 파일 삭제
                    System.out.println("삭제된 파일: " + filePath);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("파일 삭제 중 오류 발생: " + file.getFileUrl());
            }
        }
    }
}
