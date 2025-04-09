package com.camping.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.SalesReportDAO;
import com.camping.model.SalesReportDTO;
import com.camping.model.SalesStatisticsDTO;  // ✅ 추가

public class SalesTotalAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SalesReportDAO dao = SalesReportDAO.getInstance();
        
        // 1. 총매출 계산 및 DB 저장
        dao.calculateAndInsertSalesStatistics();

        // 2. 도넛차트에 필요한 총매출 / 순이익 값 가져오기
        int totalSales = dao.getTotalSales();       // 총 매출액
        int totalProfit = dao.getTotalProfit();     // 순이익

        // 3. 상품별 매출 리스트 (도넛 아래 테이블)
        List<SalesReportDTO> totalSalesList = dao.getTotalSalesList();

        // 4. 전체 매출 통계 리스트 (전체 매출용 테이블)
        List<SalesStatisticsDTO> salesList = dao.getAllSalesStatistics();

        // 5. request 영역에 저장
        request.setAttribute("totalSales", totalSales);
        request.setAttribute("totalProfit", totalProfit);
        request.setAttribute("orderList", totalSalesList);  // 상품별 매출 테이블
        request.setAttribute("salesList", salesList);       // 전체 매출 테이블

        // 6. 이동할 경로 설정
        ActionForward forward = new ActionForward();
        forward.setRedirect(false);
        forward.setPath("view/admin_sales_report.jsp");

        return forward;
    }
}
