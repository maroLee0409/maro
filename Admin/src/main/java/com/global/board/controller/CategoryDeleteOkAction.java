package com.global.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardDAO;
import com.global.utils.ScriptUtil;

public class CategoryDeleteOkAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	    
	    String categoryNo = request.getParameter("categoryNo");
	    
	    // 먼저 해당 카테고리에 게시글이 존재하는지 확인
	    int boardCount = BoardDAO.getInstance().getBoardCountByCategory(categoryNo);
	    
	    if (boardCount > 0) {
	        // 게시글이 존재하면 삭제 불가 메시지 전달
	        ScriptUtil.sendScript(response, "해당 카테고리에 속한 게시글들을 먼저 삭제해야 합니다.", null);
	        return null;
	    }
	    
	    // 게시글이 없는 경우에만 카테고리 삭제
	    int check = BoardDAO.getInstance().deleteCategory(categoryNo);
	    
	    if (check > 0) {
	        ScriptUtil.sendScript(response, "카테고리 삭제 성공!!", "boardCategoryList.do");
	    } else {
	        ScriptUtil.sendScript(response, "카테고리 삭제 실패!!", null);
	    }

	    return null;
	}

}
