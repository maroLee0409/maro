package com.global.board.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardCategoryDTO;
import com.global.board.model.BoardDAO;
import com.global.board.model.BoardDTO;
import com.global.board.model.BoardFilter;
import com.global.utils.Location;
import com.global.utils.PageInfo;
import com.global.utils.RequestParameterUtils;

public class BoardSearchListAction implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 게시판의 현재 위치
        String subtitle = request.getParameter("subtitle");
        // 검색 키워드
        String searchKeyword = RequestParameterUtils.parseString(request.getParameter("searchKeyword").trim(), "");

        // 필터 파라미터들을 설정 및 필터 객체 생성
        BoardFilter filter = new BoardFilter();

        // 카테고리 번호 설정
        filter.setCategoryNo(request.getParameter("categoryNo"));

        // 유틸리티 메서드를 사용하여 파라미터를 안전하게 변환 및 설정
        filter.setUserNo(RequestParameterUtils.parseInteger(request.getParameter("userNo")));
        filter.setMinViews(RequestParameterUtils.parseInteger(request.getParameter("minViews")));
        filter.setMaxViews(RequestParameterUtils.parseInteger(request.getParameter("maxViews")));
        filter.setStartDate(RequestParameterUtils.parseDate(request.getParameter("startDate")));
        filter.setEndDate(RequestParameterUtils.parseDate(request.getParameter("endDate")));
        filter.setIsDeleted(request.getParameter("isDeleted"));

        // 댓글 수 정렬 옵션 설정
        String sortByCommentCountParam = request.getParameter("sortByCommentCount");
        filter.setSortByCommentCount(RequestParameterUtils.parseBoolean(sortByCommentCountParam));

        // DAO 호출 및 검색 결과 얻기
        BoardDAO dao = BoardDAO.getInstance();

        int listCount = dao.getBoardCount(filter);

        // 현재 페이지 처리
        int currentPage = RequestParameterUtils.parseInteger(request.getParameter("currentPage"), 1);

        // 게시물 리스트를 가져오기
        int boardLimit = 30;
        int pageLimit = 10;
        List<BoardDTO> boardList = dao.searchBoardList(currentPage, boardLimit, searchKeyword, filter);

        // 각 게시글의 댓글 수를 가져와 설정
        for (BoardDTO board : boardList) {
            board.setCommentCount(dao.getCommentCount(board.getBoardNo()));
        }

        // 댓글 수 정렬 옵션이 설정된 경우 리스트 정렬
        if (filter.getSortByCommentCount() != null && filter.getSortByCommentCount()) {
            Collections.sort(boardList, Comparator.comparing(BoardDTO::getCommentCount).reversed());
        }

        // 페이지 정보 계산 및 설정
        int maxPage = (int) Math.ceil((double) listCount / boardLimit);
        int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
        int endPage = Math.min(startPage + pageLimit - 1, maxPage);
        PageInfo pi = new PageInfo(currentPage, listCount, pageLimit, maxPage, startPage, endPage, boardLimit);

        // 필터용 BoardCategory List
        List<BoardCategoryDTO> categoryList = dao.selectBoardCategoryList();
        
        
		Location location = new Location();
		location.addPath("홈");
		location.addPath("게시판 관리");
		location.setCurrent("검색 결과");  // 현재 위치
		location.setTitle("게시판");  // 동적으로 h1 타이틀 설정
		
        // request에 필요한 속성 설정
		request.setAttribute("location", location);
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("searchKeyword", searchKeyword);
        request.setAttribute("filter", filter);
        request.setAttribute("count", pi.getListCount());
        request.setAttribute("list", boardList);
        request.setAttribute("pi", pi);

        // 결과 페이지로 이동
        return new View("main.go").setUrl("/views/board/boardList.jsp");
    }
}
