<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
    String cusName = (String) session.getAttribute("cus_name");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title >캠핑</title>
  <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-white text-black font-sans">

  
  <header class="w-full border-b py-4 px-6 relative flex items-center">
  <!-- 로고: 중앙에 위치 -->
  <h1 class="absolute left-1/2 transform -translate-x-1/2 text-2xl font-bold">
    PanknGO
  </h1>

  <!-- 메뉴: 오른쪽 정렬 -->
  <nav class="ml-auto space-x-6">
    <% if (cusName == null) { %>
    
    <!-- 로그인 안 했을 때 -->
    <a href="<%=request.getContextPath()%>/customer_login.go" class="text-sm hover:underline">로그인</a>
    <a href="<%=request.getContextPath()%>/camping_signUp.go" class="text-sm hover:underline">회원가입</a>
  <% } else { %>
    <!-- 로그인 했을 때 -->
    <span class="text-sm font-semibold"><%= cusName %> 님</span>
    <a href="<%=request.getContextPath()%>/customer_logout.go" class="text-sm hover:underline">로그아웃</a>
  <% } %>
  
    <a href="<%=request.getContextPath()%>/customer_mypage.go" class="text-sm hover:underline">마이페이지</a>
    <a href="#" class="text-sm hover:underline">장바구니</a>
  </nav>
</header>




  <section class="bg-black text-white text-center py-20">
    <h2 class="text-4xl font-semibold mb-4">자연 속에서의 완벽한 하루</h2>
    <p class="text-lg">프리미엄 캠핑용품, 지금 구매 또는 대여하세요.</p>
  </section>

<br>

  <!-- 카테고리 메뉴 (가로 정렬) -->
  <section class="max-w-7xl mx-auto px-6 mt-6" >
    <div class="flex justify-center space-x-10 border-b-2  pb-10">
      <button class="hover:underline text-lg font-semibold">텐트</button>
      <button class="hover:underline text-lg font-semibold">코트</button>
      <button class="hover:underline text-lg font-semibold">체어</button>
      <button class="hover:underline text-lg font-semibold">테이블</button>
    </div>
  </section>

	

 <br>
 
  <!-- 상품 리스트 전체적용 설정 -->
   <br>
 <main class="max-w-7xl mx-auto px-6 py-10">
     <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
  
     
     
      <!-- 상품 테이블 3개 리스트 -->
	      <div class="border p-4 rounded hover:shadow transition ">
	        <img src="./image/0350010000052.jpg" alt="테이블1" class="mb-4">
	        <h1 class="text-lg font-semibold mb-2">프리미엄 테이블1</h1>
	        <p >편안한 캠핑을 위한 테이블</p> 
		        <div class="flex justify-between items-center mt-3">
				    <h1 >100,000원</h1>
				    <input  type="button" value="자세히 보기" onclick="location.href='table1.jsp'" 
				    class="border border-black text-sm text-black px-3 py-1 rounded
				    	 hover:bg-black hover:text-white transition">
				    
				    
				   <!--  <button class="border border-black text-sm text-black px-3 py-1 rounded
				    	 hover:bg-black hover:text-white transition">  자세히 보기  </button> -->
				</div>
		  </div>

      
     
      
	      <div class="border p-4 rounded hover:shadow transition  ">
	        <img src="./image/table2.jpg" alt="테이블2" class="mb-4">
	        <h1 class="text-lg font-semibold mb-2">프리미엄 테이블2</h1>
	        <p>튼튼한 테이블</p>
		        <div class="flex justify-between items-center mt-3">
				    <h1 >100,000원</h1>
				    <button class="border border-black text-sm text-black px-3 py-1 rounded
				    	 hover:bg-black hover:text-white transition">  자세히 보기  </button>
				</div>
		  </div>


      	 
	      <div class="border p-4 rounded hover:shadow transition">
			  <img src="./image/table3.jpg" alt="테이블3" class="mb-4">
			  <h1 class="text-lg font-semibold mb-2">프리미엄 테이블3</h1>
			  <p>가벼운 테이블</p>
				  <div class="flex justify-between items-center mt-3">
				    <h1 >100,000원</h1>
				    <button class="border border-black text-sm text-black px-3 py-1 rounded
				    	 hover:bg-black hover:text-white transition">  자세히 보기  </button>
		  
				  </div>
		  </div>
	
	      
      
      		<!-- 상품 의자 3개 리스트 -->
	      <div class="border p-4 rounded hover:shadow transition ">
	        <img src="./image/chair1.jpg" alt="의자1" width="800px" hight="800px" class="mb-4">
	        <h1 class="text-lg font-semibold mb-2" >프리미엄 의자1</h1>
	        <p >편안한 캠핑을 위한 의자</p> 
		        <div class="flex justify-between items-center mt-3">
				    <h1 >150,000원</h1>
				    <button class="border border-black text-sm text-black px-3 py-1 rounded
				    	 hover:bg-black hover:text-white transition">  자세히 보기  </button>
				</div>
		  </div>

      
     
      
	      <div class="border p-4 rounded hover:shadow transition  ">
	        <img src="./image/chair2.jpg" alt="의자2" class="mb-4">
	        <h1 class="text-lg font-semibold mb-2">프리미엄 의자2</h1>
	        <p>튼튼한 의자</p>
		        <div class="flex justify-between items-center mt-3">
				    <h1 >170,000원</h1>
				    <button class="border border-black text-sm text-black px-3 py-1 rounded
				    	 hover:bg-black hover:text-white transition">  자세히 보기  </button>
				</div>
		  </div>


      	 
	      <div class="border p-4 rounded hover:shadow transition">
			  <img src="./image/chair3.jpg" alt="의자3" class="mb-4" >
			  <h1 class="text-lg font-semibold mb-2">프리미엄 의자3</h1>
			  <p>가벼운 의자</p>
				  <div class="flex justify-between items-center mt-3">
				    <h1 >130,000원</h1>
				    <button class="border border-black text-sm text-black px-3 py-1 rounded
				    	 hover:bg-black hover:text-white transition">  자세히 보기  </button>
		  
				  </div>
		  </div>


      	<!-- 상품 코트 3개 리스트 -->
	      <div class="border p-4 rounded hover:shadow transition ">
	        <img src="./image/cot1.jpg" alt="코트1" class="mb-4">
	        <h1 class="text-lg font-semibold mb-2">프리미엄 코트1</h1>
	        <p >편안한 캠핑을 위한 테이블</p> 
		        <div class="flex justify-between items-center mt-3">
				    <h1 >100,000원</h1>
				    <button class="border border-black text-sm text-black px-3 py-1 rounded
				    	 hover:bg-black hover:text-white transition">  자세히 보기  </button>
				</div>
		  </div>

      
     
      
	      <div class="border p-4 rounded hover:shadow transition  ">
	        <img src="./image/cot2.jpg" alt="코트2" class="mb-4">
	        <h1 class="text-lg font-semibold mb-2">프리미엄 코트2</h1>
	        <p>튼튼한 테이블</p>
		        <div class="flex justify-between items-center mt-3">
				    <h1 >100,000원</h1>
				    <button class="border border-black text-sm text-black px-3 py-1 rounded
				    	 hover:bg-black hover:text-white transition">  자세히 보기  </button>
				</div>
		  </div>


      	 
	      <div class="border p-4 rounded hover:shadow transition">
			  <img src="./image/cot3.jpg" alt="코트3" class="mb-4">
			  <h1 class="text-lg font-semibold mb-2">프리미엄 코트3</h1>
			  <p>가벼운 테이블</p>
				  <div class="flex justify-between items-center mt-3">
				    <h1 >100,000원</h1>
				    <button class="border border-black text-sm text-black px-3 py-1 rounded
				    	 hover:bg-black hover:text-white transition">  자세히 보기  </button>
		  
				  </div>
		  </div>
		  
		  
		  <!-- 상품 텐트 3개 리스트 -->
	      <div class="border p-4 rounded hover:shadow transition ">
	        <img src="./image/tent1.jpg" alt="텐트1" class="mb-4">
	        <h1 class="text-lg font-semibold mb-2">프리미엄 텐트1</h1>
	        <p >편안한 캠핑을 위한 텐트</p> 
		        <div class="flex justify-between items-center mt-3">
				    <h1 >100,000원</h1>
				    <button class="border border-black text-sm text-black px-3 py-1 rounded
				    	 hover:bg-black hover:text-white transition">  자세히 보기  </button>
				</div>
		  </div>

      
     
      
	      <div class="border p-4 rounded hover:shadow transition  ">
	        <img src="./image/tent2.jpg" alt="텐트2" class="mb-4">
	        <h1 class="text-lg font-semibold mb-2">프리미엄 텐트2</h1>
	        <p>튼튼한 텐트</p>
		        <div class="flex justify-between items-center mt-3">
				    <h1 >100,000원</h1>
				    <button class="border border-black text-sm text-black px-3 py-1 rounded
				    	 hover:bg-black hover:text-white transition">  자세히 보기  </button>
				</div>
		  </div>


      	 
	      <div class="border p-4 rounded hover:shadow transition">
			  <img src="./image/tent3.jpg" alt="텐트3" class="mb-4">
			  <h1 class="text-lg font-semibold mb-2">프리미엄 텐트3</h1>
			  <p>가벼운 텐트</p>
				  <div class="flex justify-between items-center mt-3">
				    <h1 >100,000원</h1>
				    <button class="border border-black text-sm text-black px-3 py-1 rounded
				    	 hover:bg-black hover:text-white transition">  자세히 보기  </button>
		  
				  </div>
		  </div>


      

    </div>
  </main>

  <!-- 푸터 -->
  <footer class="border-t p-4 text-center text-sm">
    &copy; 2025 캠핑스토어. 모든 권리 보유.
  </footer>

</body>
</html>