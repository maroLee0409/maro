package com.global.board.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardCategoryDTO;
import com.global.board.model.BoardDAO;
import com.global.utils.RequestParameterUtils;
import com.global.utils.ScriptUtil;

public class CategoryUpdateOkAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		
		String status = RequestParameterUtils.parseString(request.getParameter("status"), "");
    	int currentPage = RequestParameterUtils.parseInteger(request.getParameter("currentPage"), 1);
    	
		String categoryNo = request.getParameter("categoryNo");
		String categoryTitle = request.getParameter("name");
		String isDeleted = request.getParameter("isDeleted");
		
		BoardCategoryDTO category = new BoardCategoryDTO();
		category.setCategoryNo(categoryNo);
		category.setName(categoryTitle);
		category.setIsDeleted(isDeleted);
		
		System.out.println(category.toString());
		
		int check = BoardDAO.getInstance().updateCategory(category);
		
		request.setAttribute("status", status);
    	request.setAttribute("currentPage", currentPage);
		
		if(check>0) {
			ScriptUtil.sendScript(response, "카테고리 변경 성공!", "boardCategoryList.do");
		}else {
			ScriptUtil.sendScript(response, "카테고리 변경 실패!", null);
		}
		
		
		return null;
	}

}
