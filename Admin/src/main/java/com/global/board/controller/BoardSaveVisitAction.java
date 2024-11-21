package com.global.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardVisitDAO;
import com.google.gson.Gson;

/*
	게시글을 클릭할 때마다 해당 게시글의 방문 기록을 저장하고, 그 후 사용자가 방문한 모든 게시글 목록을 반환합니다.
*/
public class BoardSaveVisitAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
		BoardVisitDAO visitDAO = BoardVisitDAO.getInstance();
		visitDAO.saveVisit(userNo, boardNo);

		// 사용자의 모든 방문 기록을 가져옴
		List<Integer> visitedBoardNos = visitDAO.getVisitedBoardNos(userNo);

		// 방문 기록을 JSON으로 변환
		Gson gson = new Gson();
		String json = gson.toJson(visitedBoardNos);

		// JSON을 클라이언트로 응답
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();

		return null; // View로 이동하지 않음, 바로 JSON을 응답
	}

}
