package com.global.cartitem.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.global.action.Action;
import com.global.action.View;
import com.global.cartitem.model.CartItemDAO;
import com.global.cartitem.model.CartItemDTO;
import com.global.user.model.UsersDTO;
import com.global.utils.ScriptUtil;

public class CartItemModifyAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	
			 HttpSession session = request.getSession();
		     UsersDTO user = (UsersDTO) session.getAttribute("user");
		        
		        if (user == null) {
		            ScriptUtil.sendScript(response, "로그인이 필요합니다.", "boardList.do");
		            return null;
		        }
			

		        int no = Integer.parseInt(request.getParameter("no").trim());
				
				CartItemDAO dao = CartItemDAO.getInstanceCartItem();
				
				CartItemDTO dto = dao.getContentCartItem(no);
				
				System.out.println("dto.getCartItem_cartNo() : " + dto.getCart_no());
				
				request.setAttribute("Modify", dto);
				
				return new View("main.go").setUrl("/views/cartitem/cartitemModify.jsp");
			
	}
}
