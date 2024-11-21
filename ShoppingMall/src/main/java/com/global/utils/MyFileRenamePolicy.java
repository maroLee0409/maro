package com.global.utils;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyFileRenamePolicy implements FileRenamePolicy {

	@Override
	public File rename(File originFile) {
		// 전달 받은 원본 파일명을 중복되는 값 없게끔 변경해서 변경된 파일명으로 반환하기
		// 변경명은 파일 업로드 한 시간(년월일시분초) + 0~10만 사이의 랜덤값(5자리의 랜덤값)
		// 을 부여해서 2019101409390012345.jpg
		
		// 현재 시간을 long형으로 받음
		long currentTime = System.currentTimeMillis();
		
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmss");
		
		int ranNum = (int)(Math.random() * 100000); // 5자리의 랜덤 숫자 생성
		
		// 파일명을 변경해도 확장자를 유지하기 위하여 별도로 확장자명 가져오기
		String name = originFile.getName(); // 원본 파일명 알아내기 -> "aaa.png"
		
		String ext = ""; // 확장자만 추출해서 담아줄 변수 미리 선언
		
		int dot = name.lastIndexOf("."); // .의 위치 값 알아내기
		
		if(dot != -1) { // 존재할 경우
			// dot 포함해서 확장자 추출
			ext = name.substring(dot); // .의 위치값부터 마지막까지 전체 추출 --> ".png"
		}
		
		// 최종적으로 수정할 파일명
		String fileName = ft.format(new Date(currentTime)) + ranNum + ext;
		
		// 파일을 변경된 파일명으로 생성
		File newFile = new File(originFile.getParent(), fileName);
		
		return newFile;
	}

}
