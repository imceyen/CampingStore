<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>PacknGo Manager</title>
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

    <!-- 관리자 메뉴 섹션 -->
    <main class="max-w-md mx-auto mt-20 px-4">
        <h2 class="text-2xl font-semibold mb-8 text-center">상품 관리</h2>

        
        <!-- 상품 관리 세부 메뉴 -->
        <div class="grid grid-cols-2 gap-4 mt-8">
        	<a href="<%=request.getContextPath() %>/admin_product_list.go" class="block text-center border border-black px-4 py-2 rounded hover:bg-black hover:text-white transition">
                상품 목록
            </a>
            <a href="<%=request.getContextPath() %>/admin_product_insert.go" class="block text-center border border-black px-4 py-2 rounded hover:bg-black hover:text-white transition">
                상품 추가
            </a>
            <a href="admin_product_search.go" class="block text-center border border-black px-4 py-2 rounded hover:bg-black hover:text-white transition">
                상품 검색
            </a>
            <a href="admin_product_update.go" class="block text-center border border-black px-4 py-2 rounded hover:bg-black hover:text-white transition">
                상품 수정
            </a>
            <a href="admin_product_delete.go" class="block text-center border border-black px-4 py-2 rounded hover:bg-black hover:text-white transition">
                상품 삭제
            </a>
        </div>
    </main>

    <!-- 푸터 -->
    <footer class="border-t mt-20 p-4 text-center text-sm">
        &copy; 2025 PacknGo 관리자 페이지. All rights reserved.
    </footer>

</body>
</html>
