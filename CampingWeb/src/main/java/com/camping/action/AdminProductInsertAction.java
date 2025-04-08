package com.camping.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.action.Action;
import com.camping.action.ActionForward;
import com.camping.model.CategoryDAO;
import com.camping.model.CategoryDTO;

public class AdminProductInsertAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 카테고리 코드 전체 리스트를 D에서 조회하여
		// 상품 등록 폼 페이지로 이동시키는 비지니스 로직.
		
		CategoryDAO dao = CategoryDAO.getInstance();
		
		List<CategoryDTO> list = dao.getCategoryList();
		
		request.setAttribute("CategoryList", list);
		
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		
		forward.setPath("view/admin_product_insert.jsp");
		

		
		return forward;
	}

}




