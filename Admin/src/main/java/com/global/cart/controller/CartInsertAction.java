package com.global.cart.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.cart.model.CartDAO;
import com.global.cart.model.CartDTO;
import com.global.utils.ScriptUtil;

public class CartInsertAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		int product_no = Integer.parseInt(request.getParameter("no").trim());
		int user_no = Integer.parseInt(request.getParameter("user_no").trim());
		
		
		CartDTO dto = new CartDTO();
		CartDAO dao = CartDAO.getInstanceCart();
		int check = dao.insertCart(dto, user_no, product_no);
		
		if (check > 0) {
			ScriptUtil.sendScript(response, "장바구니 상품 추가 성공","cart_list.do");
			
        } 
        
        else {
        	ScriptUtil.sendScript(response, "장바구니 상품 추가 실패", null);
        }
		
		return null;
	}

}
