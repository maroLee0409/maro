package com.global.delivery.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.delivery.model.DeliveryDAO;
import com.global.delivery.model.DeliveryDTO;
import com.global.utils.ScriptUtil;

public class UpdateOkDeliveryAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int delivery_no = Integer.parseInt(request.getParameter("delivery_no").trim());
		int order_no = Integer.parseInt(request.getParameter("order_no").trim());
		String delivery_status = request.getParameter("delivery_status").trim();
		int o;
		DeliveryDTO dto = new DeliveryDTO();
		
		dto.setDelivery_no(delivery_no);
		dto.setOrder_no(order_no);
		dto.setDelivery_status(delivery_status);
			
		DeliveryDAO dao = DeliveryDAO.getInstance();
		
		int check = dao.getDelivery(dto);
		
		if (check > 0) {
			ScriptUtil.sendScript(response, "배송 상태 수정 성공", "delivery_list.do");
		} else {
			ScriptUtil.sendScript(response, "배송 상태 수정 실패!!!", null);
		}	
		return null;
	}

}
