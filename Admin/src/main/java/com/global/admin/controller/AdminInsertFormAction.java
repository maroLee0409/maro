package com.global.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.admin.model.AdminDAO;

public class AdminInsertFormAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		return new View("main.go").setUrl("/views/admin/admin_insert_Form.jsp");
	}
}