package com.global.product.controller;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.global.action.Action;
import com.global.action.View;
import com.global.product.model.ProductDAO;
import com.global.product.model.ProductDTO;
import com.global.product.model.ProductImageDAO;
import com.global.product.model.ProductImageDTO;
import com.global.user.model.UsersDTO;
import com.global.utils.ScriptUtil;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;


public class InsertProductOkAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// DTO 설정
		ProductDTO product = new ProductDTO();
				
		
		// 첨부파일이 저장될 위치(경로) 설정
		String root = request.getSession().getServletContext().getRealPath("/");
		String saveFolder = root + "/resources/product/img";
		int fileSize = 10 * 1024 * 1024; // 10MB

		// 파일 업로드를 위한 MultipartRequest 객체 생성
		MultipartRequest multi = new MultipartRequest(request, saveFolder, fileSize, "UTF-8",
				new DefaultFileRenamePolicy());

		// 파라미터 가져오기 및 null 체크
		String product_name = getParameterSafe(multi, "product_name");
		String category_no = getParameterSafe(multi, "category_no");
		String product_info = getParameterSafe(multi, "product_info");
		int product_price = parseIntSafe(multi.getParameter("product_price"));
		int stock_quantity = parseIntSafe(multi.getParameter("stock_quantity"));
		int user_no = parseIntSafe(multi.getParameter("user_no")); 
		String product_image_info = getParameterSafe(multi, "product_image_info");
		
		UsersDTO user = (UsersDTO)request.getSession().getAttribute("user");
		
		
		
		
		
		
		
		File upload_file = multi.getFile("image_url");
		
		
		if(upload_file != null) {
			String fileName = upload_file.getName();
			
			// 날짜 객체 생성
			Calendar cal = Calendar.getInstance();
			
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);

			//.........../uploadFile/2024-08-20
			String homedir = saveFolder + "/" + year + "-" + month + "-" + day;
			String homedirset = year + "-" + month + "-" + day;
			
			File path1 = new File(homedir);
			
			if(!path1.exists()) {
				path1.mkdir();
			}
			// 파일을 만들어 보자
			
			
			upload_file.renameTo(new File(homedir+"/"+fileName));
			
			// 실제로 DB에 저장되는 파일 이름
			// "/2024-08-20/UserId_파일명" 으로 저장 예정
			
			String fileDBName = "/resources/product/img/" + homedirset + "/" +fileName;
			
			product.setImage_url(fileDBName);
			
		}
		
		
		product.setName(product_name);
		product.setCategory_no(category_no);
		product.setDescription(product_info);
		product.setPrice(product_price);
		product.setStock_quantity(stock_quantity);
		product.setUser_no(user_no);
		product.setImg_description(product_image_info);
		

		// DAO 객체 생성 및 데이터베이스에 제품 정보 저장
		ProductDAO dao = ProductDAO.getInstance();
		

		int check = dao.insertProduct(product);

		if (check > 0) {
			// 이미지 파일명 가져오기
			// 이미지 정보 저장
			ProductImageDAO imageDAO = ProductImageDAO.getInstance();
			
			String image_url = product.getImage_url();

			if (image_url == null) {
				int img = imageDAO.insertNullProduct(product);
				if (img > 0) {
					ScriptUtil.sendScript(response, "상품 등록 성공( 이미지 x )", "productList.do");
					
				}
			} else {
				
				ProductImageDTO image = new ProductImageDTO();
				
				image.setImage_url(product.getImage_url());
				image.setProudct_no(product.getProduct_no());
				image.setDescription(product.getImg_description());
				
				int img = imageDAO.insertImgProduct(image);
				
				if (img > 0) {

					ScriptUtil.sendScript(response, "상품 등록 성공", "productList.do");

				}
			}

		} else {

			ScriptUtil.sendScript(response, "상품 등록 실패!!!", null);

		}

		return null;
	}

	// 안전하게 파라미터 값을 가져오는 메서드
	private String getParameterSafe(MultipartRequest multi, String paramName) {
		String value = multi.getParameter(paramName);
		return (value != null) ? value.trim() : "";
	}

	// 안전하게 정수로 변환하는 메서드
	private int parseIntSafe(String value) {
		try {
			return Integer.parseInt(value.trim());
		} catch (NumberFormatException e) {
			return 0; // 적절히 처리하거나 기본값 설정
		}
	}

}
