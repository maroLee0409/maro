package com.global.board.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardCategoryDTO;
import com.global.board.model.BoardDAO;
import com.global.board.model.BoardDTO;
import com.global.utils.ScriptUtil;

public class BoardUpdateOkAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BoardDAO dao = BoardDAO.getInstance();
		
		//int no = Integer.parseInt(request.getParameter("no"));
		String userType = request.getParameter("userType");
		
		String status = request.getParameter("status") == null ? "" : request.getParameter("status");
    	int currentPage = request.getParameter("currentPage") == null ? 0 : Integer.parseInt(request.getParameter("currentPage"));
    	
		
    	/* 현재 로그인이 구현 안됐으므로 아래 코드는 임시 주석처리합니다.*/
		/*
		int userNo;
		if(request.getParameter("userNo") != null) {
			userNo = Integer.parseInt(request.getParameter("userNo"));
			board.setUserNo(userNo);
		}
		*/
    	
    	int boardNo = Integer.parseInt(request.getParameter("boardNo").trim());
    	String categoryNo = request.getParameter("categoryNo");
    	String boardTitle = request.getParameter("boardTitle");
    	String content = request.getParameter("content").trim();
    	
    	BoardDTO board = new BoardDTO();
    	board.setBoardNo(boardNo);
    	board.setCategoryNo(categoryNo);
    	board.setTitle(boardTitle);
    	board.setContent(content);
    	
    	
    	/* 카테고리 No를 이용하여 카테기로 정보를 갖고 옵니다.*/
    	//BoardCategoryDTO category = dao.selectboardCategory(categoryNo); 
    	
    	
    	int check = dao.updateBoard(board); 
    	
    	request.setAttribute("status", status);
    	request.setAttribute("currentPage", currentPage);
    	
    	if(check>0) {
    		ScriptUtil.sendScript(response, "게시글 수정 성공!", "boardList.do");
    	}else {
    		ScriptUtil.sendScript(response, "게시글 수정 실패!", null);
    	}
    	
    	
		return null;
	}

}
