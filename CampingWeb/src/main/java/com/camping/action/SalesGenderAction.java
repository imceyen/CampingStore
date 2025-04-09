package com.camping.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.SalesReportDAO;
import com.camping.model.SalesReportDTO;

public class SalesGenderAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 성별 파라미터 가져오기 (m / f / all)
        String gender = request.getParameter("gender");

        // 2. DAO 호출
        SalesReportDAO dao = SalesReportDAO.getInstance();
        List<SalesReportDTO> salesList = dao.getSalesByGender(gender);

        // 3. 총매출/순이익 계산
        int totalSales = 0;
        int totalProfit = 0;
        for (SalesReportDTO dto : salesList) {
            totalSales += dto.getTotal_sales();
            totalProfit += dto.getTotal_profit();
        }

        // 4. request에 데이터 저장
        request.setAttribute("salesList", salesList);
        request.setAttribute("totalSales", totalSales);
        request.setAttribute("totalProfit", totalProfit);
        request.setAttribute("selectedGender", gender);  // 선택 유지용

        // 5. 이동 경로 설정
        ActionForward forward = new ActionForward();
        forward.setRedirect(false);
        forward.setPath("view/admin_sales_report.jsp");

        return forward;
    }
}
