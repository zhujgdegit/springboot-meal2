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
     * Home展示 普通、特价、推荐
     */
	/*@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("common",foodService.listFoodByComment(-1,5));//普通
		model.addAttribute("special",foodService.listFoodBySpecial(5));//特价
		model.addAttribute("recommend",foodService.listFoodByComment(0,5));//推荐
		return "index";
	}*/

    @Autowired
    TypeService typeService;
    @Autowired
    DiningCarService diningCarService;

    @Autowired
    OrderRepository orderRepository;

    /**
     * 查看菜品列表
     */
    @GetMapping("/")
    public String index(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model, HttpSession session) {
		/*User user = (User) session.getAttribute("user");
		List<Food> userFoods = diningCarService.getUserFoods(user.getId());*/
        List<FoodDTO> userfoodDTO = new ArrayList<>();
		/*for (Food food : userFoods) {
			FoodDTO dto = new FoodDTO();
			BeanUtils.copyProperties(food, dto);
			DiningCar diningCar = null;
			for (DiningCar dc : food.getDiningCars()) {
				if (dc.getUser().getId() == user.getId()) {
					diningCar = dc;
					break;
				}
			}
			if (diningCar != null) {
				dto.setCartId(diningCar.getId());
			}
			userfoodDTO.add(dto);
		}*/
        model.addAttribute("foods", userfoodDTO);
        model.addAttribute("types", typeService.listType());
        model.addAttribute("states", Arrays.asList("上架", "下架", "售空"));
        Page<Food> foodPage = foodService.listFood(pageable);
        List<FoodDTO> dtoList = foodPage.stream().map(food -> {
            FoodDTO dto = new FoodDTO();
            BeanUtils.copyProperties(food, dto);

            // Find the corresponding DiningCar for the user
			/*food.getDiningCars().stream()
					.filter(dc -> dc.getUser().getId() == user.getId())
					.findFirst()
					.ifPresent(diningCar -> dto.setCartId(diningCar.getId()));*/

            return dto;
        }).collect(Collectors.toList());

        // Create a PageImpl with the DTO list while retaining original page information
        Page<FoodDTO> dtos = new PageImpl<>(dtoList, pageable, foodPage.getTotalElements());

        model.addAttribute("page", dtos);
        return "index";
    }

    /**
     * 查询菜品
     */
    @PostMapping("/search")
    public String search(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam(required = false) Integer typeId, @RequestParam(required = false) String name,
                         @RequestParam(required = false) String state,
                         Model model, HttpSession session) {
		/*User user = (User) session.getAttribute("user");
		List<Food> foods = diningCarService.getUserFoods(user.getId());*/
        List<FoodDTO> userfoodDTO = new ArrayList<>();
		/*for (Food food : foods) {
			FoodDTO dto = new FoodDTO();
			BeanUtils.copyProperties(food, dto);
			DiningCar diningCar = null;
			for (DiningCar dc : food.getDiningCars()) {
				if (dc.getUser().getId() == user.getId()) {
					diningCar = dc;
					break;
				}
			}
			if (diningCar != null) {
				dto.setCartId(diningCar.getId());
			}
			userfoodDTO.add(dto);
		}*/
        model.addAttribute("foods", userfoodDTO);
        if ((name != null && !"".equals(name)) || (state != null && !"".equals(state))) {
            Page<Food> foodPage = foodService.listFood(pageable, name, typeId, state);
            List<FoodDTO> dtoList = foodPage.stream().map(food -> {
                FoodDTO dto = new FoodDTO();
                BeanUtils.copyProperties(food, dto);

                // Find the corresponding DiningCar for the user
				/*food.getDiningCars().stream()
						.filter(dc -> dc.getUser().getId() == user.getId())
						.findFirst()
						.ifPresent(diningCar -> dto.setCartId(diningCar.getId()));*/

                return dto;
            }).collect(Collectors.toList());

            // Create a PageImpl with the DTO list while retaining original page information
            Page<FoodDTO> dtos = new PageImpl<>(dtoList, pageable, foodPage.getTotalElements());
            model.addAttribute("page", dtos);

            return "index :: foodList";
        }
        if (typeId != null && typeId != 0) {
            Page<Food> foodPage = foodService.listFood(pageable, typeId);
            List<FoodDTO> dtoList = foodPage.stream().map(food -> {
                FoodDTO dto = new FoodDTO();
                BeanUtils.copyProperties(food, dto);

                // Find the corresponding DiningCar for the user
				/*food.getDiningCars().stream()
						.filter(dc -> dc.getUser().getId() == user.getId())
						.findFirst()
						.ifPresent(diningCar -> dto.setCartId(diningCar.getId()));*/

                return dto;
            }).collect(Collectors.toList());

            // Create a PageImpl with the DTO list while retaining original page information
            Page<FoodDTO> dtos = new PageImpl<>(dtoList, pageable, foodPage.getTotalElements());
            model.addAttribute("page", dtos);
        } else {
            Page<Food> foodPage = foodService.listFood(pageable);
            List<FoodDTO> dtoList = foodPage.stream().map(food -> {
                FoodDTO dto = new FoodDTO();
                BeanUtils.copyProperties(food, dto);

                // Find the corresponding DiningCar for the user
				/*food.getDiningCars().stream()
						.filter(dc -> dc.getUser().getId() == user.getId())
						.findFirst()
						.ifPresent(diningCar -> dto.setCartId(diningCar.getId()));*/

                return dto;
            }).collect(Collectors.toList());

            // Create a PageImpl with the DTO list while retaining original page information
            Page<FoodDTO> dtos = new PageImpl<>(dtoList, pageable, foodPage.getTotalElements());
            model.addAttribute("page", dtos);
        }

        return "index :: foodList";
    }


}
