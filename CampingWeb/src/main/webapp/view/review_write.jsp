<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    // 로그인 여부 확인
    String id = (String)session.getAttribute("cus_id");

    if (id == null) {
        response.sendRedirect("customer_login.go");
        return;
    }

    // 후기 작성에 필요한 정보
    String name = (String)session.getAttribute("cus_name");
    Integer customerNo = (Integer)session.getAttribute("cus_no");

    // 오늘 날짜 (단순 표시용, DB에는 now()나 sysdate로 삽입)
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String today = sdf.format(new Date());

    // URL에서 전달된 상품 번호
    String productNoParam = request.getParameter("product_no");
    int productNo = 0;
    if (productNoParam != null && !productNoParam.equals("")) {
        productNo = Integer.parseInt(productNoParam);
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>구매 후기 작성</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-white min-h-screen flex flex-col items-center py-10">
    <header class="w-full border-b py-4 flex justify-center">
        <div class="text-xl font-bold">PacknGo</div>
    </header>

    <h2 class="text-2xl font-bold mt-10 mb-4">구매 후기 작성</h2>

    <form action="review_write_ok.go" method="post" class="w-full max-w-2xl px-6">
        <input type="hidden" name="customer_no" value="<%= customerNo %>">
        <input type="hidden" name="product_no" value="<%= productNo %>">

        <div class="mb-4">
            <label class="block font-semibold">작성자</label>
            <input type="text" value="<%= name %>" disabled class="w-full border px-3 py-2 bg-gray-100">
        </div>

        <div class="mb-4">
            <label class="block font-semibold">후기 내용</label>
            <textarea name="content" rows="6" required
                class="w-full border px-3 py-2 resize-none focus:outline-none focus:ring-2 focus:ring-black"
                placeholder="상품을 사용해보신 후기를 남겨주세요."></textarea>
        </div>

        <div class="mb-6">
            <label class="block font-semibold">작성일</label>
            <input type="text" value="<%= today %>" disabled class="w-full border px-3 py-2 bg-gray-100">
        </div>

        <div class="flex gap-4 justify-center">
            <button type="submit" class="bg-black text-white px-6 py-2 rounded hover:bg-gray-900">후기 등록</button>
            <button type="button" onclick="location.href='review_list.go';"
                class="border border-black text-black px-6 py-2 rounded hover:bg-gray-100">목록보기</button>
        </div>
    </form>
</body>
</html>