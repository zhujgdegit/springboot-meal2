package top.naccl.controller.admin;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.naccl.annotation.OnlyAdmin;
import top.naccl.bean.User;
import top.naccl.service.UserService;

import javax.validation.Valid;
import java.lang.reflect.Field;

/**
 * @Description: user management
 * @Author: Naccl
 * @Date: 2020-05-18
 */

@Controller
@RequestMapping("/admin")
@OnlyAdmin
public class UserController {
	@Autowired
	UserService userService;

	/**
	 * Get the user list page
	 */
	@GetMapping("/users")
	public String users(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
		model.addAttribute("page", userService.listUser(pageable));
		return "admin/users";
	}

	/**
	 * jump to The Add User page
	 */
	@GetMapping("/users/input")
	public String input(Model model) {
		model.addAttribute("user", new User());
		return "admin/users-input";
	}

	/**
	 * jump to The Modify User page
	 */
	@GetMapping("/users/{id}/input")
	public String edit(@PathVariable Integer id, Model model) {
		User user = userService.getUser(id);
		User user1 = new User();
		BeanUtils.copyProperties(user, user1);
		splitAddresses(user1);
		model.addAttribute("user", user1);
		return "admin/users-input";
	}

	/**
	 * Split addresses
	 *
	 * @param user
	 * @return
	 */
	private void splitAddresses(User user) {
		try {
			String[] split = user.getAddress().split(",");
			user.setAddress(split[0]);
			for (int i = 1; i < split.length; i++) {
				setFieldValue(user, "address" + (i), split[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setFieldValue(Object obj, String fieldName, String value) throws Exception {
		Field field1 = obj.getClass().getDeclaredField(fieldName);
//        field.setAccessible(true); // Set accessible, even private fields can be accessed
//        field.set(obj, value); // Set the value of the field
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				field.setAccessible(true); // Set accessible, even private fields can be accessed
				field.set(obj, value); // Set the value of the field
				return;
			}
		}
		throw new NoSuchFieldException(fieldName);
	}

	/**
	 * POST submits to add and modify users
	 */
	@PostMapping("/users")
	public String post(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "admin/users-input";
		}

		if (user.getIdent() == null) {
			bindingResult.rejectValue("username", "nameError", "Please select identity");
			return "admin/users-input";
		}

		User user1 = userService.getUserByUsername(user.getUsername());
		if (user.getId() == null) {//add
			if (user1 != null) {//unable to add users with the same name
				bindingResult.rejectValue("username", "nameError", "User already exists");
				return "admin/users-input";
			}

			User u = userService.saveUser(user);
			if (u == null) {//failed to save
				redirectAttributes.addFlashAttribute("message", "Fail to add");
			} else {//save successfully
				redirectAttributes.addFlashAttribute("message", "Add successfully");
			}
		} else {//modify
			if (user1 != null && user1.getId() != user.getId()) {//unable to modify to other users with the same name
				bindingResult.rejectValue("username", "nameError", "User already exists");
				return "admin/users-input";
			}

			User u = userService.updateUser(user.getId(), user);
			if (u == null) {//failed to save
				redirectAttributes.addFlashAttribute("message", "Fail to modify");
			} else {//save successfully
				redirectAttributes.addFlashAttribute("message", "Modify successfully");
			}
		}
		return "redirect:/admin/users";
	}

	/**
	 * delete user
	 */
	@GetMapping("/users/{id}/delete")
	public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		userService.deleteUser(id);
		redirectAttributes.addFlashAttribute("message", "Delete successfully");
		return "redirect:/admin/users";
	}


}
