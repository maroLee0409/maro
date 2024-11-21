package com.global.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardCategoryDTO;
import com.global.board.model.BoardDAO;
import com.global.board.model.BoardDTO;

public class BoardUpdateFormAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BoardDAO dao = BoardDAO.getInstance();
		int no = Integer.parseInt(request.getParameter("no"));
		String userType = request.getParameter("userType");
		
		String status = request.getParameter("status") == null ? "" : request.getParameter("status");
    	int currentPage = request.getParameter("currentPage") == null ? 0 : Integer.parseInt(request.getParameter("currentPage"));
    	
		BoardDTO board = dao.selectBoard(no, userType);
		
		List<BoardCategoryDTO> list = dao.selectBoardCategoryList();
		
        // request에 필요한 속성 설정
        request.setAttribute("categoryList", list);
    	request.setAttribute("status", status);
    	request.setAttribute("currentPage", currentPage);
    	request.setAttribute("info", board);
		
		return new View("main.go").setUrl("/views/board/boardUpdateForm.jsp");
	}

}
