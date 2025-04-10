<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.camping.model.ReviewDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>전체 리뷰 목록</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-white text-gray-800">
    <header class="text-center py-6 border-b">
        <h1 class="text-3xl font-bold"><span class="text-black">PacknGo</span></h1>
    </header>

    <div class="max-w-4xl mx-auto mt-10 p-6 border rounded-2xl shadow-lg">
        <h1 class="text-2xl font-bold mb-6 text-center">전체 리뷰</h1>

        <c:choose>
            <c:when test="${not empty allReviews}">
                <div class="mb-8">
                    <h2 class="text-xl font-semibold border-b pb-2 mb-4">전체 리뷰</h2>
                    <ul class="space-y-4">
                        <c:forEach var="review" items="${allReviews}">
                            <li>
                                <a href="review_detail.go?review_no=${review.review_no}&from=all" class="block border p-4 rounded-lg bg-white hover:bg-gray-100 transition">
                                    <div class="text-sm text-gray-600">작성자 번호: ${review.customer_no}</div>
                                    <div class="text-base font-medium mt-2">${review.firstLine}</div>
                                    <div class="text-sm text-gray-400 mt-1">${review.review_date}</div>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </c:when>
            <c:otherwise>
                <p class="text-gray-500">등록된 리뷰가 없습니다.</p>
            </c:otherwise>
        </c:choose>

        <!-- 내가 쓴 리뷰로 돌아가기 버튼 -->
        <div class="mt-6 text-right">
            <form action="review_list.go" method="get">
                <button type="submit" class="bg-black text-white px-4 py-2 rounded hover:bg-gray-800">
                    내가 쓴 리뷰 보기
                </button>
            </form>
        </div>
    </div>
</body>
</html>
