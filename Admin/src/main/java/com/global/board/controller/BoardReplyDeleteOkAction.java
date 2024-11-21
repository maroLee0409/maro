package com.global.board.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class BoardReplyDeleteOkAction implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Gson 객체 생성
        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();

    	
    	// 파라미터에서 replyNo를 가져옴
        int replyNo = 0;
        try {
        	// 요청의 JSON 데이터를 파싱
            JsonObject jsonRequest = gson.fromJson(request.getReader(), JsonObject.class);
            
            replyNo = jsonRequest.get("replyNo").getAsInt();
        } catch (NumberFormatException e) {
            // 파싱 실패 시 에러 메시지 반환
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("success", false);
            errorResponse.addProperty("message", "유효하지 않은 댓글 번호입니다.");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(errorResponse.toString());
            return null;
        }

        // DAO를 사용하여 댓글 삭제 (논리 삭제)
        BoardDAO dao = BoardDAO.getInstance();
        int result = dao.deleteReply(replyNo);

        if (result > 0) {
            jsonResponse.addProperty("success", true);
            jsonResponse.addProperty("message", "댓글이 성공적으로 삭제되었습니다.");
        } else {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "댓글 삭제에 실패했습니다.");
        }

        // JSON 형식으로 응답 설정
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(jsonResponse.toString());

        return null; // 뷰 이동이 필요 없으므로 null 반환
    }
}
