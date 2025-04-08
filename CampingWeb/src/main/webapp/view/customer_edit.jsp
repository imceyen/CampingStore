<%@page import="com.camping.model.CustomerDAO"%>
<%@page import="com.camping.model.CustomerDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
    String id = (String) session.getAttribute("cus_id");
    String name = (String) session.getAttribute("cus_name");

    CustomerDAO dao = com.camping.model.CustomerDAO.getInstance();
    CustomerDTO dto = dao.getCustomerInfo(id);  // 회원 정보 가져오기
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 정보 수정</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-white font-sans text-black">

    <!-- 로고 -->
    <header class="text-center py-6 border-b">
        <h1 class="text-3xl font-bold"><span class="text-black">PacknGo</span></h1>
    </header>

    <!-- 메인 -->
    <main class="max-w-xl mx-auto py-12 px-4">
        <h2 class="text-2xl font-bold text-center mb-12">회원 정보 수정</h2>

        <form action="customer_edit_ok.go" method="post" class="space-y-8">

            <!-- 이름 -->
            <div>
                <label class="block mb-2 font-semibold">이름</label>
                <input type="text" name="cus_name" value="<%= dto.getName() %>" readonly 
                    class="w-full border-b py-2 bg-gray-100 text-gray-600 cursor-not-allowed">
            </div>

            <!-- 아이디 -->
            <div>
                <label class="block mb-2 font-semibold">아이디</label>
                <input type="text" name="cus_id" value="<%= dto.getCustomer_id() %>" readonly 
                    class="w-full border-b py-2 bg-gray-100 text-gray-600 cursor-not-allowed">
            </div>

            <!-- 비밀번호 -->
            <div>
                <label class="block mb-2 font-semibold">비밀번호</label>
                <input type="password" value="********" readonly 
                    class="w-full border-b py-2 bg-gray-100 text-gray-600 cursor-not-allowed">
            </div>

            <!-- 생년월일 -->
            <div>
                <label class="block mb-2 font-semibold">생년월일</label>
                <input type="text" value="<%= dto.getBirth_Date().substring(0, 10) %>" readonly 
    			class="w-full border-b py-2 bg-gray-100 text-gray-600 cursor-not-allowed">
            </div>

            <!-- 성별 -->
            <div>
                <label class="block mb-2 font-semibold">성별</label>
                <input type="text" value="<%= dto.getGender() %>" readonly 
                    class="w-full border-b py-2 bg-gray-100 text-gray-600 cursor-not-allowed">
            </div>

            <!-- 연락처 -->
            <div>
                <label class="block mb-2 font-semibold">연락처</label>
                <input type="text" name="cus_phone" value="<%= dto.getPhone() %>" 
                    class="w-full border-b py-2 focus:outline-none" required>
            </div>

            <!-- 주소 -->
            <div>
                <label class="block mb-2 font-semibold">주소</label>
                <input type="text" name="cus_addr" value="<%= dto.getAddress() %>" 
                    class="w-full border-b py-2 focus:outline-none" required>
            </div>

            <!-- 버튼 -->
            <div class="pt-8 flex flex-col gap-2">
                <button type="submit" class="w-full bg-black text-white py-3 rounded hover:bg-gray-900">수정</button>
                <button type="button" onclick="history.back();" class="w-full border border-black text-black py-3 rounded hover:bg-gray-100">취소</button>
            </div>

        </form>
    </main>

</body>
</html>