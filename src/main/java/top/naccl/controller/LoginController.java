package top.naccl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.naccl.bean.User;
import top.naccl.service.UserService;

import javax.servlet.http.HttpSession;

/**
 * @Description: login
 * @Author: Naccl
 * @Date: 2020-05-17
 */

@Controller
public class LoginController {
	@Autowired
	private UserService userService;

	/**
	 * login page
	 */
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}


	/**
	 * Administrator landing page
	 */

	@GetMapping("/adminLogin")
	public String adminLoginPage() {
		return "/admin/adminLogin";
	}

	/**
	 * Two-Step Verification
	 */
	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
		User user = userService.checkUser(username, password);
		if (user != null) {
			user.setPassword(null);
			session.setAttribute("user", user);
			redirectAttributes.addFlashAttribute("message", "Login Successfully");
			if (user.getIdent() == 0) {
				return "redirect:/user/index";
			} else if (user.getIdent() == 1) {
				return "redirect:/admin/foods";
			}
			return "redirect:/";
		} else {
			redirectAttributes.addFlashAttribute("message", "The user name or password is incorrect");
			return "redirect:/login";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
		session.removeAttribute("user");
		redirectAttributes.addFlashAttribute("message", "Already logged out");
		return "redirect:/";
	}
}
