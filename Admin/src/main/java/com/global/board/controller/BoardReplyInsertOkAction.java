package com.global.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardDAO;
import com.global.board.model.BoardReplyDTO;
import com.google.gson.Gson;

public class BoardReplyInsertOkAction implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Gson gson = new Gson();
        BoardReplyDTO reply = gson.fromJson(request.getReader(), BoardReplyDTO.class);

        BoardDAO dao = BoardDAO.getInstance();

        if (reply.getParentReplyNo() == null) {
            // 일반 댓글
            dao.insertReply(reply);
        } else {
            // 대댓글
            dao.insertSubReply(reply);
        }

        // 최신 댓글 리스트를 JSON으로 반환
        List<BoardReplyDTO> comments = dao.getCommentsByBoardNo(reply.getBoardNo());

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 처리 성공에 대한 응답
        ResponseData responseData = new ResponseData(true, "댓글이 성공적으로 등록되었습니다.", comments);
        out.print(gson.toJson(responseData));
        out.flush();

        return null; // JSON 응답이므로 View는 null로 반환
    }

    // 응답 데이터 포맷을 위한 클래스
    private class ResponseData {
        boolean success;
        String message;
        List<BoardReplyDTO> comments;

        ResponseData(boolean success, String message, List<BoardReplyDTO> comments) {
            this.success = success;
            this.message = message;
            this.comments = comments;
        }
    }
}
