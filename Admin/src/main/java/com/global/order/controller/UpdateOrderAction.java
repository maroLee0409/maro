package com.global.order.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.order.model.OrderDAO;
import com.global.order.model.OrderDTO;

public class UpdateOrderAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int o;
		int order_no = Integer.parseInt(request.getParameter("no").trim());
		
		OrderDAO dao = OrderDAO.getInstance();
		
		OrderDTO dto = dao.getOrder(order_no);
		
		request.setAttribute("Modify", dto);
		
		return new View("main.go").setUrl("/views/order/order_Modify.jsp");
	}

}
