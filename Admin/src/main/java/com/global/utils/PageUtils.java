package com.global.utils;

public class PageUtils {
	public static PageInfo getPageInfo(int currentPage, int listCount, int boardLimit, int pageLimit) {
		int maxPage; // 전체 페이지 중 가장 마지막 페이지
		int startPage; // 페이지 하단에 보여질 시작 페이지
		int endPage; // 페이지 하단에 보여질 마지막 페이지

		// (전체 게시글 수 / 한 페이지에 보여질 개수) 결과를 올림 처리
		maxPage = (int) Math.ceil((double) listCount / boardLimit);

		// 나의 현재 페이지(currentPage)에서 pageLimit만큼을 나누고 다시 곱한 뒤 1을 더한다
		startPage = (currentPage - 1) / pageLimit * pageLimit + 1;

		// 현재 페이지에서 보여질 마지막 페이지 수
		endPage = startPage + pageLimit - 1;

		// 하지만 페이지 수가 총 페이지 수보다 클 경우
		if (maxPage < endPage) {
			endPage = maxPage;
		}

		return new PageInfo(currentPage, listCount, pageLimit, maxPage, startPage, endPage, boardLimit);
	}
}
