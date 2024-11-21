package com.global.order.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.order.model.OrderDAO;
import com.global.order.model.OrderDTO;
import com.global.utils.ScriptUtil;

public class UpdateOkOrderAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int order_no = Integer.parseInt(request.getParameter("order_no").trim());
		String status = request.getParameter("status").trim();
		int user_no = Integer.parseInt(request.getParameter("user_no").trim());
		int total_amount = Integer.parseInt(request.getParameter("total_amount").trim());
		
		OrderDTO dto = new OrderDTO();
		
		dto.setOrder_no(order_no);
		dto.setStatus(status);
		dto.setUser_no(user_no);
		dto.setTotal_amount(total_amount);
		
		
		OrderDAO dao = OrderDAO.getInstance();
		int o;
		int check = dao.ModifyOrder(dto);
		
		if (check > 0) {
			ScriptUtil.sendScript(response, "배송 상태 수정 성공", "order_list.do");
		} else {
			ScriptUtil.sendScript(response, "배송 상태 수정 실패!!!", null);
		}
		
		return null;
		
	}

}
