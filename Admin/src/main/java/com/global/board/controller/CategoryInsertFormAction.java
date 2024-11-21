package com.global.board.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.global.action.Action;
import com.global.action.View;
import com.global.user.model.UsersDTO;
import com.global.utils.ScriptUtil;

public class CategoryInsertFormAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        UsersDTO user = (UsersDTO) session.getAttribute("user");

        if (user == null || user.getUserType() != "ADMIN") {
        	ScriptUtil.sendScript(response, "로그인을 하지 않았거나 관리자가 아닙니다.", null);
            return null;
        }
        
		return new View("main.go").setUrl("/views/board/categoryInsertForm.jsp");
	}

}
