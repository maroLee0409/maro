package com.global.product.controller;

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
import com.global.product.model.ProductCategoryDTO;
import com.global.product.model.ProductDAO;
import com.global.product.model.ProductDTO;
import com.global.product.model.ProductFilter;
import com.global.utils.Location;
import com.global.utils.PageInfo;
import com.global.utils.RequestParameterUtils;

public class ProductSearchListAction implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 게시판의 현재 위치
        String subtitle = request.getParameter("subtitle");
        // 검색 키워드
        String searchKeyword = RequestParameterUtils.parseString(request.getParameter("searchKeyword").trim(), "");
        String status = request.getParameter("status");

        System.out.println("search >>>> " + searchKeyword);
        
        // 필터 파라미터들을 설정 및 필터 객체 생성
        ProductFilter filter = new ProductFilter();

        // 카테고리 번호 설정
        filter.setCategoryNo(request.getParameter("categoryNo"));

        // 유틸리티 메서드를 사용하여 파라미터를 안전하게 변환 및 설정
        filter.setUserNo(RequestParameterUtils.parseInteger(request.getParameter("userNo")));
        filter.setMinViews(RequestParameterUtils.parseInteger(request.getParameter("minViews")));
        filter.setMaxViews(RequestParameterUtils.parseInteger(request.getParameter("maxViews")));
        filter.setStartDate(RequestParameterUtils.parseDate(request.getParameter("startDate")));
        filter.setEndDate(RequestParameterUtils.parseDate(request.getParameter("endDate")));
        filter.setMinPrice(RequestParameterUtils.parseInteger(request.getParameter("minPrice")));
        filter.setMaxPrice(RequestParameterUtils.parseInteger(request.getParameter("maxPrice")));
        filter.setIsDeleted(request.getParameter("isDeleted"));

        //         // DAO 호출 및 검색 결과 얻기
        ProductDAO dao = ProductDAO.getInstance();

        int listCount = dao.getProductCount(filter);

        // 현재 페이지 처리
        int currentPage = RequestParameterUtils.parseInteger(request.getParameter("currentPage"), 1);

        // 게시물 리스트를 가져오기
        int boardLimit = 10;
        int pageLimit = 10;
        List<ProductDTO> list;
        List<ProductDTO> productList = dao.searchProductList(currentPage, boardLimit, searchKeyword, filter);

        // 페이지 정보 계산 및 설정
        int maxPage = (int) Math.ceil((double) listCount / boardLimit);
        int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
        int endPage = Math.min(startPage + pageLimit - 1, maxPage);
        List<ProductCategoryDTO> categoryList = dao.selectProductCategoryList();
		if("N".equals(status)) {
			listCount = dao.getProductCount('N'); //얘는 필터 기능이다.
			list = dao.selectProductList(currentPage, boardLimit, 'N');
		}else if("Y".equals(status)) {
			listCount = dao.getProductCount('Y');
			list = dao.selectProductList(currentPage, boardLimit, 'Y');
		}else {
		listCount = dao.getProductListCount();
        list = dao.getProduct(currentPage, boardLimit);
		}

        
        PageInfo pi = new PageInfo(currentPage, listCount, pageLimit, maxPage, startPage, endPage, boardLimit);

        String category_No = filter.getCategoryNo();
        int user_No = filter.getUserNo();
        int min_views = filter.getMinViews();
        int max_views = filter.getMaxViews();
        Date start_Date = filter.getStartDate();
        Date end_Date = filter.getEndDate();
        int min_Price = filter.getMinPrice();
        int max_Price = filter.getMaxPrice();
        String is_Deleted = filter.getIsDeleted();
        		
        
        for(ProductDTO product : productList) {
        	System.out.println("product >>> " + product);
        }
        
		
        // request에 필요한 속성 설정
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("searchKeyword", searchKeyword);
        request.setAttribute("filter", filter);
        request.setAttribute("count", pi.getListCount());
        request.setAttribute("List", productList);
        request.setAttribute("pi", pi);
        request.setAttribute("address", "productSearchList.do?");
//        		+ "subtitle=&searchKeyword="+searchKeyword+"&categoryNo="+category_No+"&userNo="
//        +user_No+"&minViews="+min_views+"&maxViews="+max_views+"&startDate="+start_Date+"&endDate="+end_Date+"&minPrice="+min_Price+"&maxPrice="+max_Price+"&isDeleted="+is_Deleted);

        // 결과 페이지로 이동
        return new View("main.go").setUrl("/views/product/product_list.jsp");
    }
}