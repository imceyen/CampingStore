<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 로그인 여부 확인. 로그인이 안되어있을 경우 로그인 화면으로 이동 -->
<%
    String id = (String) session.getAttribute("cus_id");

    if (id == null) {
        response.sendRedirect("customer_login.go");
        return;
    }
%>

<!-- 어떤 목적으로 비밀번호 재확인 하는지 (정보 변경 or 회원 탈퇴) -->
<%
	String target = request.getParameter("target");

	if (target == null) target = "edit";  // 기본 목적은 변경.
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>비밀번호 재확인</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-white flex justify-center items-center min-h-screen">

    <form action="customer_check_pwd_ok.go?target=<%= target %>" method="post" class="w-full max-w-md p-8 bg-white">
        <h2 class="text-2xl font-bold text-center mb-4">비밀번호 재확인</h2>
        <p class="text-center text-sm text-gray-600 leading-relaxed mb-8">
            회원님의 소중한 개인정보를 안전하게 보호하고<br>
            개인정보 도용으로 인한 피해를 예방하기 위하여<br>
            비밀번호를 확인합니다.<br><br>
            비밀번호는 타인에게 노출되지 않도록 주의해주세요.<br>
        </p>

        <!-- 아이디 표시 -->
        <div class="mb-6">
            <input type="text" name="cus_id" value="<%= id %>" readonly 
                class="w-full border-b border-gray-800 py-2 text-lg focus:outline-none bg-transparent">
        </div>

        <!-- 비밀번호 입력 -->
        <div class="mb-6">
            <input type="password" name="cus_pwd" placeholder="비밀번호"
                class="w-full border-b border-gray-800 py-2 text-lg focus:outline-none bg-transparent" required>
        </div>

        <!-- 버튼 -->
        <div class="flex flex-col gap-3">
            <button type="submit"
                class="bg-black text-white py-3 rounded text-lg hover:bg-gray-900">확인</button>
                
            <button type="button"
                onclick="history.back();"
                class="border border-black text-black py-3 rounded text-lg hover:bg-gray-100">취소</button>
        </div>
    </form>
</body>
</html>