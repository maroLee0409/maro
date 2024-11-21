package com.global.review.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.review.model.ProductReviewDAO;
import com.global.review.model.ProductReviewDTO;
import com.global.utils.PageInfo;

public class ProductReviewListAction implements Action{

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		ProductReviewDAO dao = ProductReviewDAO.getInstance();
		
		
		int listCount;
		
		// 현재 페이지
		int currentPage = 1;
		if (request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		// 한 페이지에 보여질 게시글 최대 수
		int boardLimit = 10;
		
		// 페이지 하단에 보여질 페이지 수
		int pageLimit = 10;
		List<ProductReviewDTO> list;

		listCount = dao.getReviewCount();
		list = dao.selectReviewList(currentPage, boardLimit);

		
		// 전체 페이지 수 계산
        int maxPage = (int) Math.ceil((double) listCount / boardLimit);
        
        // 시작 페이지 계산
        int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
        
        // 끝 페이지 계산
        int endPage = startPage + pageLimit - 1;
        if (maxPage < endPage) {
            endPage = maxPage;
        }
        
     // PageInfo 객체 생성 및 설정
        PageInfo pi = new PageInfo(currentPage, listCount, pageLimit, maxPage, startPage, endPage, boardLimit);
        
        // request에 필요한 속성 설정
        request.setAttribute("count", pi.getListCount());
        request.setAttribute("list", list);
        request.setAttribute("pi", pi);
        request.setAttribute("address", "productReviewList.do"); // 필터 상태를 JSP로 전달
		
		return new View("main.go").setUrl("/views/review/product_review_list.jsp");
	}
	
}
