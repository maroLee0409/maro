package com.global.board.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardCategoryDTO;
import com.global.board.model.BoardDAO;

public class CategoryUpdateFormAction implements Action{

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String categoryNo = request.getParameter("no");
		
		String status = request.getParameter("status") == null ? "" : request.getParameter("status");
    	int currentPage = request.getParameter("currentPage") == null ? 0 : Integer.parseInt(request.getParameter("currentPage"));
    	
    	BoardCategoryDTO category = BoardDAO.getInstance().selectBoardCategory(categoryNo);
		
		
        // request에 필요한 속성 설정
    	request.setAttribute("status", status);
    	request.setAttribute("currentPage", currentPage);
    	request.setAttribute("category", category);
		
		return new View("main.go").setUrl("/views/board/categoryUpdateForm.jsp");
	}

}
