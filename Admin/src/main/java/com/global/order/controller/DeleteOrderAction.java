package com.global.order.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.delivery.model.DeliveryDAO;
import com.global.order.model.OrderDAO;
import com.global.orderitem.model.OrderItemDAO;
import com.global.utils.ScriptUtil;

public class DeleteOrderAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int o;
		int order_no = Integer.parseInt(request.getParameter("no").trim());
		
		DeliveryDAO dao3 = DeliveryDAO.getInstance();
		
		OrderItemDAO dao2 = OrderItemDAO.getInstance();
		
		OrderDAO dao1 = OrderDAO.getInstance();
		
		int check3 = dao3.DeleteDelivery(order_no);

		int check1 = dao2.DeleteOrderItem(order_no);
		
		int check2 = dao1.DeleteOrder(order_no);
		
		if (check1 > 0) {
			ScriptUtil.sendScript(response, "주문 삭제 성공", "order_list.do");
		} else {
			ScriptUtil.sendScript(response, "주문 삭제 실패!!!", null);
		}
		
		return null;
	}

}
