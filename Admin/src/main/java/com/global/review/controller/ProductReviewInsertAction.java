package com.global.review.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardCategoryDTO;
import com.global.board.model.BoardDAO;
import com.global.product.model.ProductCategoryDAO;
import com.global.product.model.ProductCategoryDTO;


public class ProductReviewInsertAction implements Action{

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		ProductCategoryDAO productCategoryDAO = ProductCategoryDAO.getInstance();
		BoardDAO boardDao = BoardDAO.getInstance();
		
		List<ProductCategoryDTO> productCategoryList = productCategoryDAO.getCategoryList();
		List<BoardCategoryDTO> boardCategoryList = boardDao.selectBoardCategoryList();
		
        // request에 필요한 속성 설정
        request.setAttribute("productCategoryList", productCategoryList);
        request.setAttribute("boardCategoryList", boardCategoryList);
		
		return new View("main.go").setUrl("/views/review/product_review_insert.jsp");
	}
	
	

}
