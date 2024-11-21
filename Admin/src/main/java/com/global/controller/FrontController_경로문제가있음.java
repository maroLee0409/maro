package com.global.controller;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.ActionFactory;
import com.global.action.View;
import com.global.utils.PropertyUtils;
import com.global.utils.RequestUtils;

public class FrontController_경로문제가있음 extends HttpServlet {
    private static final long serialVersionUID = -1934459167346141964L;
    
    // 파일의 마지막 수정 시간을 저장하는 변수
    private long lastModifiedTime = 0;
    
    // 프로퍼티 객체를 저장하는 변수
    private Properties properties = new Properties();

    // 서비스 메서드
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 한글 처리 작업 진행
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // 프로퍼티 파일 경로
        String filePath = FrontController_경로문제가있음.class.getResource("/mapping/mapping.properties").getPath();
        File file = new File(filePath);

        // 파일의 마지막 수정 시간 확인
        long currentModifiedTime = file.lastModified();

        // 파일이 수정된 경우 프로퍼티 파일 다시 로드
        if (currentModifiedTime != lastModifiedTime) {
            properties = PropertyUtils.loadProperties(filePath);
            lastModifiedTime = currentModifiedTime;
        }

        // URI와 커맨드를 추출
        String uri = RequestUtils.extractUri(request);
        String command = RequestUtils.extractCommand(uri, request.getContextPath());

        // 커맨드에 해당하는 클래스 이름을 가져온다.
        String namePath = properties.getProperty(command);

        System.out.println("Command: " + command);
        System.out.println("NamePath: " + namePath);
        View view = null;

        if (namePath.endsWith(".jsp")) {
            // JSP로 직접 이동하는 경우
            view = new View(namePath).setUrl(namePath);
        } else {
            // Action 클래스를 동적으로 로딩
            Action action = ActionFactory.createActionInstance(namePath);

            if (action == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Action을 실행하고 결과 처리
            view = action.execute(request, response);
        }

        if (view != null) {
            view.render(request, response);
        }
    }
}
