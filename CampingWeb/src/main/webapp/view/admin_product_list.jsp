<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>상품 목록 - PacknGo Manager</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script type="text/javascript">
        function del(no) {
            let result = confirm("정말로 제품을 삭제하시겠습니까?");
            if (result) {
                location.href = "admin_product_delete.do?product_no=" + no;
            }
        }
    </script>
</head>
<body class="bg-white text-black font-sans">

    <!-- 헤더 -->
    <header class="w-full border-b py-4 px-6 relative flex items-center bg-black">
        <h1 class="absolute left-1/2 transform -translate-x-1/2 text-2xl font-bold text-white">PacknGo Manager</h1>
        <nav class="ml-auto space-x-6">
            <a href="../logout.go" class="text-sm text-white hover:underline">로그아웃</a>
        </nav>
    </header>

    <!-- 메인 콘텐츠 -->
    <main class="max-w-7xl mx-auto mt-16 px-4">
        <h2 class="text-3xl font-semibold mb-10 text-center">전체 상품</h2>

        <c:set var="list" value="${productList}" />
        <c:if test="${!empty list}">
            <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
                <c:forEach items="${list}" var="dto">
                    <div class="border rounded-2xl p-4 shadow-sm hover:shadow-md transition">
                        <img src="<%=request.getContextPath() %>/upload/${dto.product_image}" alt="상품 이미지"
                             class="w-full h-48 object-cover rounded-xl mb-4" />
                        <h3 class="text-lg font-semibold mb-1">${dto.product_name}</h3>
                        <p class="text-sm text-gray-500 mb-2">카테고리 번호: ${dto.category_no}</p>
                        <p class="text-base font-medium mb-1">
                            <fmt:formatNumber value="${dto.output_price}" /> 원
                        </p>
                        <p class="text-sm text-gray-600 mb-4">판매재고: ${dto.stock_qty}개</p>
                        <div class="flex justify-between text-sm">
                            <a href="<%=request.getContextPath() %>/admin_product_modify.go?no=${dto.product_no}"
                               class="text-blue-600 hover:underline">수정</a>
                            <a href="javascript:del(${dto.product_no})"
                               class="text-red-600 hover:underline">삭제</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${empty list}">
            <div class="text-center text-gray-500 mt-20">
                <h3 class="text-lg">상품 전체 리스트가 없습니다.</h3>
            </div>
        </c:if>
    </main>

    <!-- 푸터 -->
    <footer class="border-t mt-20 p-4 text-center text-sm text-gray-600">
        &copy; 2025 PacknGo 관리자 페이지. All rights reserved.
    </footer>

</body>
</html>
