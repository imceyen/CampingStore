package com.camping.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.RentalDAO;
import com.camping.model.RentalDTO;

public class AdminRentalListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// DAO 객체 생성
        RentalDAO dao = RentalDAO.getInstance();

        // "대여중" 상태의 회원 대여 목록 + 반납까지 남은 일수 조회
        List<RentalDTO> rentalList = dao.getRentalList();

        // request 영역에 저장
        request.setAttribute("rentalList", rentalList);

        // 페이지 이동 설정
        ActionForward forward = new ActionForward();
        forward.setRedirect(false);  // forward 방식
        forward.setPath("view/admin_rental_list.jsp");

        return forward;
    }
}
