package com.global.product.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.product.model.ProductCategoryDAO;
import com.global.utils.ScriptUtil;

public class ProductDeleteCategoryAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String category_no = request.getParameter("no").trim();
		
		ProductCategoryDAO dao = ProductCategoryDAO.getInstance();
		
		int check = dao.DeleteCategory(category_no);
		
		if (check > 0) {
			ScriptUtil.sendScript(response, "카테고리 삭제 성공", "productCategoryList.do");
		} else {
			ScriptUtil.sendScript(response, "카테고리 삭제 실패!!!", null);
		}
		
		return null;
	}

}
