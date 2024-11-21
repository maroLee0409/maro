package com.global.cart.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.global.action.Action;
import com.global.action.View;
import com.global.cart.model.CartDAO;
import com.global.cart.model.CartDTO;
import com.global.user.model.UsersDTO;

import com.global.utils.ScriptUtil;

public class CartDeleteAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
        UsersDTO user = (UsersDTO) session.getAttribute("user");
        
        if (user == null) {
            ScriptUtil.sendScript(response, "로그인이 필요합니다.", "cartList.do");
            return null;
        }
		
        int cart_no = Integer.parseInt(request.getParameter("no").trim());
		
     // CartDAO 객체 가져오기
        CartDAO dao = CartDAO.getInstanceCart();

        // 장바구니 삭제 작업 수행
        int check = dao.deleteCart(cart_no);
        
        // 삭제 성공 여부에 따른 처리
        if (check > 0) {
            ScriptUtil.sendScript(response, "장바구니 삭제 성공", "cart_list.do");
            
        } else {
            ScriptUtil.sendScript(response, "장바구니 삭제 실패", null);
        }

        return null;
	}

}


