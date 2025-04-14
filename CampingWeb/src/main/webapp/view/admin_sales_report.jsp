<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>PacknGo 총매출 통계</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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
    <h2 class="text-3xl font-semibold text-center mb-10">총매출 통계</h2>

    <!-- 차트 + 요약 -->
    <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-12">
      <div class="bg-white border rounded-xl shadow p-6">
        <canvas id="salesDonut"
          data-sales="${not empty totalSales ? totalSales : 0}"
          data-profit="${not empty totalProfit ? totalProfit : 0}">
        </canvas>
      </div>
      <div class="flex flex-col justify-center gap-6">
        <div class="bg-gray-100 p-6 rounded-xl shadow-sm">
          <h3 class="text-xl font-semibold mb-2">총 매출</h3>
          <p class="text-2xl font-bold text-blue-600">
            <fmt:formatNumber value="${totalSales}" type="number"/> 원
          </p>
        </div>
        <div class="bg-gray-100 p-6 rounded-xl shadow-sm">
          <h3 class="text-xl font-semibold mb-2">총 순이익</h3>
          <p class="text-2xl font-bold text-green-600">
            <fmt:formatNumber value="${totalProfit}" type="number"/> 원
          </p>
        </div>
      </div>
    </div>

    <!-- 필터 버튼 -->
    <div class="flex flex-wrap justify-center gap-4 mb-10">
      <button onclick="toggleFilter('gender')" class="bg-black text-white px-5 py-2 rounded-lg text-sm hover:bg-gray-800">성별 통계</button>
      <button onclick="toggleFilter('age')" class="bg-black text-white px-5 py-2 rounded-lg text-sm hover:bg-gray-800">연령 통계</button>
      <button onclick="toggleFilter('date')" class="bg-black text-white px-5 py-2 rounded-lg text-sm hover:bg-gray-800">일자 통계</button>
    </div>

    <!-- 필터 영역 -->
    <div id="genderFilter" class="mb-10 hidden text-center">
      <form method="get" action="sales_gender.go" class="flex flex-wrap justify-center items-center gap-4">
        <label for="gender" class="text-sm font-medium">성별:</label>
        <select name="gender" id="gender" class="border rounded-lg px-4 py-2 text-sm">
          <option value="all" <c:if test="${param.gender == 'all'}">selected</c:if>>전체</option>
          <option value="M" <c:if test="${param.gender == 'M'}">selected</c:if>>남성</option>
          <option value="F" <c:if test="${param.gender == 'F'}">selected</c:if>>여성</option>
        </select>
        <button type="submit" class="bg-blue-600 text-white px-5 py-2 rounded-lg text-sm hover:bg-blue-700">조회</button>
      </form>
    </div>

    <div id="ageFilter" class="mb-10 hidden text-center">
      <form method="get" action="sales_age.go" class="flex flex-wrap justify-center items-center gap-4">
        <label for="age" class="text-sm font-medium">연령대:</label>
        <select name="age" id="age" class="border rounded-lg px-4 py-2 text-sm">
          <option value="all" <c:if test="${param.age == 'all'}">selected</c:if>>전체</option>
          <option value="10" <c:if test="${param.age == '10'}">selected</c:if>>10대</option>
          <option value="20" <c:if test="${param.age == '20'}">selected</c:if>>20대</option>
          <option value="30" <c:if test="${param.age == '30'}">selected</c:if>>30대</option>
          <option value="40" <c:if test="${param.age == '40'}">selected</c:if>>40대</option>
          <option value="50" <c:if test="${param.age == '50'}">selected</c:if>>50대</option>
          <option value="60" <c:if test="${param.age == '60'}">selected</c:if>>60대 이상</option>
        </select>
        <button type="submit" class="bg-blue-600 text-white px-5 py-2 rounded-lg text-sm hover:bg-blue-700">조회</button>
      </form>
    </div>

    <div id="dateFilter" class="mb-10 hidden text-center">
      <form method="get" action="sales_date.go" class="flex flex-wrap justify-center items-center gap-4">
        <label class="text-sm font-medium">기간:</label>
        <input type="date" name="start_date" value="${param.start_date}" class="border rounded-lg px-4 py-2 text-sm" required />
        <span>~</span>
        <input type="date" name="end_date" value="${param.end_date}" class="border rounded-lg px-4 py-2 text-sm" required />
        <button type="submit" class="bg-blue-600 text-white px-5 py-2 rounded-lg text-sm hover:bg-blue-700">조회</button>
      </form>
    </div>
	<!-- 상품별 매출 바 차트 -->
<div class="bg-white border rounded-xl p-6 shadow mb-12">
  <h2 class="text-xl font-bold mb-6 text-center">상품별 매출 및 순이익</h2>
  <canvas id="productBarChart"
    data-products='[
      <c:forEach var="dto" items="${salesList}" varStatus="status">
        {"name":"${dto.product_name}", "sales":${dto.total_sales}, "profit":${dto.total_profit}}<c:if test="${!status.last}">,</c:if>
      </c:forEach>
    ]'>
  </canvas>
</div>
	
    <!-- 상품별 매출 테이블 -->
    <div class="bg-white border rounded-xl p-6 shadow">
      <h2 class="text-xl font-bold mb-6">
        상품별 매출 내역
        <c:if test="${not empty param.start_date && not empty param.end_date}">
          <span class="text-sm text-gray-500 ml-2">(${param.start_date} ~ ${param.end_date})</span>
        </c:if>
      </h2>

      <table class="min-w-full border text-sm text-center">
        <thead class="bg-gray-100">
          <tr>
            <th class="border px-4 py-2">상품명</th>
            <th class="border px-4 py-2">매출원가</th>
            <th class="border px-4 py-2">매입원가</th>
            <th class="border px-4 py-2">순이익</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="dto" items="${salesList}">
            <tr>
              <td class="border px-4 py-2">${dto.product_name}</td>
              <td class="border px-4 py-2">
                <fmt:formatNumber value="${dto.total_sales}" type="number" />원
              </td>
              <td class="border px-4 py-2">
                <fmt:formatNumber value="${dto.total_cost}" type="number" />원
              </td>
              <td class="border px-4 py-2">
                <fmt:formatNumber value="${dto.total_profit}" type="number" />원
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </main>

  <!-- 차트 스크립트 -->
  <script>
    const canvas = document.getElementById('salesDonut');
    const totalSales = Number(canvas.dataset.sales) || 0;
    const totalProfit = Number(canvas.dataset.profit) || 0;

    new Chart(canvas.getContext('2d'), {
      type: 'doughnut',
      data: {
        labels: ['총 매출', '순이익'],
        datasets: [{
          label: '금액',
          data: [totalSales, totalProfit],
          backgroundColor: ['#3b82f6', '#10b981'],
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: 'bottom'
          }
        }
      }
    });

    function toggleFilter(type) {
      document.getElementById('genderFilter').classList.add('hidden');
      document.getElementById('ageFilter').classList.add('hidden');
      document.getElementById('dateFilter').classList.add('hidden');
      if (type === 'gender') {
        document.getElementById('genderFilter').classList.remove('hidden');
      } else if (type === 'age') {
        document.getElementById('ageFilter').classList.remove('hidden');
      } else if (type === 'date') {
        document.getElementById('dateFilter').classList.remove('hidden');
      }
    }
  </script>
	<script>
	
  const barCanvas = document.getElementById('productBarChart');
  const rawData = barCanvas.dataset.products;
  const productData = JSON.parse(rawData);

  const labels = productData.map(p => p.name);
  const salesData = productData.map(p => p.sales);
  const profitData = productData.map(p => p.profit);

  new Chart(barCanvas.getContext('2d'), {
    type: 'bar',
    data: {
      labels: labels,
      datasets: [
        {
          label: '총 매출',
          data: salesData,
          backgroundColor: '#3b82f6',
          borderRadius: 5
        },
        {
          label: '순이익',
          data: profitData,
          backgroundColor: '#10b981',
          borderRadius: 5
        }
      ]
    },
    options: {
      responsive: true,
      plugins: {
        legend: {
          position: 'top'
        },
        tooltip: {
          callbacks: {
            label: function(context) {
              return context.dataset.label + ': ' + context.raw.toLocaleString() + '원';
            }
          }
        }
      },
      scales: {
        y: {
          beginAtZero: true,
          ticks: {
            callback: function(value) {
              return value.toLocaleString() + '원';
            }
          }
        }
      }
    }
  });
</script>
	
  <footer class="border-t mt-20 p-4 text-center text-sm text-gray-600">
    &copy; 2025 PacknGo 관리자 페이지. All rights reserved.
  </footer>
</body>
</html>
