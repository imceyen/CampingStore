<%@page import="com.camping.model.InquiryDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    InquiryDTO dto = (InquiryDTO)request.getAttribute("dto");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>1:1 문의 상세보기</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-white text-gray-800">

    <div class="max-w-2xl mx-auto px-6 py-12">
        <h1 class="text-2xl font-semibold text-center mb-6">PacknGo</h1>
        <hr class="mb-6">
        
        <h2 class="text-xl font-bold mb-4 text-center">1:1 문의 상세보기</h2>

        <div class="bg-gray-50 p-6 rounded-2xl shadow-sm">
            <div class="mb-4">
                <label class="font-medium text-gray-600">문의 번호</label>
                <p class="mt-1 text-gray-900"><%= dto.getInquiry_no() %></p>
            </div>

            <div class="mb-4">
                <label class="font-medium text-gray-600">상품 번호</label>
                <p class="mt-1 text-gray-900"><%= dto.getProduct_no() %></p>
            </div>

            <div class="mb-4">
                <label class="font-medium text-gray-600">문의 내용</label>
                <div class="mt-1 p-3 border rounded-md bg-white text-gray-800"><%= dto.getContent() %></div>
            </div>

            <div>
                <label class="font-medium text-gray-600">작성일자</label>
                <p class="mt-1 text-gray-900"><%= dto.getInquiry_date() %></p>
            </div>
        </div>

        <div class="mt-8 flex justify-between">
            <form action="inquiry_list.go" method="post">
                <button type="submit" class="px-4 py-2 rounded-md bg-gray-200 hover:bg-gray-300 text-sm font-medium">목록</button>
            </form>

            <div class="flex space-x-2">
                <form action="inquiry_edit.go?inquiry_no=${dto.inquiry_no}" method="post">
                    <input type="hidden" name="no" value="<%= dto.getInquiry_no() %>">
                    <button type="submit" class="px-4 py-2 rounded-md bg-gray-100 hover:bg-gray-200 text-sm font-medium border border-gray-300">수정</button>
                </form>

                <form action="inquiry_delete.go" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                    <input type="hidden" name="no" value="<%= dto.getInquiry_no() %>">
                    <button type="submit" class="px-4 py-2 rounded-md bg-gray-100 hover:bg-gray-200 text-sm font-medium border border-gray-300">삭제</button>
                </form>
            </div>
        </div>
    </div>

</body>
</html>