package com.global.product.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.ActionForward;
import com.global.action.View;
import com.global.product.model.ProductCategoryDAO;
import com.global.product.model.ProductCategoryDTO;

public class ProductModifyCategoryAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		ProductCategoryDAO dao = ProductCategoryDAO.getInstance();

		String category_no = request.getParameter("no").trim(); 
		int currentPage = request.getParameter("currentPage") == null ? 0 : Integer.parseInt(request.getParameter("currentPage"));
				
		
		ProductCategoryDTO dto = dao.getCategoryContent(category_no);
		
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("Modify", dto);
		
		return new View("main.go").setUrl("/views/product/productCategoryModify.jsp");
	}

}
