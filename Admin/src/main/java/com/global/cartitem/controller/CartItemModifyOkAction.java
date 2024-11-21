package com.global.cartitem.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.cartitem.model.CartItemDAO;
import com.global.cartitem.model.CartItemDTO;
import com.global.utils.ScriptUtil;

public class CartItemModifyOkAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		int user_no = Integer.parseInt(request.getParameter("no").trim());
		int cartitem_no = Integer.parseInt(request.getParameter("cartItem_no").trim());
		int quantity = Integer.parseInt(request.getParameter("quantity").trim());
		
		CartItemDTO dto = new CartItemDTO();
		
		dto.setQuantity(quantity);
		dto.setCartItem_no(cartitem_no);

		
		CartItemDAO dao = CartItemDAO.getInstanceCartItem();
		
		int check = dao.modifyCartItem(dto);
		
		if (check > 0) {
				ScriptUtil.sendScript(response, "장바구니 상품 수정 성공", "cartItem_list.do?user_no="+ user_no);
				
	        } 
	        
	        else {
	        	ScriptUtil.sendScript(response, "장바구니 상품 수정 실패", null);
	        }
			
			return null;
		
	}
}
