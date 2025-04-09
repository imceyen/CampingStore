package com.camping.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.action.Action;
import com.camping.action.ActionForward;
import com.camping.model.ProductDAO;

public class AdminProductDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// get 방식으로 넘어온 상품번호에 해당하는 상품을
		// DB에서 삭제하는 비지니스 로직.
		int product_no = 
				Integer.parseInt(request.getParameter("no").trim());
		
		ProductDAO dao = ProductDAO.getInstance();
		
		int check = dao.deleteProduct(product_no);
		
		PrintWriter out = response.getWriter();
		
		
		if(check > 0) {
			dao.updateSequence(product_no);
			
			out.println("<script>");
			out.println("alert('상품이 삭제되었습니다.')");
			out.println("location.href='admin_product_list.go'");
			out.println("</script>");
		}else {
			out.println("<script>");
			out.println("alert('※ 상품이 삭제되지 않았습니다. ※')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		
		return null;
	}

}
