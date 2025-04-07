<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	String userId = (String) session.getAttribute("userId");
	if (userId == null || !"admin".equals(userId)) {
		response.sendRedirect("camping_login.jsp");
		return;
	}
	%>

	<h1>관리자 전용 페이지</h1>
	<p>
		환영합니다,
		<%=userId%>님!
	</p>
</body>
</html>