package com.camping.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.SalesReportDAO;
import com.camping.model.SalesReportDTO;

public class SalesDateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String startDate = request.getParameter("start_date"); // YYYY-MM-DD
		String endDate = request.getParameter("end_date");

		SalesReportDAO dao = SalesReportDAO.getInstance();
		List<SalesReportDTO> list = dao.getSalesByDateRange(startDate, endDate);

		// 총 매출과 순이익 계산
		int totalSales = 0;
		int totalProfit = 0;

		for (SalesReportDTO dto : list) {
			totalSales += dto.getTotal_sales();
			totalProfit += dto.getTotal_profit();
		}

		// JSP에 전달
		request.setAttribute("salesList", list);
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("totalSales", totalSales);
		request.setAttribute("totalProfit", totalProfit);

		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("view/admin_sales_report.jsp");

		return forward;
	}
}
