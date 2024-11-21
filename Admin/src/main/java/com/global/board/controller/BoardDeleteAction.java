package com.global.board.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardDAO;
import com.global.board.model.BoardDTO;
import com.global.user.model.UsersDTO;
import com.global.utils.RequestParameterUtils;
import com.global.utils.ScriptUtil;

public class BoardDeleteAction implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int boardNo = Integer.parseInt(request.getParameter("boardNo"));
        UsersDTO user = (UsersDTO) request.getSession().getAttribute("user");

        BoardDAO dao = BoardDAO.getInstance();
        BoardDTO board = dao.selectBoard(boardNo, user.getUserType());

        // 권한 확인: 관리자이거나 작성자일 때만 삭제 가능
        if ("ADMIN".equals(user.getUserType()) || user.getUserNo() == board.getUserNo()) {
            int res = dao.deleteBoard(boardNo);
            if (res > 0) {
            	int currentPage = RequestParameterUtils.parseInteger(request.getParameter("currentPage"), 1);
            	request.setAttribute("currentPage",currentPage );
            	
    			ScriptUtil.sendScript(response, "게시글 삭제 성공!", "boardList.do");
            	
                return null; 
            } else {
                request.setAttribute("errorMsg", "게시글 삭제에 실패하였습니다.");
                return new View("main.go").setUrl("/views/common/error/error.jsp");
            }
        } else {
            request.setAttribute("errorMsg", "삭제 권한이 없습니다.");
            return new View("error.jsp");
        }
    }
}
