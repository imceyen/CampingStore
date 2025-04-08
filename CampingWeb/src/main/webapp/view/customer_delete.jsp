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



<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>회원 탈퇴</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>

<body class="bg-white font-sans text-black">

    <!-- 로고 -->
    <header class="text-center py-6 border-b">
        <h1 class="text-3xl font-bold"><span class="text-black">PacknGo</span></h1>
    </header>

    <!-- 메인 -->
    <main class="max-w-xl mx-auto py-12 px-4">
        <h2 class="text-2xl font-bold text-center mb-12">회원 탈퇴</h2>

        <form action="customer_delete_ok.go" method="post" class="space-y-8">
            <p class="text-center text-sm text-gray-600 leading-relaxed">
                탈퇴하면 회원님의 정보는 모두 삭제되며<br>
                복구할 수 없습니다. 정말로 탈퇴하시겠습니까?
            </p>

            <div class="flex flex-col gap-3">
                <button type="submit"
                    class="bg-black text-white py-3 rounded text-lg hover:bg-gray-900">탈퇴하기</button>

                <button type="button"
                    onclick="history.back();"
                    class="border border-black text-black py-3 rounded text-lg hover:bg-gray-100">취소</button>
            </div>
        </form>
    </main>
</body>
</html>