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
import com.global.utils.ScriptUtil;

public class BoardInsertAction implements Action {


	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		BoardDAO dao = BoardDAO.getInstance();
		
		List<BoardCategoryDTO> list = dao.selectBoardCategoryList("N");
	
        // request에 필요한 속성 설정
        request.setAttribute("categoryList", list);

		return new View("main.go").setUrl("/views/board/boardInsertForm.jsp");
	}

}
