<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.camping.model.ReviewDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    ReviewDTO review = (ReviewDTO) request.getAttribute("review");
    String from = (String) request.getAttribute("from");
    Boolean isMine = (Boolean) request.getAttribute("isMine");
%>

<html>
<head>
    <title>리뷰 상세보기</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-white text-gray-800">
    <header class="text-center py-6 border-b border-gray-300">
        <h1 class="text-3xl font-bold text-black">PacknGo</h1>
    </header>

    <div class="max-w-2xl mx-auto mt-10 p-6 border rounded-2xl shadow-sm bg-white">
        <h2 class="text-lg font-semibold mb-4 text-center text-gray-700">리뷰 상세보기</h2>

        <div class="border p-4 rounded bg-gray-50 space-y-2 text-sm">
            <p><strong>후기 번호:</strong> <%= review.getReview_no() %></p>
            <p><strong>고객 번호:</strong> <%= review.getCustomer_no() %></p>
            <p><strong>상품 번호:</strong> <%= review.getProduct_no() %></p>
            <p><strong>작성일자:</strong> <%= review.getReview_date() %></p>
        </div>

        <div class="mt-6 border-t pt-4">
            <h3 class="text-base font-medium mb-2 text-gray-600">후기 내용</h3>
            <div class="whitespace-pre-line text-gray-900 text-sm leading-relaxed"><%= review.getContent().replaceAll("\n", "<br>") %></div>
        </div>

        <div class="mt-8 flex justify-between items-center">
            <!-- 목록 버튼 (좌측) -->
            <form action="<%= "my".equals(from) ? "review_list.go" : "review_all.go" %>" method="get">
                <button type="submit" class="px-4 py-2 bg-gray-300 text-gray-800 rounded hover:bg-gray-400">목록</button>
            </form>

            <!-- 수정/삭제 버튼 (우측, 본인일 때만) -->
            <c:if test="${isMine}">
                <div class="space-x-2">
                    <form action="review_edit.go" method="get" class="inline">
                        <input type="hidden" name="review_no" value="<%= review.getReview_no() %>">
                        <button type="submit" class="px-4 py-2 bg-black text-white rounded hover:bg-gray-800">수정</button>
                    </form>
                    <form action="review_delete_ok.go" method="post" class="inline">
    					<input type="hidden" name="review_no" value="<%= review.getReview_no() %>">
    					<button type="submit" onclick="return confirm('정말 삭제하시겠습니까?');"
            					class="px-4 py-2 bg-red-600 text-white rounded hover:bg-red-700">삭제</button>
					</form>
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>