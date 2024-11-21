package com.global.product.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.product.model.ProductCategoryDAO;
import com.global.product.model.ProductCategoryDTO;
import com.global.product.model.ProductDAO;
import com.global.product.model.ProductDTO;

public class ModifyProductAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String product_no = request.getParameter("no").trim();
		
		String status = request.getParameter("status") == null ? "" : request.getParameter("status");
    	int currentPage = request.getParameter("currentPage") == null ? 0 : Integer.parseInt(request.getParameter("currentPage"));
		
		ProductDAO dao = ProductDAO.getInstance();
		
		ProductCategoryDAO categoryDAO = ProductCategoryDAO.getInstance();
		
		ProductDTO dto = dao.getProductContent(product_no);
		
		List<ProductCategoryDTO> list = categoryDAO.getCategoryList();
		
		// request에 필요한 속성 설정
    	request.setAttribute("status", status);
    	request.setAttribute("currentPage", currentPage);
		request.setAttribute("Modify", dto);
		request.setAttribute("CategoryList", list);
		
		return new View("main.go").setUrl("/views/product/productModify.jsp");
		
		
		
	}

}
