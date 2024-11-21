package com.global.order.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.order.model.OrderDAO;
import com.global.order.model.OrderDTO;
import com.global.orderitem.model.OrderItemDAO;
import com.global.product.model.ProductDAO;
import com.global.product.model.ProductDTO;
import com.global.utils.ScriptUtil;

public class InsertOrderAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
	  int product_no = Integer.parseInt(request.getParameter("product_no").trim());
	  int user_no = Integer.parseInt(request.getParameter("user_no").trim());
	  int quantity = Integer.parseInt(request.getParameter("quantity").trim());
	  int price = Integer.parseInt(request.getParameter("price").trim());
	  
	  
	  System.out.println("product_no :: " + product_no);
	  
	  OrderDAO orderdao = OrderDAO.getInstance();
	  OrderItemDAO orderitemdao = OrderItemDAO.getInstance();
	  ProductDAO productdao = ProductDAO.getInstance();
	  
	  ProductDTO prodto = new ProductDTO();
	  OrderDTO ordto = new OrderDTO();
	  
	  
	  int res = orderdao.InsertOrder(ordto,user_no,product_no,quantity,price);
	  
	  
	  
	  if (res > 0) {
			ScriptUtil.sendScript(response, "주문 성공","order_list.do");
			
      } 
      
      else {
      	ScriptUtil.sendScript(response, "주문 실패", null);
      }
	  
	  
		
		
		
		return null;
	}

}
