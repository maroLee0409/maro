package com.global.review.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.global.action.Action;
import com.global.action.View;
import com.global.order.model.OrderDAO;
import com.global.order.model.OrderDTO;
import com.global.user.model.UsersDTO;

public class ContentAction implements Action{

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	
		// 세션으로 회원 정보 불러오기
		HttpSession session = request.getSession();
		UsersDTO user = (UsersDTO) session.getAttribute("user");
				
		OrderDAO dao = OrderDAO.getInstance();

		List<OrderDTO> list = new ArrayList<OrderDTO>();
		list = dao.getOrderList();
				
		request.setAttribute("list", list);
		request.setAttribute("user", user);
				
				
		return new View("main.go").setUrl("/views/review/content.jsp");
	}

}
