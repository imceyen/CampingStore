package com.camping.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.RentalDAO;
import com.camping.model.RentalStockDTO;

public class AdminRentalProductAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
        // DAO 생성
        RentalDAO dao = RentalDAO.getInstance();
        
        // 대여 상품 재고 현황 조회
        List<RentalStockDTO> rentalStockList = dao.getRentalStockStatus();

        // 조회 결과 request에 저장
        request.setAttribute("rentalStockList", rentalStockList);

        // 포워딩
        ActionForward forward = new ActionForward();
        forward.setRedirect(false);
        forward.setPath("view/admin_rental_product.jsp");

        return forward;
    }
}
