package com.global.product.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.product.model.ProductCategoryDAO;
import com.global.product.model.ProductDAO;
import com.global.product.model.ProductImageDAO;
import com.global.utils.ScriptUtil;

public class DeleteProductAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int product_no = Integer.parseInt(request.getParameter("no"));
		
		ProductDAO dao = ProductDAO.getInstance();
		
		ProductImageDAO image = ProductImageDAO.getInstance();
				
		int img = image.DeleteProductImage(product_no);
		
		if(img > 0) {
		
			int check = dao.DeleteProduct(product_no);
			
			if (check > 0) {
			
				ScriptUtil.sendScript(response, "상품 삭제 성공", "productList.do");
			} 
			else {
				ScriptUtil.sendScript(response, "상품 삭제 실패!!!", null);
				
			}
		
		} else {
			ScriptUtil.sendScript(response, "상품 삭제 실패!!!", null);
			
		}
		return null;
	}

}
