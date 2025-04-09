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
        <div>
			<a href="javascript:history.back()" class="text-white text-sm hover:underline">이전으로</a>
		</div>
        <h1 class="absolute left-1/2 transform -translate-x-1/2 text-2xl font-bold text-white">PacknGo Manager</h1>
        <nav class="ml-auto space-x-6">
            <a href="../logout.go" class="text-sm text-white hover:underline">로그아웃</a>
        </nav>
    </header>

    <!-- 관리자 메뉴 섹션 -->
    <main class="max-w-md mx-auto mt-20 px-4">
        <h2 class="text-2xl font-semibold mb-8 text-center">관리자 메뉴</h2>
        <div class="flex flex-col space-y-4">
            <a href="<%=request.getContextPath() %>/admin_product_list.go" class="block w-full text-center border border-black px-4 py-2 rounded hover:bg-black hover:text-white transition">
                상품 관리
            </a>
            <a href="<%=request.getContextPath() %>/admin_product_input.do" class="block w-full text-center border border-black px-4 py-2 rounded hover:bg-black hover:text-white transition">
                대여 반납 관리
            </a>
            <a href="<%=request.getContextPath() %>/admin_sales_report.go" class="block w-full text-center border border-black px-4 py-2 rounded hover:bg-black hover:text-white transition">
                매출 통계
            </a>
        </div>
    </main>

    <!-- 푸터 -->
    <footer class="border-t mt-20 p-4 text-center text-sm">
        &copy; 2025 PacknGo 관리자 페이지. All rights reserved.
    </footer>

</body>
</html>