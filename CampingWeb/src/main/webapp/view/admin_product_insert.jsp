<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        <h1 class="absolute left-1/2 transform -translate-x-1/2 text-2xl font-bold text-white">PacknGo Manager</h1>
        <nav class="ml-auto space-x-6">
            <a href="../logout.go" class="text-sm text-white hover:underline">로그아웃</a>
        </nav>
    </header>

    <!-- 상품 추가 폼 -->
    <main class="max-w-md mx-auto mt-20 px-4">
        <h2 class="text-2xl font-semibold mb-8 text-center">PacknGo Manager</h2>

        <form action="product_add_process.jsp" method="POST" enctype="multipart/form-data">
            <div class="space-y-4">
                <!-- 상품 카테고리 -->
                <div>
                    <label for="cam_category" class="block text-sm font-medium text-gray-700">상품 카테고리</label>
                    <select id="cam_category" name="cam_category" class="block w-full px-4 py-2 border border-gray-300 rounded">
                        <option value="chair">체어</option>
                        <option value="table">테이블</option>
                        <option value="coat">코트</option>
                        <option value="tent">텐트</option>
                    </select>
                </div>

                <!-- 상품 이름 -->
                <div>
                    <label for="product_name" class="block text-sm font-medium text-gray-700">상품 이름</label>
                    <input type="text" id="product_name" name="product_name" class="block w-full px-4 py-2 border border-gray-300 rounded" required />
                </div>

                <!-- 입고액 -->
                <div>
                    <label for="input_price" class="block text-sm font-medium text-gray-700">입고액</label>
                    <input type="number" id="input_price" name="input_price" class="block w-full px-4 py-2 border border-gray-300 rounded" required />
                </div>

                <!-- 출고액 -->
                <div>
                    <label for="output_price" class="block text-sm font-medium text-gray-700">출고액</label>
                    <input type="number" id="output_price" name="output_price" class="block w-full px-4 py-2 border border-gray-300 rounded" required />
                </div>

                <!-- 재고수량 -->
                <div>
                    <label for="stock_qty" class="block text-sm font-medium text-gray-700">재고수량</label>
                    <input type="number" id="stock_quantity" name="stock_quantity" class="block w-full px-4 py-2 border border-gray-300 rounded" required />
                </div>

                <!-- 상품 이미지 업로드 -->
                <div>
                    <label for="product_image" class="block text-sm font-medium text-gray-700">상품 대표이미지</label>
                    <input type="file" id="product_image" name="product_image" class="block w-full px-4 py-2 border border-gray-300 rounded" required />
                </div>

                <!-- 상세페이지 이미지 업로드 (4개) -->
                <div>
                    <label for="detail_image1" class="block text-sm font-medium text-gray-700">상세페이지 이미지 1</label>
                    <input type="file" id="detail_image1" name="detail_image1" class="block w-full px-4 py-2 border border-gray-300 rounded" required />
                </div>
                <div>
                    <label for="detail_image2" class="block text-sm font-medium text-gray-700">상세페이지 이미지 2</label>
                    <input type="file" id="detail_image2" name="detail_image2" class="block w-full px-4 py-2 border border-gray-300 rounded" required />
                </div>
                <div>
                    <label for="detail_image3" class="block text-sm font-medium text-gray-700">상세페이지 이미지 3</label>
                    <input type="file" id="detail_image3" name="detail_image3" class="block w-full px-4 py-2 border border-gray-300 rounded" required />
                </div>
                <div>
                    <label for="detail_image4" class="block text-sm font-medium text-gray-700">상세페이지 이미지 4</label>
                    <input type="file" id="detail_image4" name="detail_image4" class="block w-full px-4 py-2 border border-gray-300 rounded" required />
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
