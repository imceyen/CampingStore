package com.camping.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.action.Action;
import com.camping.action.ActionForward;
import com.camping.model.ProductDAO;
import com.camping.model.ProductDTO;

public class AdminProductListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 전체 상품 등록 목록을 DB에서 조회하여
		// view page로 이동시키는 비지니스 로직.
		
		ProductDAO dao = ProductDAO.getInstance();
		
		List<ProductDTO> list = dao.getProductList();
		
		request.setAttribute("productList", list);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		
		forward.setPath("view/admin_product_list.jsp");
		
		
		return forward;
		
	}

}
