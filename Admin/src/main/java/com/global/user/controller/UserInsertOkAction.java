package com.global.user.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.user.model.UsersDAO;
import com.global.user.model.UsersDTO;

public class UserInsertOkAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String user_id = request.getParameter("user_id").trim();
		String user_pwd = request.getParameter("user_password").trim();
		String user_name = request.getParameter("name").trim();
		String user_email = request.getParameter("email").trim();
		
		UsersDTO dto = new UsersDTO();
		
		dto.setUserId(user_id);
		dto.setPassword(user_pwd);
		dto.setName(user_name);
		dto.setEmail(user_email);
		
		UsersDAO dao = UsersDAO.getInstance();
		
		int check = dao.insertUser(dto);
		
		PrintWriter out = response.getWriter();
		
		if(check > 0) {
			out.println("<script>");
			out.println("alert('유저 추가 성공')");
			out.println("location.href='userInsert_select.do'");
			out.println("</script>");
		}
		
		else {
			out.println("<script>");
			out.println("alert('유저 추가 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return null;
	}
}