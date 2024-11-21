package com.global.orderitem.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.orderitem.model.OrderItemDAO;
import com.global.orderitem.model.OrderItemDTO;

public class OrderItemContentAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int order_no = Integer.parseInt(request.getParameter("no").trim());
		
		
		OrderItemDAO dao = OrderItemDAO.getInstance();
		
		List<OrderItemDTO> list = dao.getOrderItemList(order_no);
		
		request.setAttribute("Content", list);
		
		return new View("main.go").setUrl("/views/order/order_content.jsp");
	}

}
