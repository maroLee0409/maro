package com.global.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.admin.model.AdminDAO;

public class AdminCategoryDeleteOkAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		AdminDAO dao = AdminDAO.getInstance();
		
		int check = dao.deleteAdmin();
		
		PrintWriter out = response.getWriter();
		
		if(check > 0) {
			
			out.println("<script>");
			out.println("alert('직책 삭제 성공')");
			out.println("location.href='select.do'");
			out.println("</script>");
		}
		
		else {
			out.println("<script>");
			out.println("alert('직책 삭제 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return new View("main.go").setUrl("/views/admin/admin_category_list.jsp");
	}
}