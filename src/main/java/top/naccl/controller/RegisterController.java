package top.naccl.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.naccl.bean.User;
import top.naccl.service.UserService;

import javax.validation.Valid;

/**
 * @Description: 注册
 * @Author: Naccl
 * @Date: 2020-05-17
 */

@Controller
public class RegisterController {
	@Autowired
	private UserService userService;

	/**
	 * 注册页
	 */
	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	/**
	 * 验证、保存注册信息
	 */
	@PostMapping("/register")
	public String register(@Valid User user, BindingResult bindingResult,
	                       @RequestParam String passwordAgain, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "register";
		}

		if (passwordAgain == null || !user.getPassword().equals(passwordAgain)) {
			bindingResult.rejectValue("password", "Error", "incorrect password range or syntax");
			return "register";
		}

		User u = userService.getUserByUsername(user.getUsername());
		if (u != null) {
			bindingResult.rejectValue("username", "nameError", "The user name is unavailable");
			return "register";
		}

		user.setIdent(0);//用户注册页面只能是普通用户
		User user1 = userService.saveUser(user);
		if (user1 == null) {//没保存成功
			bindingResult.rejectValue("username", "nameError", "Exception, registration failed");
			return "register";
		} else {//保存成功
			redirectAttributes.addFlashAttribute("message", "Registration successful, please login");
			return "redirect:/login";
		}
	}

	@PostMapping("/register/verify")
	@ResponseBody
	public JSONObject checkUsername(@RequestParam String username) {
		JSONObject result = new JSONObject();
		User u = userService.getUserByUsername(username);
		if (u != null) {
			result.put("success", false);
			result.put("message", "The user name is unavailable");
		} else {
			result.put("success", true);
			result.put("message", "Avialable");
		}
		System.out.println(result);
		return result;
	}
}
