<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>PacknGo</title>
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-white text-black font-sans">

	<!-- 헤더: 로고 -->
	<header class="w-full border-b py-10 px-6 relative flex items-center">
		<h1
			class="absolute left-1/2 transform -translate-x-1/2 text-4xl font-extrabold tracking-wide leading-snug">
			PacknGo</h1>
	</header>

	<br>
	<br>
	<br>

	<!-- 로그인 UI 시작 -->
	<main class="flex flex-col items-center justify-center py-12">
		<h2 class="text-2xl font-bold mb-10">로그인</h2>

		<br>

		<!-- 로그인 폼 -->
		<form action="customer_login_ok.go" method="post" class="w-96 space-y-6">

			<!-- 아이디 -->
			<input type="text" name="user_id" id="user_id" required
				placeholder="아이디"
				class="w-full border-0 border-b border-black placeholder-gray-400 focus:outline-none focus:ring-0 focus:border-black" />
			<br>
			<br>

			<!-- 비밀번호 -->
			<input type="password" name="user_pwd" id="user_pwd" required
				placeholder="비밀번호"
				class="w-full border-0 border-b border-black placeholder-gray-400 focus:outline-none focus:ring-0 focus:border-black" />
			<br>
			<br> <input type="submit" value="로그인"
				class="w-full bg-black text-white py-3 text-base font-semibold hover:bg-gray-800 transition" />

		</form>

		<br>
		<!-- 회원가입 버튼 -->
		<form action="register.jsp" method="get" class="w-96 mt-4">
			<input type="submit" value="회원가입"
				class="w-full border border-black text-black py-3 text-base font-semibold hover:bg-gray-100 transition" />
		</form>

		<!-- 에러 메시지 -->
		<c:if test="${not empty error}">
			<p class="text-red-500 mt-6">${error}</p>
		</c:if>
	</main>

</body>
</html>