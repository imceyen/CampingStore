package com.camping.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userId = request.getParameter("id");
        String userPwd = request.getParameter("pwd");

        // 예시용 관리자 계정 (실제는 DB에서 조회)
        if ("admin".equals(userId) && "admin123".equals(userPwd)) {
            // 세션에 관리자 정보 저장
            HttpSession session = request.getSession();
            session.setAttribute("user_id", userId);
            session.setAttribute("is_admin", true);

            ActionForward forward = new ActionForward();
            forward.setRedirect(true);
            forward.setPath("admin/main.jsp"); // 관리자 메인화면
            return forward;
        } else {
            // 일반 회원 처리 or 실패 처리
            boolean isValidUser = /* DB에서 유효성 검사 */ true; // 예시

            if (isValidUser) {
                HttpSession session = request.getSession();
                session.setAttribute("user_id", userId);
                session.setAttribute("is_admin", false);

                ActionForward forward = new ActionForward();
                forward.setRedirect(true);
                forward.setPath("main.jsp"); // 일반 사용자 메인화면
                return forward;
            } else {
                request.setAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
                ActionForward forward = new ActionForward();
                forward.setRedirect(false);
                forward.setPath("login.jsp");
                return forward;
            }
        }
    }
	}

}
