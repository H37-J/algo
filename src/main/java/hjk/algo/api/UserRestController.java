package hjk.algo.api;

import hjk.algo.model.User;
import hjk.algo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping("/user")
    public User selectUser() {
        return userService.selectUser();
    }

    @GetMapping("/session")
    public HttpSession getSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session;
    }
}
