package com.global.board.model;

public class BoardFileUploadDTO {
    private int fileNo;
    private int boardNo;
    private String fileUrl;
    private String fileName;
    private long fileSize;
    private String fileType;
    private String description;
    private java.util.Date uploadedAt;

    // Getters and Setters
    public int getFileNo() {
        return fileNo;
    }

    public void setFileNo(int fileNo) {
        this.fileNo = fileNo;
    }

    public int getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(int boardNo) {
        this.boardNo = boardNo;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.util.Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(java.util.Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
