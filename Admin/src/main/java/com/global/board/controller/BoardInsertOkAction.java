package com.global.board.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardDAO;
import com.global.board.model.BoardDTO;
import com.global.board.model.BoardFileUploadDTO;
import com.global.user.model.UsersDTO;
import com.global.utils.ScriptUtil;

public class BoardInsertOkAction implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        UsersDTO user = (UsersDTO) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/loginForm.do");
            return null;
        }

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String categoryNo = request.getParameter("categoryNo");

        BoardDTO board = new BoardDTO();
        board.setTitle(title);
        board.setContent(content);
        board.setCategoryNo(categoryNo);

        // 게시글을 먼저 저장하고 boardNo를 받아옴
        BoardDAO dao = BoardDAO.getInstance();
        int boardNo = dao.insertBoard(board, user.getUserNo()); 

        List<BoardFileUploadDTO> uploadedFiles = (List<BoardFileUploadDTO>) session.getAttribute("uploadedFiles");
        
        LocalDate now = LocalDate.now();
        String year = String.valueOf(now.getYear());
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());

        if (uploadedFiles != null) {
            for (BoardFileUploadDTO file : uploadedFiles) {
                String tempFileUrl = file.getFileUrl();  // 임시 저장소 경로
                String fileName = file.getFileName();

                // 영구 저장소 경로 설정 (예: /resources/board/board_upload_files/boardNo/유저번호/유저아이디/파일명)
                String permanentFileUrl = "/resources/board/board_upload_files/" + boardNo + "/" + user.getUserNo() + "/" + user.getUserId() + "/" + year + "/" + month + "/" + day + "/" + fileName;

                // 임시 파일 URL에서 컨텍스트 경로 제거 (예: /Admin)
                String contextPath = request.getContextPath();  // "/Admin"
                if (tempFileUrl.startsWith(contextPath)) {
                    tempFileUrl = tempFileUrl.substring(contextPath.length());
                }

                // 절대 경로로 변환
                String tempFileAbsolutePath = request.getServletContext().getRealPath(tempFileUrl);
                
                String permanentFileAbsolutePath = request.getServletContext().getRealPath(permanentFileUrl);

                // 영구 저장소 디렉토리가 존재하지 않으면 생성
                File permanentDir = new File(permanentFileAbsolutePath).getParentFile();  // 디렉토리 경로만 추출
                if (!permanentDir.exists()) {
                    permanentDir.mkdirs();  // 경로가 없으면 생성
                }

                // 실제 파일 이동
                if (new File(tempFileAbsolutePath).exists()) {
                    Files.move(Paths.get(tempFileAbsolutePath), Paths.get(permanentFileAbsolutePath));

                    // 파일 이동 후, 영구 저장소 경로를 DTO에 저장
                    BoardFileUploadDTO fileDTO = new BoardFileUploadDTO();
                    fileDTO.setFileUrl(permanentFileUrl);
                    fileDTO.setFileName(fileName);
                    fileDTO.setFileSize(file.getFileSize());
                    fileDTO.setFileType(file.getFileType());

                    uploadedFiles.set(uploadedFiles.indexOf(file), fileDTO);
                }
            }
        }
 
        
     // 게시글 작성 후 파일 경로를 영구 저장소 경로로 변경
        if (uploadedFiles != null && !uploadedFiles.isEmpty()) {
            String updatedContent = replaceTempUrlWithPermanentUrl(content, uploadedFiles, boardNo, user, request.getContextPath(), year, month, day);
            
            
            board.setContent(updatedContent);
        }
        // 게시글 첨부파일 저장
        dao.boardInsertFileUpload(boardNo, uploadedFiles);
        int result = dao.updateBoardContent(boardNo,board.getContent());
        
        
        if (result > 0) {
            session.setAttribute("isSaved", true);  // 세션에 저장 성공 표시
            session.removeAttribute("uploadedFiles");  // 임시 파일 목록 제거
            ScriptUtil.sendScript(response, "게시글 작성 성공!", "boardList.do");
        } else {
            session.setAttribute("isSaved", false);
            ScriptUtil.sendScript(response, "게시글 작성 실패!", null);
        }

        return null;  // View는 필요 없음
    }

 // 이미지 URL을 임시 저장소 경로에서 영구 저장소 경로로 업데이트하는 메서드
    private String replaceTempUrlWithPermanentUrl(String tmpContent, List<BoardFileUploadDTO> uploadedFiles, int boardNo, UsersDTO user, String contextPath, String year, String month, String day) {
        for (BoardFileUploadDTO file : uploadedFiles) {
            // Froala에서 사용하는 경로는 /Admin 포함
            // 이 경로는 실제 HTML에서 사용된 이미지 경로와 일치해야 함
            String tempUrl = contextPath + "/resources/board/board_temp_files/" + file.getFileName();  // 임시 파일 경로
            String permanentUrl = contextPath + "/resources/board/board_upload_files/" + boardNo + "/" + user.getUserNo() + "/" + user.getUserId() + "/" + year + "/" + month + "/" + day + "/" + file.getFileName();

            // Froala에서 사용되는 src 속성 경로를 영구 경로로 대체
            tmpContent = tmpContent.replace(tempUrl, permanentUrl);
        }
        return tmpContent;
    }



}
