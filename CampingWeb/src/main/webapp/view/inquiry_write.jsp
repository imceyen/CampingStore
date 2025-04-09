<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    // 로그인 여부 확인
    String id = (String)session.getAttribute("cus_id");

    if (id == null) {
        response.sendRedirect("customer_login.go");
        return;
    }
	
    // 문의 작성창에서 필요한 정보
    String name = (String)session.getAttribute("cus_name");
    Integer customerNo = (Integer)session.getAttribute("cus_no");

    // 현재 날짜
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String today = sdf.format(new Date());

    // 임시 상품 번호
    int productNo = 999;  // 추후 실제 상품 선택 기능이 연결되면 수정
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>1:1 문의 작성</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-white min-h-screen flex flex-col items-center py-10">
    <header class="w-full border-b py-4 flex justify-center">
        <div class="text-xl font-bold">PacknGo</div>
    </header>

    <h2 class="text-2xl font-bold mt-10 mb-4">1:1 문의 작성</h2>


    <form action="inquiry_write_ok.go" method="post" class="w-full max-w-2xl px-6">
        <input type="hidden" name="customer_no" value="<%= customerNo %>">
        <input type="hidden" name="product_no" value="<%= productNo %>">

        <div class="mb-4">
            <label class="block font-semibold">이름</label>
            <input type="text" value="<%= name %>" disabled class="w-full border px-3 py-2 bg-gray-100">
        </div>

        <div class="mb-4">
            <label class="block font-semibold">내용</label>
            <textarea name="content" rows="6" required
                class="w-full border px-3 py-2 resize-none focus:outline-none focus:ring-2 focus:ring-black"></textarea>
        </div>

        <div class="mb-6">
            <label class="block font-semibold">작성일</label>
            <input type="text" value="<%= today %>" disabled class="w-full border px-3 py-2 bg-gray-100">
        </div>

        <div class="flex gap-4 justify-center">
            <button type="submit" class="bg-black text-white px-6 py-2 rounded hover:bg-gray-900">등록하기</button>
            <button type="button" onclick="location.href='inquiry_list.go';"
                class="border border-black text-black px-6 py-2 rounded hover:bg-gray-100">목록보기</button>
        </div>
    </form>
</body>
</html>