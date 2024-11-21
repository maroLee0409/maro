package com.global.user.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.admin.model.AdminDAO;
import com.global.admin.model.AdminDTO;
import com.global.user.model.UsersDAO;
import com.global.user.model.UsersDTO;

public class UserInsertListAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		UsersDAO dao = UsersDAO.getInstance();
		
		List<UsersDTO> list = dao.getUsersList();
		
		request.setAttribute("List", list);
		
		return new View("main.go").setUrl("/views/admin/user_insert_list.jsp");
	}
}