package com.global.listener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.io.File;
import java.util.List;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        Boolean isSaved = (Boolean) se.getSession().getAttribute("isSaved");
        
        // 세션이 만료되거나 종료될 때, 게시글 저장이 완료되지 않았을 경우에만 파일 삭제
        if (isSaved == null || !isSaved) {
            List<String> uploadedFiles = (List<String>) se.getSession().getAttribute("uploadedFiles");

            if (uploadedFiles != null) {
                for (String filePath : uploadedFiles) {
                    File file = new File(filePath);
                    if (file.exists()) {
                        file.delete(); // 파일 삭제
                    }
                }
            }
        }
    }
}
