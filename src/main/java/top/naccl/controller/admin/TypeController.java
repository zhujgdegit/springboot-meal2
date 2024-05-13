package top.naccl.controller.admin;

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
import top.naccl.bean.Type;
import top.naccl.service.TypeService;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
@OnlyAdmin
public class TypeController {
	@Autowired
	TypeService typeService;

	/**
	 * Get the category list page
	 */
	@GetMapping("/types")
	public String types(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model){
		model.addAttribute("page",typeService.listType(pageable));
		return "admin/types";
	}

	/**
	 * Jump to Add category page
	 */
	@GetMapping("types/input")
	public String input(Model model) {
		model.addAttribute("type", new Type());
		return "admin/types-input";
	}

	/**
	 * Jump to Modify category page
	 */
	@GetMapping("/types/{id}/input")
	public String edit(@PathVariable Integer id, Model model) {
		model.addAttribute("type", typeService.getType(id));
		return "admin/types-input";
	}

	/**
	 * POST submit add, modify category
	 */
	@PostMapping("/types")
	public String post(@Valid Type type, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		Type type1 = typeService.getTypeByName(type.getName());
		if (type1 != null) {//You cannot add a category with the same name or change the category name
			bindingResult.rejectValue("name", "nameError", "Category already exists");
		}

		if (bindingResult.hasErrors()) {
			return "admin/types-input";
		}

		//Save adding and modifying categories
		if (type.getId() == null) {//add
			Type t = typeService.saveType(type);
			if (t == null) {//failed to save
				redirectAttributes.addFlashAttribute("message", "Fail to add");
			} else {//save successfully
				redirectAttributes.addFlashAttribute("message", "Add successfully");
			}
		} else {//modify
			Type t = typeService.updateType(type.getId(), type);
			if (t == null) {//failed to save
				redirectAttributes.addFlashAttribute("message", "Fail to modify");
			} else {//save successfully
				redirectAttributes.addFlashAttribute("message", "Modify successfully");
			}
		}


		return "redirect:/admin/types";
	}

	/**
	 * delete category
	 */
	@GetMapping("/types/{id}/delete")
	public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		typeService.deleteType(id);
		redirectAttributes.addFlashAttribute("message", "Delete successfully");
		return "redirect:/admin/types";
	}
}
