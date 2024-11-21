package com.global.board.model;

// 파일 업로드 결과를 위한 내부 클래스
public class UploadResponse {
    private boolean success;
    private String link;
    private String errorMessage;

    public UploadResponse(boolean success, String link, String errorMessage) {
        this.success = success;
        this.link = link;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getLink() {
        return link;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}