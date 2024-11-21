package com.global.delivery.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.delivery.model.DeliveryDAO;
import com.global.delivery.model.DeliveryDTO;
import com.global.order.model.OrderDAO;
import com.global.order.model.OrderDTO;

public class UpdateDeliveryAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		int o;
		int order_no = Integer.parseInt(request.getParameter("no").trim());
		
		DeliveryDAO dao = DeliveryDAO.getInstance();
		
		DeliveryDTO dto = dao.getDelivery(order_no);
		
		request.setAttribute("Modify", dto);
		
		return new View("main.go").setUrl("/views/order/delivery_modify.jsp");
	}

}
