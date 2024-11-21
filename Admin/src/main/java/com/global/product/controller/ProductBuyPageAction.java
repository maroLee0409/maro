package com.global.product.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.product.model.ProductDAO;
import com.global.product.model.ProductDTO;
import com.global.utils.PageInfo;

public class ProductBuyPageAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		ProductDAO dao = ProductDAO.getInstance();
		
		String product_no = request.getParameter("no").trim(); 
		
		// 조회하는 메서드 호출
		ProductDTO product = dao.getProductBuyContent(product_no);

		request.setAttribute("Content", product);
		
		return new View("main.go").setUrl("/views/product/product_buy_page.jsp");
	}

}
