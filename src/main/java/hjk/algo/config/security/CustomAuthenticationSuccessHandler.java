package hjk.algo.config.security;

import hjk.algo.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        session.setAttribute("id", user.getUsername());
        session.setAttribute("password", user.getPassword());

        Cookie cookie = new Cookie("test" ,"aa");
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        response.addCookie(cookie);

        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/");
    }
}
