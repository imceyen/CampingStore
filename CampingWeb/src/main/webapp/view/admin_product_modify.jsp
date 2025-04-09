<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>상품 수정 - PacknGo Manager</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-white text-black font-sans">

    <!-- 헤더 -->
    <header class="w-full border-b py-4 px-6 relative flex items-center bg-black">
        <div>
			<a href="javascript:history.back()" class="text-white text-sm hover:underline">이전으로</a>
		</div>
        <h1 class="absolute left-1/2 transform -translate-x-1/2 text-2xl font-bold text-white">PacknGo Manager</h1>
        <nav class="ml-auto space-x-6">
            <a href="../logout.go" class="text-sm text-white hover:underline">로그아웃</a>
        </nav>
    </header>

    <!-- 메인 콘텐츠 -->
    <main class="max-w-2xl mx-auto mt-16 px-4">
        <h2 class="text-3xl font-semibold mb-8 text-center">상품 수정</h2>

        <form method="post" enctype="multipart/form-data"
              action="<%=request.getContextPath() %>/admin_product_modify_ok.go"
              class="bg-white shadow-md rounded-xl p-8 space-y-6 border">

            <c:set var="dto" value="${ProductCont}" />
            <input type="hidden" name="product_no" value="${dto.product_no}" />

            <div>
                <label class="block font-medium mb-1">카테고리 번호</label>
                <input type="text" name="category_no" value="${dto.category_no}" readonly
                       class="w-full border rounded-md px-3 py-2 bg-gray-100" />
            </div>

            <div>
                <label class="block font-medium mb-1">상품명</label>
                <input type="text" name="product_name" value="${dto.product_name}"
                       class="w-full border rounded-md px-3 py-2" />
            </div>

            <div>
                <label class="block font-medium mb-1">입고가</label>
                <input type="number" name="input_price" value="${dto.input_price}" readonly
                       class="w-full border rounded-md px-3 py-2 bg-gray-100" />
            </div>

            <div>
                <label class="block font-medium mb-1">출고가</label>
                <input type="number" name="output_price" value="${dto.output_price}"
                       class="w-full border rounded-md px-3 py-2" />
            </div>

            <div>
                <label class="block font-medium mb-1">재고 수량</label>
                <input type="number" name="stock_qty" value="${dto.stock_qty}"
                       class="w-full border rounded-md px-3 py-2" />
            </div>

            <div>
                <label class="block font-medium mb-1">품절 여부</label>
                <input type="text" name="is_sold_out" value="${dto.is_sold_out}"
                       class="w-full border rounded-md px-3 py-2" />
            </div>

            <div>
                <label class="block font-medium mb-1">렌탈 금액</label>
                <input type="number" name="rental_unit_price" value="${dto.rental_unit_price}"
                       class="w-full border rounded-md px-3 py-2" />
            </div>

            <div>
                <label class="block font-medium mb-1">렌탈 가능 여부</label>
                <input type="text" name="is_rent_available" value="${dto.is_rent_available}"
                       class="w-full border rounded-md px-3 py-2" />
            </div>

            <div>
                <label class="block font-medium mb-1">상품 이미지</label>
                <img src="<%=request.getContextPath() %>/upload/${dto.product_image}" alt="상품 이미지"
                     class="w-32 h-24 object-cover rounded mb-2" />
                <input type="file" name="p_image_new" class="w-full border rounded-md px-3 py-2" />
                <input type="hidden" name="p_image_old" value="${dto.product_image}" />
            </div>

            <div class="flex justify-center space-x-4 pt-4">
                <input type="submit" value="상품 수정"
                       class="bg-black text-white px-6 py-2 rounded-md hover:bg-gray-800 cursor-pointer" />
                <input type="reset" value="다시 작성"
                       class="border border-gray-400 text-gray-700 px-6 py-2 rounded-md hover:bg-gray-100 cursor-pointer" />
            </div>

        </form>
    </main>

    <!-- 푸터 -->
    <footer class="border-t mt-20 p-4 text-center text-sm text-gray-600">
        &copy; 2025 PacknGo 관리자 페이지. All rights reserved.
    </footer>

</body>
</html>
