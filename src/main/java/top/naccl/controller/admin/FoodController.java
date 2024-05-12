package top.naccl.controller.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.naccl.annotation.OnlyAdmin;
import top.naccl.bean.Food;
import top.naccl.service.DiningCarService;
import top.naccl.service.FoodService;
import top.naccl.service.TypeService;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

/**
 * @Description: Dish Management
 * @Author: Naccl
 * @Date: 2020-05-17
 */

@Controller
@RequestMapping("/admin")
//@OnlyAdmin
public class FoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    DiningCarService diningCarService;
    @Autowired
    private TypeService typeService;
    @Value("${upload.path}")
    private String uploadPath;

    /**
     * Dish Management Page
     */
    @GetMapping("/foods")
    public String foods(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", foodService.listFood(pageable));
        return "admin/foods";
    }

    @PostMapping("/updateState")
    @ResponseBody
    public JSONObject updateState(@RequestParam Integer foodId, @RequestParam String state) {
        JSONObject result = new JSONObject();
        Food food = foodService.getFood(foodId);
        food.setState(state);
        Food food1 = foodService.updateFood(foodId, food);
        result.put("success", true);
        result.put("message", state + " succeeded!");
        return result;
    }

    /**
     * Query Dishes by Category
     */
    @PostMapping("/foods/search")
    public String search(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam Integer typeId, Model model) {
        if (typeId != null && typeId != 0) {
            model.addAttribute("page", foodService.listFood(pageable, typeId));
        } else {
            model.addAttribute("page", foodService.listFood(pageable));
        }
        return "admin/foods :: foodList";
    }

    /**
     * Redirect to Dish Addition Page
     */
    @GetMapping("/foods/input")
    public String input(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("food", new Food());
        return "admin/foods-input";
    }

    /**
     * Redirect to Dish Modification Page
     */
    @GetMapping("/foods/{id}/input")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("food", foodService.getFood(id));
        return "admin/foods-input";
    }

    /**
     * POST Submit Adding/Modifying Dish
     */
    @PostMapping("/foods")
    public String post(@Valid Food food, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (food.getType().getId() == null) {
            bindingResult.rejectValue("name", "nameError", "Type cannot be empty");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("types", typeService.listType());
            return "admin/foods-input";
        }

        Food food1 = foodService.getFoodByName(food.getName());
        if (food.getId() == null) {//Adding
            if (food1 != null) {//Cannot add existing dish names
                bindingResult.rejectValue("name", "nameError", "Dish already exists");
                model.addAttribute("types", typeService.listType());
                return "admin/foods-input";
            }

            Food f = foodService.saveFood(food);
            if (f == null) {//Not saved successfully
                redirectAttributes.addFlashAttribute("message", "Adding failed");
            } else {//Saved successfully
                redirectAttributes.addFlashAttribute("message", "Added successfully");
            }
        } else {//Modification
            if (food1 != null && food1.getId() != food.getId()) {//Cannot modify to other existing dish names
                bindingResult.rejectValue("name", "nameError", "Dish already exists");
                model.addAttribute("types", typeService.listType());
                return "admin/foods-input";
            }

            Food f = foodService.updateFood(food.getId(), food);
            if (f == null) {//Not saved successfully
                redirectAttributes.addFlashAttribute("message", "Modification failed");
            } else {//Saved successfully
                redirectAttributes.addFlashAttribute("message", "Modified successfully");
            }
        }
        return "redirect:/admin/foods";
    }


    /**
     * Delete Dish
     */
    @GetMapping("/foods/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        // First delete all rows in the diningcar table that reference the food_id
        diningCarService.deleteByFoodId(id);
        foodService.deleteFood(id);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully");
        return "redirect:/admin/foods";
    }


}
