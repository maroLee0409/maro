package com.global.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * 서블릿 컨텍스트 리스너 클래스입니다.
 * <p>
 * 이 클래스는 서블릿 컨텍스트가 초기화될 때 프로퍼티 파일을 로드하고, 이를 서블릿 컨텍스트에 저장하여 다른 서블릿 및 JSP에서 사용할 수
 * 있도록 합니다. 또한, 컨텍스트가 종료될 때 필요한 정리 작업을 수행할 수 있는 메서드도 포함되어 있습니다.
 * </p>
 */

public class FrontControllerContextListener implements ServletContextListener {
	/**
	 * 서블릿 컨텍스트가 초기화될 때 호출됩니다.
	 * <p>
	 * 이 메서드에서는 프로퍼티 파일의 경로를 가져와 해당 파일을 읽고, 읽어들인 프로퍼티를 서블릿 컨텍스트의 속성으로 설정합니다. 이를 통해 웹
	 * 애플리케이션의 다른 서블릿과 JSP에서 프로퍼티에 접근할 수 있습니다.
	 * </p>
	 * 
	 * @param sce 서블릿 컨텍스트 이벤트 객체로, 서블릿 컨텍스트에 대한 정보를 제공합니다.
	 */

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// 서블릿 컨텍스트 객체를 가져옵니다.
		ServletContext context = sce.getServletContext();

		// 프로퍼티 파일의 실제 경로를 가져옵니다.
		String fileName = context.getRealPath("/WEB-INF/mapping.properties");

		try {
			// FrontControllerInitializer를 사용하여 프로퍼티 파일을 읽어옵니다.
			FrontControllerInitializer initializer = new FrontControllerInitializer(fileName);
			
			// 읽어들인 프로퍼티를 서블릿 컨텍스트에 속성으로 설정합니다.
			context.setAttribute("properties", initializer.getProperties());
		} catch (IOException e) {
			// 프로퍼티 파일을 읽는 데 실패하면 런타임 예외를 던집니다.
			throw new RuntimeException("Failed to load mapping properties file", e);
		}
	}

	/**
	 * 서블릿 컨텍스트가 종료될 때 호출됩니다.
	 * <p>
	 * 이 메서드는 컨텍스트가 종료될 때 필요한 정리 작업을 수행할 수 있도록 사용자가 구현할 수 있는 메서드입니다. 현재 구현에서는 별도의 정리
	 * 작업이 없습니다.
	 * </p>
	 * 
	 * @param sce 서블릿 컨텍스트 이벤트 객체로, 서블릿 컨텍스트에 대한 정보를 제공합니다.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// 필요에 따라 정리 작업을 추가할 수 있습니다.
	}
}
