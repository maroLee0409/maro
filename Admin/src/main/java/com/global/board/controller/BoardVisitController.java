package com.global.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardVisitDAO;
import com.global.user.model.UsersDTO;
import com.google.gson.Gson;

/*
	페이지를 로드할 때 사용자의 방문 기록을 가져오는 데 사용됩니다. 
	이는 사용자가 페이지를 로드할 때, 이미 방문한 게시글들을 시각적으로 표시할 수 있게 합니다.
*/
public class BoardVisitController implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsersDTO user = (UsersDTO) request.getSession().getAttribute("user");
        int userNo = user.getUserNo();

        BoardVisitDAO visitDAO = BoardVisitDAO.getInstance();
        List<Integer> visitedBoardNos = visitDAO.getVisitedBoardNos(userNo);

        Gson gson = new Gson();
        String json = gson.toJson(visitedBoardNos);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();

        return null;
    }
}