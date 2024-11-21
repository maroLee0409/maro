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
import com.global.board.model.BoardFilter;
import com.global.utils.Location;
import com.global.utils.PageInfo;
import com.global.utils.RequestParameterUtils;

public class BoardCategoryFilterAction implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 카테고리 번호와 서브타이틀을 받아옵니다.
        String categoryNo = request.getParameter("categoryNo");
        String subtitle = request.getParameter("subtitle");

        // DAO 호출 및 필터링된 결과 얻기
        BoardDAO dao = BoardDAO.getInstance();
        BoardFilter filter = new BoardFilter();
        filter.setCategoryNo(categoryNo);

        int listCount = dao.getBoardCount(filter);
        int currentPage = RequestParameterUtils.parseInteger(request.getParameter("currentPage"), 1);
        int boardLimit = 10;
        int pageLimit = 10;

        // 게시물 리스트를 가져오기
        List<BoardDTO> boardList = dao.searchBoardList(currentPage, boardLimit, "", filter);

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
		location.setCurrent(subtitle);  // 현재 위치
		location.setTitle("게시판");  // 동적으로 h1 타이틀 설정
		
        // request에 필요한 속성 설정
		request.setAttribute("location", location);
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("filter", filter);
        request.setAttribute("count", pi.getListCount());
        request.setAttribute("list", boardList);
        request.setAttribute("address", "boardList.do"); // 페이지의 매핑을 던져줘야 함
        request.setAttribute("pi", pi);

        // 결과 페이지로 이동
        return new View("main.go").setUrl("/views/board/boardList.jsp");
    }
}
