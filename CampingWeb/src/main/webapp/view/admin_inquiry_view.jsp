<%@page import="java.sql.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="com.camping.model.InquiryDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    InquiryDTO dto = (InquiryDTO) request.getAttribute("dto");
%>

<%
    // 답변일자 포맷 설정
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // 답변 내용과 답변 일자 가져오기
    String answerContent = dto.getAnswer_content();
    Date answerDate = dto.getAnswer_date();
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>1:1 문의 상세보기 (관리자)</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-white text-gray-800">

    <div class="max-w-2xl mx-auto px-6 py-12">
        <h1 class="text-2xl font-semibold text-center mb-6">PacknGo 관리자</h1>
        <hr class="mb-6">
        
        <h2 class="text-xl font-bold mb-4 text-center">1:1 문의 상세보기</h2>

        <div class="bg-gray-50 p-6 rounded-2xl shadow-sm">
            <!-- 문의 번호 -->
            <div class="mb-4">
                <label class="font-medium text-gray-600">문의 번호</label>
                <p class="mt-1 text-gray-900"><%= dto.getInquiry_no() %></p>
            </div>

            <!-- 고객 번호 -->
            <div class="mb-4">
                <label class="font-medium text-gray-600">고객 번호</label>
                <p class="mt-1 text-gray-900"><%= dto.getCustomer_no() %></p>
            </div>

            <!-- 상품 번호 -->
            <div class="mb-4">
                <label class="font-medium text-gray-600">상품 번호</label>
                <p class="mt-1 text-gray-900"><%= dto.getProduct_no() %></p>
            </div>

            <!-- 문의 내용 -->
            <div class="mb-4">
                <label class="font-medium text-gray-600">문의 내용</label>
                <p class="mt-1 text-gray-900"><%= dto.getContent() %></p>
            </div>

            <!-- 문의 일자 -->
            <div class="mb-4">
                <label class="font-medium text-gray-600">문의 일자</label>
                <p class="mt-1 text-gray-900"><%= dto.getInquiry_date() %></p>
            </div>

            <!-- 답변 내용 -->
            <div class="mb-4">
                <label class="font-medium text-gray-600">답변 내용</label>
                <%
                    if (answerContent != null && !answerContent.trim().isEmpty()) {
                %>
                    <p class="mt-1 text-gray-900"><%= answerContent %></p>
                <% } else { %>
                    <p class="mt-1 text-gray-500">답변이 없습니다.</p>
                <% } %>
            </div>

            <!-- 답변 일자 -->
            <div class="mb-4">
                <label class="font-medium text-gray-600">답변 일자</label>
                <%
                    if (answerDate != null) {
                %>
                    <p class="mt-1 text-sm text-gray-500"><%= dateFormat.format(answerDate) %></p>
                <% } else { %>
                    <p class="mt-1 text-gray-500">답변 일자가 없습니다.</p>
                <% } %>
            </div>


            <!-- 답변하기 폼 -->
            <% if (dto.getAnswer_content() == null) { %>
            <div class="mb-4">
                <label class="font-medium text-gray-600">답변 작성</label>
                <form action="admin_inquiry_answer.go" method="post">
                    <textarea name="answer_content" rows="5" class="w-full p-2 border rounded" placeholder="답변을 작성하세요"></textarea>
                    <input type="hidden" name="inquiry_no" value="<%= dto.getInquiry_no() %>" />
                    <button type="submit" class="mt-2 bg-gray-600 text-white px-4 py-2 rounded">답변 제출</button>
                </form>
            </div>
            <% } else { %>
            
            <!-- 답변이 있는 경우 수정/삭제 버튼 -->
            <div class="mb-4">
                <form action="admin_inquiry_answer_edit_form.go" method="get" style="display: inline;">
                    <input type="hidden" name="inquiry_no" value="<%= dto.getInquiry_no() %>" />
                    <button type="submit" class="mt-2 bg-gray-500 text-white px-4 py-2 rounded">답변 수정</button>
                </form>

                <form action="admin_inquiry_answer_delete.go" method="post" style="display: inline;">
                    <input type="hidden" name="inquiry_no" value="<%= dto.getInquiry_no() %>" />
                    <button type="submit" class="mt-2 bg-gray-700 text-white px-4 py-2 rounded">답변 삭제</button>
                </form>
            </div>
            <% } %>
        </div>

        <!-- 뒤로가기 버튼 -->
        <div class="mt-6 text-center">
            <a href="admin_inquiry_list.go" class="text-gray-600 hover:underline">목록으로 돌아가기</a>
        </div>
    </div>

</body>
</html>
