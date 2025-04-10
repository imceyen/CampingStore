<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.camping.model.InquiryDTO" %>
<%
    InquiryDTO dto = (InquiryDTO) request.getAttribute("dto");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>문의 수정</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-white text-gray-800 font-sans">
    <header class="text-center py-6 border-b">
        <h1 class="text-3xl font-bold"><span class="text-black">PacknGo</span></h1>
    </header>

    <main class="max-w-2xl mx-auto mt-10 p-6 border rounded-xl shadow-md">
        <h2 class="text-2xl font-semibold mb-6 text-center">문의 내용 수정</h2>

        <form action="inquiry_edit_ok.go" method="post" class="space-y-6">
            <input type="hidden" name="inquiry_no" value="<%= dto.getInquiry_no() %>">

            <div>
                <label for="content" class="block text-gray-700 mb-2">내용</label>
                <textarea name="content" id="content" rows="10"
                    class="w-full p-4 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-black"><%= dto.getContent() %></textarea>
            </div>

            <div class="text-center">
                <input type="submit" value="수정 완료"
                    class="bg-black text-white px-6 py-2 rounded-full hover:bg-gray-800 transition duration-200">
            </div>
        </form>
    </main>
</body>
</html>
