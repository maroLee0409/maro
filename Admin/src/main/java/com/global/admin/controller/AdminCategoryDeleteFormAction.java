package com.global.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;

public class AdminCategoryDeleteFormAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String role_code = request.getParameter("rolecode").trim();
		
		request.setAttribute("Code", role_code);
		
		return new View("main.go").setUrl("/views/admin/admin_category_delete.jsp");
	}
}