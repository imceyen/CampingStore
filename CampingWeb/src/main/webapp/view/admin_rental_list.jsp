<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>PacknGo 대여 현황</title>
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

  <!-- 메인 -->
  <main class="max-w-screen-xl mx-auto px-6 mt-10 mb-20">
    <h2 class="text-3xl font-semibold text-center mb-10">대여 회원 목록</h2>

    <!-- ▶ 대여 상품 재고 현황 버튼 -->
    <div class="flex justify-end mb-4">
      <a href="admin_rental_product.go"
         class="bg-black text-white px-4 py-2 rounded hover:bg-gray-800 transition">
         대여 상품 재고 현황
      </a>
    </div>

    <div class="overflow-x-auto bg-white border rounded-xl shadow p-6">
      <table class="min-w-full text-sm text-center border border-gray-200">
        <thead class="bg-gray-100">
          <tr>
            <th class="border px-4 py-2">대여번호</th>
            <th class="border px-4 py-2">회원번호</th>
            <th class="border px-4 py-2">상품번호</th>
            <th class="border px-4 py-2">대여수량</th>
            <th class="border px-4 py-2">대여일</th>
            <th class="border px-4 py-2">반납예정일</th>
            <th class="border px-4 py-2">남은일수</th>
            <th class="border px-4 py-2">상태</th>
          </tr>
        </thead>
        <tbody>
          <c:choose>
            <c:when test="${not empty rentalList}">

              <!-- 대여중 먼저 표시 (남은일수 2일 이하이면 빨간색) -->
              <c:forEach var="rental" items="${rentalList}">
                <c:if test="${rental.rental_status ne '반납완료'}">
                  <tr class="${rental.remaining_days <= 2 ? 'bg-red-100 text-red-700 font-bold' : ''}">
                    <td class="border px-4 py-2">${rental.rental_no}</td>
                    <td class="border px-4 py-2">${rental.customer_no}</td>
                    <td class="border px-4 py-2">${rental.product_no}</td>
                    <td class="border px-4 py-2">${rental.rental_qty}</td>
                    <td class="border px-4 py-2">${fn:substring(rental.rental_date, 0, 10)}</td>
                    <td class="border px-4 py-2">${fn:substring(rental.return_date, 0, 10)}</td>
                    <td class="border px-4 py-2">
                      <c:choose>
                        <c:when test="${rental.remaining_days lt 0}">0일</c:when>
                        <c:otherwise>${rental.remaining_days}일</c:otherwise>
                      </c:choose>
                    </td>
                    <td class="border px-4 py-2">${rental.rental_status}</td>
                  </tr>
                </c:if>
              </c:forEach>

              <!-- 반납완료는 아래쪽에 초록색으로 출력 -->
              <c:forEach var="rental" items="${rentalList}">
                <c:if test="${rental.rental_status eq '반납완료'}">
                  <tr class="bg-green-100 text-green-700 font-semibold">
                    <td class="border px-4 py-2">${rental.rental_no}</td>
                    <td class="border px-4 py-2">${rental.customer_no}</td>
                    <td class="border px-4 py-2">${rental.product_no}</td>
                    <td class="border px-4 py-2">${rental.rental_qty}</td>
                    <td class="border px-4 py-2">${fn:substring(rental.rental_date, 0, 10)}</td>
                    <td class="border px-4 py-2">${fn:substring(rental.return_date, 0, 10)}</td>
                    <td class="border px-4 py-2">-</td>
                    <td class="border px-4 py-2">${rental.rental_status}</td>
                  </tr>
                </c:if>
              </c:forEach>

            </c:when>
            <c:otherwise>
              <tr>
                <td colspan="8" class="border px-4 py-4 text-gray-500">현재 대여중인 회원이 없습니다.</td>
              </tr>
            </c:otherwise>
          </c:choose>
        </tbody>
      </table>
    </div>
  </main>

  <!-- 푸터 -->
  <footer class="border-t mt-20 p-4 text-center text-sm text-gray-600">
    &copy; 2025 PacknGo 관리자 페이지. All rights reserved.
  </footer>

</body>
</html>
