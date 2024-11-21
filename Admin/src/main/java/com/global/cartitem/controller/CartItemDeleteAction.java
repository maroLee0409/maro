package com.global.cartitem.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.global.action.Action;
import com.global.action.View;
import com.global.cartitem.model.CartItemDAO;
import com.global.cartitem.model.CartItemDTO;
import com.global.user.model.UsersDTO;
import com.global.utils.ScriptUtil;

public class CartItemDeleteAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
        UsersDTO user = (UsersDTO) session.getAttribute("user");
        
        if (user == null) {
            ScriptUtil.sendScript(response, "로그인이 필요합니다.", "cartList.do");
            return null;
        }
		
        int cartItem_no = Integer.parseInt(request.getParameter("no").trim());
        int user_no = Integer.parseInt(request.getParameter("user_no").trim());
        CartItemDAO dao = CartItemDAO.getInstanceCartItem();
        
        int check = dao.deleteCartItem(cartItem_no);
        
        if (check > 0) {
        	dao.updateSequenceCartItem(cartItem_no);
        	
			ScriptUtil.sendScript(response, "장바구니 상품 삭제 성공", "cartItem_list.do?user_no="+user_no);
        } 
        
        else {
        	ScriptUtil.sendScript(response, "장바구니 상품 삭제 실패", null);
        }
		
		return null;
	}
}
