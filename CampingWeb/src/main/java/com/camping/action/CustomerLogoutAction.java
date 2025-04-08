package com.camping.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CustomerLogoutAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// 세션 종료시키기
		HttpSession session = request.getSession(false); // 기존 세션이 있으면 가져옴, 없으면 null
		
		if (session != null) {
			session.invalidate();
			
		} // if end
		
		
		// 세션 종료 후 메인 페이지로 이동
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(true);
		forward.setPath("main.jsp");
		
		return forward;
	}

}
