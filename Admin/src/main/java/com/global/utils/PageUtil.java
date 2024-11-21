package com.global.utils;

import javax.servlet.http.HttpServletRequest;
import com.global.utils.PageInfo;

public class PageUtil {
    
    public static PageInfo getPageInfo(HttpServletRequest request, int listCount, int boardLimit, int pageLimit) {
        // 현재 페이지
        int currentPage = 1;
        if (request.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        }
        
        // 전체 페이지 수 계산
        int maxPage = (int) Math.ceil((double) listCount / boardLimit);

        // 시작 페이지 계산
        int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
        
        // 끝 페이지 계산
        int endPage = startPage + pageLimit - 1;
        if (maxPage < endPage) {
            endPage = maxPage;
        }
        
        // PageInfo 객체 생성 및 반환
        return new PageInfo(currentPage, listCount, pageLimit, maxPage, startPage, endPage, boardLimit);
    }
}
