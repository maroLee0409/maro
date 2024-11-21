package com.global.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.admin.model.AdminDAO;
import com.global.admin.model.AdminDTO;

public class AdminInsertContentFormAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		int user_no = Integer.parseInt(request.getParameter("userNo").trim());
		
		AdminDAO dao = AdminDAO.getInstance();
		
		AdminDTO content = dao.contentAdmin(user_no);
		
		request.setAttribute("Cont", content);
		
		return new View("main.go").setUrl("/views/admin/admin_content.jsp");
	}
}