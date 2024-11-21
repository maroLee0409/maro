package com.global.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.admin.model.AdminDAO;
import com.global.admin.model.AdminDTO;

public class AdminCategorySearchListAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String search_field = request.getParameter("field").trim();
		String search_keyword = request.getParameter("keyword").trim();
		
		AdminDAO dao = AdminDAO.getInstance();
		
		List<AdminDTO> searchList = dao.searchCategoryAdminList(search_field, search_keyword);
		
		request.setAttribute("Search", searchList);
		
		return new View("main.go").setUrl("/views/admin/Admin_category_searchList.jsp");
	}
}