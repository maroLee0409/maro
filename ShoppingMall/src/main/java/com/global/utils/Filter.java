package com.global.utils;

import java.sql.Date;

public abstract class Filter {
    private String isDeleted;    // 삭제 여부
    private Date startDate;      // 작성일 시작 날짜
    private Date endDate;        // 작성일 종료 날짜

    // Getters and Setters
    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
