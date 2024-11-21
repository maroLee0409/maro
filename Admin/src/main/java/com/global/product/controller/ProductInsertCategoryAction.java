package com.global.product.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.product.model.ProductCategoryDAO;
import com.global.product.model.ProductCategoryDTO;
import com.global.product.model.ProductCategoryImageDAO;
import com.global.product.model.ProductCategoryImageDTO;
import com.global.product.model.ProductImageDAO;
import com.global.product.model.ProductImageDTO;
import com.global.utils.ScriptUtil;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class ProductInsertCategoryAction implements Action {

	
		@Override
		public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

			ProductCategoryDTO dto = new ProductCategoryDTO();
			
			// 첨부파일이 저장될 위치(경로) 설정
			String root = request.getSession().getServletContext().getRealPath("/");
			String saveFolder = root + "/resources/product/img";
			int fileSize = 10 * 1024 * 1024; // 10MB

			// 파일 업로드를 위한 MultipartRequest 객체 생성
			MultipartRequest multi = new MultipartRequest(request, saveFolder, fileSize, "UTF-8", new DefaultFileRenamePolicy());

			// 파라미터 가져오기 및 null 체크
			String category_no = getParameterSafe(multi, "category_no");
			String name = getParameterSafe(multi, "name").trim();
			String description = getParameterSafe(multi, "description").trim();
			String alt_text = getParameterSafe(multi, "alt_text");
			
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
				
				dto.setImage_url(fileDBName);
				
			}
			
			dto.setCategory_No(category_no);
			dto.setName(name);
			dto.setDescription(description);
			dto.setAlt_text(alt_text);
			
			ProductCategoryDAO dao = ProductCategoryDAO.getInstance();
			
			int check = dao.insertCategory(dto);
			
			if (check > 0) {
				// 이미지 파일명 가져오기
				// 이미지 정보 저장
				ProductCategoryImageDAO imageDAO = ProductCategoryImageDAO.getInstance();
				
				String image_url = dto.getImage_url();

				if (image_url == null) {
					int img = imageDAO.insertNullProductCategory(dto);
					if (img > 0) {
						ScriptUtil.sendScript(response, "카테고리 등록 성공( 이미지 x )", "productCategoryList.do");
						
					}
				} else {
					
					ProductCategoryImageDTO image = new ProductCategoryImageDTO();
					
					
					image.setImage_url(dto.getImage_url());
					image.setCategory_no(dto.getCategory_No());
					image.setAlt_text(dto.getAlt_text());
					
					int img = imageDAO.insertImgProductCategory(image);
					
					if (img > 0) {

						ScriptUtil.sendScript(response, "카테고리 등록 성공", "productCategoryList.do");

					}
				}

			} else {

				ScriptUtil.sendScript(response, "카테고리 등록 실패!!!", null);

			}

			
			
			return null;
		}
		// 안전하게 파라미터 값을 가져오는 메서드
		private String getParameterSafe(MultipartRequest multi, String paramName) {
			String value = multi.getParameter(paramName);
			return (value != null) ? value.trim() : "";
		}


}