<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>로그인</title>
    <script src="https://cdn.tailwindcss.com"></script>
    
<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
    <script>
        alert('<%= error %>');
    </script>
<%
    }
%>
</head>
<body class="bg-white min-h-screen flex flex-col items-center justify-start pt-10">

    <!-- 헤더 (로고 중앙 정렬) -->
    <header class="w-full border-b py-4 flex justify-center">
        <div class="text-xl font-bold">PacknGo</div>
    </header>

    <!-- 로그인 폼 -->
    <form action="customer_login_ok.go" method="post" class="w-full max-w-sm mt-10 px-6">
    	<h2 class="text-2xl font-bold text-center mb-10">로그인</h2>

    	<input type="text" name="id" placeholder="아이디"
        	class="w-full border-b border-black py-3 mb-6 focus:outline-none" required>

    	<input type="password" name="pwd" placeholder="비밀번호"
        	class="w-full border-b border-black py-3 mb-6 focus:outline-none" required>

    <button type="submit" class="w-full bg-black text-white py-3 rounded hover:bg-gray-900 mb-4">
        로그인
    </button>

    <button type="button" onclick="location.href='camping_signUp.go';"
        class="w-full border border-black text-black py-3 rounded hover:bg-gray-100">
        회원 가입
    </button>
</form>

</body>
</html>
