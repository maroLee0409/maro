package com.global.board.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardDAO;
import com.global.utils.RequestParameterUtils;
import com.global.utils.ScriptUtil;

public class BoardRestoreOkAction implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 게시글 번호를 요청 파라미터에서 가져옴
        
    	int boardNo = Integer.parseInt(request.getParameter("boardNo"));
        
        int currentPage = RequestParameterUtils.parseInteger(request.getParameter("currentPage"), 1);
        // 데이터베이스 액세스를 위한 DAO 인스턴스 가져오기
        BoardDAO dao = BoardDAO.getInstance();

        // 게시글의 삭제 상태를 취소
        int result = dao.restoreBoard(boardNo);

        // 복원 결과에 따라 메시지 설정
        if (result > 0) {
            // 복원 성공
        	// 복원 후 상세보기 페이지로 리다이렉트
        	ScriptUtil.sendScript(response, "게시글 복원 성공!", "boardDetailForm.do?boardNo="+boardNo+"&currentPage="+currentPage);
        } else {
            // 복원 실패
            request.setAttribute("message", "게시글 복원에 실패했습니다.");
        }
        return null;
    }
}
