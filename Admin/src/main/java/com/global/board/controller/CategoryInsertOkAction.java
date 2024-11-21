package com.global.board.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardCategoryDTO;
import com.global.board.model.BoardDAO;
import com.global.utils.ScriptUtil;

public class CategoryInsertOkAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String no = request.getParameter("no");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		BoardCategoryDTO category = new BoardCategoryDTO();
		category.setCategoryNo(no);
		category.setName(name);
		category.setDescription(description);
		
		int check = BoardDAO.getInstance().insertBoardCategory(category);
		
    	
		if (check > 0) {
			ScriptUtil.sendScript(response, "게시글 카테고리 입력 성공", "boardCategoryList.do");
		} else {
			ScriptUtil.sendScript(response, "게시글 카테고리 입력 실패!!!", null);
		}
		
		
		return null;
	}

}
