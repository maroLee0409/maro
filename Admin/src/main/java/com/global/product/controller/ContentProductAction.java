package com.global.product.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.product.model.ProductDAO;
import com.global.product.model.ProductDTO;
import com.global.utils.PageInfo;

public class ContentProductAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ProductDAO dao = ProductDAO.getInstance();
				
		String product_no = request.getParameter("no").trim(); 
		
		String user_no = request.getParameter("userType");

		String status = request.getParameter("status");
		
		int listCount;
		
        int currentPage = 1; // 기본적으로 페이지는 1로 설정
        if (request.getParameter("currentPage") != null) {
        	currentPage = Integer.parseInt(request.getParameter("currentPage"));
        }

        int boardLimit = 10; // 한 페이지에 보여질 게시글 최대 수
        
        int pageLimit = 10; // 한 페이지 하단에 보여질 페이지 수
        
		List<ProductDTO> list;
		
		if("N".equals(status)) {
			listCount = dao.getProductCount('N');
			list = dao.selectProductList(currentPage, boardLimit, 'N');
		}else if("Y".equals(status)) {
			listCount = dao.getProductCount('Y');
			list = dao.selectProductList(currentPage, boardLimit, 'Y');
		}else {
		listCount = dao.getProductListCount();
        list = dao.getProduct(currentPage, boardLimit);
		}
		
		
		// 조회하는 메서드 호출
		ProductDTO product = dao.getProductContent(product_no);
                

		request.setAttribute("Content", product);
		
		
		
		
		return new View("main.go").setUrl("/views/product/product_content.jsp");

		
	}

}
