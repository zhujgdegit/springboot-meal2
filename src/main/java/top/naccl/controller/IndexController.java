package top.naccl.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.naccl.bean.DiningCar;
import top.naccl.bean.Food;
import top.naccl.bean.FoodDTO;
import top.naccl.bean.User;
import top.naccl.dao.OrderRepository;
import top.naccl.service.DiningCarService;
import top.naccl.service.FoodService;
import org.springframework.web.bind.annotation.CrossOrigin;
import top.naccl.service.TypeService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: Home
 * @Author: Naccl
 * @Date: 2020-05-17
 */

@Controller
@CrossOrigin(origins = "http://127.0.0.1:63343/springboot-meal/templates/index_n.html")
public class IndexController {
    @Autowired
    private FoodService foodService;

    /**
     * Home display common、special offer、recommended
     */
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("common",foodService.listFoodByComment(-1,5));//common
		model.addAttribute("special",foodService.listFoodBySpecial(5));//special offer
		model.addAttribute("recommend",foodService.listFoodByComment(0,5));//recommended
		return "index";
	}

}
