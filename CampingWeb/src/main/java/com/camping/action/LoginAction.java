// LoginAction.java
package com.camping.action;

import java.io.IOException;
import javax.servlet.http.*;

import com.camping.model.AdminDAO;
import com.camping.model.AdminDTO;

public class LoginAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("id");
        String userPwd = request.getParameter("pwd");

        AdminDAO dao = AdminDAO.getInstance();
        AdminDTO dto = dao.loginAdmin(userId, userPwd);

        if (dto != null) {
            HttpSession session = request.getSession();
            session.setAttribute("admin", dto);

            ActionForward forward = new ActionForward();
            forward.setRedirect(false);
            forward.setPath("view/admin_main.jsp"); // 관리자 전용 페이지로 이동
            
            System.out.println("로그인 성공, 이동할 경로: " + forward.getPath());
            System.out.println("Redirect? " + forward.isRedirect());

            
            return forward;
        }

        // 로그인 실패 시
        request.setAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
        ActionForward forward = new ActionForward();
        forward.setPath("camping_login.jsp");
        return forward;
    }
}
