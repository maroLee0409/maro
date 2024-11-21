package com.global.board.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class BoardReplyUpdateOkAction implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Gson 객체 생성
        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();

        try {
            // 요청의 JSON 데이터를 파싱
            JsonObject jsonRequest = gson.fromJson(request.getReader(), JsonObject.class);
            
            // JSON에서 데이터 추출
            int replyNo = jsonRequest.get("replyNo").getAsInt();
            String newContent = jsonRequest.get("content").getAsString();

            // DAO를 통해 댓글 업데이트 처리
            BoardDAO dao = BoardDAO.getInstance();
            int updateResult = dao.updateReply(replyNo, newContent);

            if (updateResult > 0) {
                // 업데이트 성공 시
                jsonResponse.addProperty("success", true);
                jsonResponse.addProperty("message", "댓글이 성공적으로 수정되었습니다.");
            } else {
                // 업데이트 실패 시
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "댓글 수정에 실패했습니다.");
            }

        } catch (Exception e) {
            // 예외 처리
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "서버 오류가 발생했습니다.");
            e.printStackTrace();
        }

        // 응답을 JSON 형식으로 전송
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(jsonResponse));

        return null; // JSP 페이지로 이동하지 않음
    }
}
