package com.camping.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.action.Action;
import com.camping.action.ActionForward;
import com.camping.model.ProductDAO;
import com.camping.model.ProductDTO;

public class AminProductModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// get 방식으로 넘어온 제품번호에 해당하는 제픔의
		// 상세내역을 DB에서 조회하여 수정 폼 페이지(view page)로
		// 이동시키는 비지니스 로직.
		int product_no = 
				Integer.parseInt(request.getParameter("no").trim());
		
		ProductDAO dao = ProductDAO.getInstance();
		
		ProductDTO cont = dao.getProductContent(product_no);
		
		request.setAttribute("ProductCont", cont);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		
		forward.setPath("view/admin_product_modify.jsp");
		
		
		return forward;
		
	}

}
