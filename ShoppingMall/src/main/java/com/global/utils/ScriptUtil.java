package com.global.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ScriptUtil {
    
    public static void sendScript(HttpServletResponse response, String alertMessage, String redirectUrl) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('" + alertMessage + "');");
        if(redirectUrl != null && !redirectUrl.isEmpty()) {
            out.println("location.href='" + redirectUrl + "';");
        } else {
            out.println("history.go(-1);");
        }
        out.println("</script>");
        out.flush();
    }
}