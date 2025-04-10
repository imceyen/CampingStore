<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.camping.model.ReviewDTO" %>
<%
    ReviewDTO review = (ReviewDTO) request.getAttribute("review");
%>

<html>
<head>
    <title>후기 수정</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-white text-gray-900">

	<!-- 로고 -->
    <header class="text-center py-6 border-b">
        <h1 class="text-3xl font-bold"><span class="text-black">PacknGo</span></h1>
    </header>
    
    <!-- 수정 메인 -->
    <div class="max-w-xl mx-auto mt-10 p-6 border rounded-2xl shadow bg-white">
        <h2 class="text-xl font-semibold mb-4 text-center">후기 수정</h2>
        <form action="review_edit_ok.go" method="post">
            <input type="hidden" name="review_no" value="<%= review.getReview_no() %>">
            
            <label class="block mb-2 text-sm font-medium">후기 내용</label>
            <textarea name="content" rows="8" class="w-full border rounded p-2" required><%= review.getContent() %></textarea>
            
            <div class="mt-6 text-center">
                <button type="submit" class="px-4 py-2 bg-black text-white rounded hover:bg-gray-800">수정 완료</button>
            </div>
        </form>
    </div>
</body>
</html>