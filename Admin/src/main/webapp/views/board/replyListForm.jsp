<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<div class="comment-list">
	<h3>댓글</h3>
	<div id="commentsContainer">
		<c:forEach var="comment" items="${comments}">
			<div class="comment" style="margin-left: ${comment.nodeLevel * 20}px;">
				<div class="comment-info">
				    <strong>${comment.userId}</strong>
					<span><fmt:formatDate value="${comment.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
				</div>
				<div class="comment-content">
					<c:out value="${comment.content}" escapeXml="false"/>
	        	</div>
    		</div>
		</c:forEach>
    </div>
</div>
    

