package com.global.board.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardDAO;
import com.global.board.model.BoardDTO;
import com.global.board.model.BoardFileUploadDTO;
import com.global.board.model.BoardReplyDTO;
import com.global.utils.PageInfo;
import com.global.utils.RequestParameterUtils;
import com.global.utils.PageUtil;

public class BoardDetailFormAction implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int boardNo = Integer.parseInt(request.getParameter("boardNo"));
        String userType = request.getParameter("userType");
        
        String status = RequestParameterUtils.parseString(request.getParameter("status"), "");
        int currentPage = RequestParameterUtils.parseInteger(request.getParameter("currentPage"),1);
        String subtitle = request.getParameter("subtitle");
        
        BoardDAO dao = BoardDAO.getInstance();
        BoardDTO board = null;
        boolean flag = false;

        // 조회수 증가 로직
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("boardNo" + boardNo)) {
                    flag = true;
                }
            }
            if (!flag) {
                int res = dao.increaseViews(boardNo);
                if (res > 0) {
                    board = dao.selectBoard(boardNo, userType);
                    Cookie c1 = new Cookie("boardNo" + boardNo, String.valueOf(boardNo));
                    c1.setMaxAge(1 * 24 * 60 * 60);
                    response.addCookie(c1);
                }
            } else {
                board = dao.selectBoard(boardNo, userType);
            }
        }
        
        // 게시글 수에 따라 페이지 정보를 가져오기 (30개 게시글, 10개 페이지 제한)
        int listCount = dao.getBoardCountByCategory(board.getCategoryNo());
        int boardLimit = 20;
        int pageLimit = 10;
        
        PageInfo pi = PageUtil.getPageInfo(request, listCount, boardLimit, pageLimit);
        
        // 파일 목록 가져오기
        List<BoardFileUploadDTO> files = dao.getFilesByBoardNo(boardNo);

        // 댓글 목록 가져오기
        List<BoardReplyDTO> comments = dao.getCommentsByBoardNo(boardNo);
        
        
        List<BoardDTO> boardList = dao.getDetailBoardList(currentPage,boardLimit,board.getCategoryNo());
        
        // request에 필요한 속성 설정
        request.setAttribute("count", listCount);
        request.setAttribute("list", boardList);
        request.setAttribute("comments", comments);
        request.setAttribute("files", files);
        request.setAttribute("status", status);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("info", board);
        request.setAttribute("pi", pi);  // PageInfo 객체 전달
        request.setAttribute("subtitle", subtitle);
        request.setAttribute("address", "boardDetailForm.do"); // 페이지의 매핑을 던져줘야 함
        
        return new View("main.go").setUrl("/views/board/boardDetailForm.jsp");
    }
}
