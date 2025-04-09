<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>PacknGo 상품 추가</title>
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

    <!-- 상품 등록 폼 -->
    <main class="max-w-md mx-auto mt-20 px-4">
        <h2 class="text-2xl font-semibold mb-8 text-center">상품 등록</h2>

        <form method="post" enctype="multipart/form-data" 
        action="<%=request.getContextPath() %>/admin_product_insert_ok.go">
            <div class="space-y-4">
            <c:set var="clist" value="${CategoryList}" />
                <!-- 상품 카테고리 -->
                <div>
                	
                    <label class="block text-sm font-medium text-gray-700">상품 카테고리</label>
                    <select name="category_no" class="block w-full px-4 py-2 border border-gray-300 rounded">
                        <c:if test="${empty clist }">
	                     <option value="">상품 카테고리</option>
	                  	</c:if>
                        <c:if test="${!empty clist }">
	                     <c:forEach items="${clist }" var="dto">
	                        <option value="${dto.getCategory_no() }">
	                             ${dto.getCategory_name() } [${dto.getCategory_no() }]</option>
	                     </c:forEach>
	                  </c:if>
                    </select>
                </div>

                <!-- 상품 이름 -->
                <div>
                    <label class="block text-sm font-medium text-gray-700">상품 이름</label>
                    <input type="text" name="product_name" class="block w-full px-4 py-2 border border-gray-300 rounded" required />
                </div>


                <!-- 출고액 -->
                <div>
                    <label class="block text-sm font-medium text-gray-700">상품 가격</label>
                    <input type="number" name="output_price" class="block w-full px-4 py-2 border border-gray-300 rounded" required />
                </div>

                <!-- 재고수량 -->
                <div>
                    <label class="block text-sm font-medium text-gray-700">재고 수량</label>
                    <input type="number" name="stock_qty" class="block w-full px-4 py-2 border border-gray-300 rounded" required />
                </div>

                <!-- 상품 이미지 업로드 -->
                <div>
                    <label class="block text-sm font-medium text-gray-700">상품 대표이미지</label>
                    <input type="file" name="product_image" class="block w-full px-4 py-2 border border-gray-300 rounded" required />
                </div>

                <!-- 상세페이지 이미지 업로드 (4개) -->
                <div>
                    <label class="block text-sm font-medium text-gray-700">상세페이지 이미지 1</label>
                    <input type="file" name="detail_image1" class="block w-full px-4 py-2 border border-gray-300 rounded" required />
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700">상세페이지 이미지 2</label>
                    <input type="file" name="detail_image2" class="block w-full px-4 py-2 border border-gray-300 rounded" required />
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700">상세페이지 이미지 3</label>
                    <input type="file" name="detail_image3" class="block w-full px-4 py-2 border border-gray-300 rounded" required />
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700">상세페이지 이미지 4</label>
                    <input type="file" name="detail_image4" class="block w-full px-4 py-2 border border-gray-300 rounded" required />
                </div>

                <!-- 제출 버튼 -->
                <div class="mt-6">
                    <button type="submit" class="w-full px-4 py-2 bg-black text-white font-semibold rounded hover:bg-gray-800 transition">상품 추가</button>
                </div>
            </div>
        </form>
    </main>

    <!-- 푸터 -->
    <footer class="border-t mt-20 p-4 text-center text-sm">
        &copy; 2025 PacknGo 관리자 페이지. All rights reserved.
    </footer>

</body>
</html>
