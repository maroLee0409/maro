package com.global.utils;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private List<String> path;  // 경로 리스트
    private String current;  // 현재 위치
    private String title;    // 페이지 타이틀 (h1)

    public Location() {
        path = new ArrayList<>();
    }

    public void addPath(String step) {
        path.add(step);
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getCurrent() {
        return current;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    // 경로 출력용
    public String displayBreadcrumb() {
        StringBuilder breadcrumb = new StringBuilder("<nav aria-label=\"breadcrumb\"><ol class=\"breadcrumb\">");
        for (String step : path) {
            breadcrumb.append("<li>").append(step).append("</li>");
        }
        breadcrumb.append("<li class=\"active\" aria-current=\"page\">").append(current).append("</li>");
        breadcrumb.append("</ol></nav>");
        return breadcrumb.toString();
    }
}
