package hjk.algo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String view() {
        return "/main";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/debug-session")
    public String debugSession(HttpSession session, Model model) {
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> {
                    model.addAttribute(name, session.getAttribute(name));
                });
        return "debug-session";
    }
}
