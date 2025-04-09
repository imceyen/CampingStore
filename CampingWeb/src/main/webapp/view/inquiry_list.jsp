<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.camping.model.InquiryDTO" %>
<%
    List<InquiryDTO> list = (List<InquiryDTO>) request.getAttribute("inquiryList");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>1:1 문의 내역</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-white text-black">

    <header class="text-center py-6 border-b">
        <h1 class="text-3xl font-bold"><span class="text-black">PacknGo</span></h1>
    </header>

    <main class="max-w-3xl mx-auto px-4 py-10">
        <h2 class="text-2xl font-semibold mb-6 border-b border-gray-300 pb-2">1:1 문의 내역</h2>

        <table class="w-full border border-gray-300 text-sm">
            <thead>
                <tr class="bg-gray-100 border-b border-gray-300 text-gray-700">
                    <th class="py-3 px-4 text-left">문의번호</th>
                    <th class="py-3 px-4 text-left">상품번호</th>
                    <th class="py-3 px-4 text-left">내용</th>
                    <th class="py-3 px-4 text-left">작성일자</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (int i = 0; i < list.size(); i++) {
                        InquiryDTO dto = list.get(i);
                %>
                <tr class="border-b border-gray-200 hover:bg-gray-50">
                    <td class="py-3 px-4"><%= dto.getInquiry_no() %></td>
                    <td class="py-3 px-4"><%= dto.getProduct_no() %></td>
                    <td class="py-3 px-4">
                        <a href="inquiry_detail.go?inquiry_no=<%= dto.getInquiry_no() %>" class="text-blue-600 hover:underline">
                            <%= dto.getContent() %>
                        </a>
                    </td>
                    <td class="py-3 px-4"><%= dto.getInquiry_date() %></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <div class="mt-8 text-right">
            <a href="inquiry_write.go" class="inline-block px-6 py-2 border border-black text-black rounded hover:bg-black hover:text-white transition">
                문의하기
            </a>
        </div>
    </main>
</body>
</html>
