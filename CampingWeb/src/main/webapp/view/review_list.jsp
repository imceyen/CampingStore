<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.camping.model.ReviewDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>후기 목록</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>

<body class="bg-white text-gray-800">
    <header class="text-center py-6 border-b">
        <h1 class="text-3xl font-bold"><span class="text-black">PacknGo</span></h1>
    </header>

    <div class="max-w-4xl mx-auto mt-10 p-6 border rounded-2xl shadow-lg">
        <h1 class="text-2xl font-bold mb-6 text-center">구매 후기</h1>

        <!-- 내가 작성한 후기 -->
        <h2 class="text-xl font-semibold mb-4">내가 작성한 후기</h2>

        <c:choose>
            <c:when test="${empty myReviews}">
                <p class="text-gray-500">작성한 후기가 없습니다.</p>
            </c:when>
            <c:otherwise>
                <c:forEach var="review" items="${myReviews}">
                    <a href="review_detail.go?review_no=${review.review_no}&from=mypage" class="block border p-4 mb-2 rounded bg-gray-50 hover:bg-gray-100 transition">
                        <div class="text-sm text-gray-600">후기 번호: ${review.review_no}</div>
                        <div class="text-base font-medium mt-2">${review.firstLine}</div>
                        <div class="text-sm text-gray-400 mt-1">${review.review_date}</div>
                    </a>
                </c:forEach>
            </c:otherwise>
        </c:choose>


        <!-- 전체 보기 버튼: 내가 작성한 후기가 없어도 항상 표시 -->
		<form action="review_all.go" method="get" class="mt-6 text-right">
    		<button type="submit" class="bg-black text-white px-4 py-2 rounded hover:bg-gray-800">
        		전체 후기 보기
    		</button>
		</form>
    </div>
</body>
</html>