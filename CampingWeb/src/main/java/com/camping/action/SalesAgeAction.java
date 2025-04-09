package com.camping.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.SalesReportDAO;
import com.camping.model.SalesReportDTO;

public class SalesAgeAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        SalesReportDAO dao = SalesReportDAO.getInstance();

        // 연령대 파라미터 받기 (기본값은 0으로 설정)
        String ageGroupParam = request.getParameter("age");
        int ageGroup = 0;

        if (ageGroupParam != null && !ageGroupParam.equals("all")) {
            ageGroup = Integer.parseInt(ageGroupParam); // 예: 10, 20, 30 ...
        }

        List<SalesReportDTO> salesList = dao.getSalesByAgeGroup(ageGroup);

        // 총 매출, 총 순이익 계산
        int totalSales = 0;
        int totalProfit = 0;
        for (SalesReportDTO dto : salesList) {
            totalSales += dto.getTotal_sales();
            totalProfit += dto.getTotal_profit();
        }

        request.setAttribute("salesList", salesList);
        request.setAttribute("totalSales", totalSales);
        request.setAttribute("totalProfit", totalProfit);
        request.setAttribute("selectedAge", ageGroupParam);  // 선택된 연령대 유지

        ActionForward forward = new ActionForward();
        forward.setRedirect(false);
        forward.setPath("view/admin_sales_report.jsp"); // jsp 파일명은 자유롭게 설정 가능!

        return forward;
    }
}
