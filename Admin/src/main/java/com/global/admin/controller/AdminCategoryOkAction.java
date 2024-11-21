package com.global.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.admin.model.AdminDAO;
import com.global.admin.model.AdminDTO;

public class AdminCategoryOkAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String admin_rolecode = request.getParameter("role_code").trim();
		String admin_rolename = request.getParameter("role_name").trim();
		
		AdminDTO dto = new AdminDTO();
		
		dto.setRoleCode(admin_rolecode);
		dto.setRoleName(admin_rolename);
		
		AdminDAO dao = AdminDAO.getInstance();
		
		int check = dao.insertAdminCategory(dto);
		
		PrintWriter out = response.getWriter();
		
		if(check > 0) {
			out.println("<script>");
			out.println("alert('관리자 직책 추가 성공')");
			out.println("location.href='adminCategory_select.do'");
			out.println("</script>");
		}
		
		else {
			out.println("<script>");
			out.println("alert('관리자 직책 추가 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return null;
	}
}