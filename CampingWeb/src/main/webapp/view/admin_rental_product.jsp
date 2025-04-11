<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>PacknGo 대여 상품 재고</title>
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-white text-black font-sans">

	<!-- 헤더 -->
	<header class="w-full border-b py-4 px-6 relative flex items-center bg-black">
		<div>
			<a href="javascript:history.back()" class="text-white text-sm hover:underline">이전으로</a>
		</div>
		<h1 class="absolute left-1/2 transform -translate-x-1/2 text-2xl font-bold text-white">
			PacknGo Manager
		</h1>
		<nav class="ml-auto space-x-6">
			<a href="../logout.go" class="text-sm text-white hover:underline">로그아웃</a>
		</nav>
	</header>

	<!-- 메인 -->
	<main class="max-w-screen-xl mx-auto px-6 mt-10 mb-20">
		<h2 class="text-3xl font-semibold text-center mb-10">대여 상품 재고 현황</h2>

		<div class="bg-white border rounded-xl p-6 shadow">
			<table class="min-w-full border text-sm text-center">
				<thead class="bg-gray-100">
					<tr>
						<th class="border px-4 py-2">상품번호</th>
						<th class="border px-4 py-2">상품명</th>
						<th class="border px-4 py-2">총 수량</th>
						<th class="border px-4 py-2">대여 중</th>
						<th class="border px-4 py-2">남은 수량</th>
						<th class="border px-4 py-2">상태</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="dto" items="${rentalStockList}">
						<tr>
							<td class="border px-4 py-2">${dto.product_no}</td>
							<td class="border px-4 py-2">${dto.product_name}</td>
							<td class="border px-4 py-2">${dto.rental_stock}</td>
							<td class="border px-4 py-2">${dto.rented_qty}</td>
							<td class="border px-4 py-2">${dto.available_qty}</td>
							<td class="border px-4 py-2">
								<c:choose>
									<c:when test="${dto.available_qty > 0}">
										<span class="text-green-600 font-semibold">대여 가능</span>
									</c:when>
									<c:otherwise>
										<span class="text-red-600 font-semibold">대여 불가</span>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</main>

	<!-- 푸터 -->
	<footer class="border-t mt-20 p-4 text-center text-sm text-gray-600">
		&copy; 2025 PacknGo 관리자 페이지. All rights reserved.
	</footer>
</body>
</html>
