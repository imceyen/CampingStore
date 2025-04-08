<%@page import="com.camping.model.CustomerDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ page import="javax.servlet.http.*, javax.servlet.*" %>
<%
    // 로그인 여부 확인 후 진행
    String name = (String) session.getAttribute("cus_name");
    String id = (String) session.getAttribute("cus_id");
    if (name == null) {
        response.sendRedirect("customer_login.go");
        return;
    }

    // DAO로 문의 수 가져오기
    /*
    int inquiryCount = 0;
    try {
        com.camping.model.CustomerDAO dao = new CustomerDAO();
        inquiryCount = dao.getInquiryCount(id); 
    } catch (Exception e) {
        e.printStackTrace();
    }
    */
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-white font-sans text-black">

    <!-- 로고 -->
    <header class="text-center py-6 border-b">
        <h1 class="text-3xl font-bold"><span class="text-black">PacknGo</span></h1>
    </header>

    <main class="max-w-4xl mx-auto mt-12">

        <h2 class="text-2xl font-bold text-center mb-10">마이페이지</h2>

        <!-- 이름 + 1:1문의 수 한 줄로 정렬 -->
		<div class="bg-gray-100 text-center py-10 rounded-lg mb-12">
  		<div class="flex justify-center items-center gap-12 text-sm">
    
    		<!-- 이름 -->
    		<div class="text-xl font-semibold"><%= name %> 님</div>
    
    		<!-- 
    		1:1 문의 
    		<div class="text-center">
      			<p class="font-semibold text-lg"></p>
      			<p>1:1문의</p>
    		</div>
    		-->

  		</div>
		</div>

        <!-- 정보 섹션 -->
        <div class="grid grid-cols-3 gap-10 text-center text-sm border-t border-gray-300 pt-6">
            <!-- 쇼핑 정보 -->
            <div>
                <p class="font-bold mb-3">나의 쇼핑 정보</p>
 				<p class="mb-1"><a href="#">주문 배송 조회</a></p>
    			<p><a href="#">구매 후기</a></p>
            </div>

            <!-- 고객센터 -->
			<div>
    			<p class="font-bold mb-3">고객센터</p>
    			<p><a href="#">1:1 문의 내역</a></p>
			</div>

            <!-- 회원 정보 -->
            <div>
                <p class="font-bold mb-3">회원 정보</p>
                <p class="mb-1"><a href="customer_check_pwd.go">회원 정보 변경</a></p>
                <p class="mb-1"><a href="customer_check_pwd.go?target=delete">회원 탈퇴</a></p>
                <p><a href="customer_logout.go">로그아웃</a></p>
            </div>
        </div>

    </main>

</body>
</html>