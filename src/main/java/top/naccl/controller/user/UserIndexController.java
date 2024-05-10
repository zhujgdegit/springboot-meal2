package top.naccl.controller.user;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.naccl.annotation.OnlyUser;
import top.naccl.bean.*;
import top.naccl.dao.OrderRepository;
import top.naccl.service.*;
import top.naccl.util.OrderNumberUtil;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 用户点餐页
 * @Author: Naccl
 * @Date: 2020-05-18
 */

@Controller
@RequestMapping("/user")
//@OnlyUser
public class UserIndexController {
    @Autowired
    FoodService foodService;
    @Autowired
    TypeService typeService;
    @Autowired
    DiningCarService diningCarService;

    @Autowired
    OrderRepository orderRepository;

    /**
     * 查看菜品列表
     */
    @GetMapping("/index")
    public String index(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Food> userFoods = diningCarService.getUserFoods(user.getId());
        List<FoodDTO> userfoodDTO = new ArrayList<>();
        for (Food food : userFoods) {
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
        }
        model.addAttribute("foods", userfoodDTO);
        model.addAttribute("types", typeService.listType());
        Page<Food> foodPage = foodService.listFood(pageable);
        List<FoodDTO> dtoList = foodPage.stream().map(food -> {
            FoodDTO dto = new FoodDTO();
            BeanUtils.copyProperties(food, dto);

            // Find the corresponding DiningCar for the user
            food.getDiningCars().stream()
                    .filter(dc -> dc.getUser().getId() == user.getId())
                    .findFirst()
                    .ifPresent(diningCar -> dto.setCartId(diningCar.getId()));

            return dto;
        }).filter(foodDTO -> !"下架".equals(foodDTO.getState())).collect(Collectors.toList());
        // Create a PageImpl with the DTO list while retaining original page information
        Page<FoodDTO> dtos = new PageImpl<>(dtoList, pageable, foodPage.getTotalElements());

        model.addAttribute("page", dtos);
        return "user/index";
    }

    /**
     * 查询菜品
     */
    @PostMapping("/index/search")
    public String search(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam(required = false) Integer typeId, @RequestParam(required = false) String name,
                         @RequestParam(required = false) String state,
                         Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Food> foods = diningCarService.getUserFoods(user.getId());
        List<FoodDTO> userfoodDTO = new ArrayList<>();
        for (Food food : foods) {
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
        }
        model.addAttribute("foods", userfoodDTO);
//		if ((name != null && !"".equals(name)) || (state != null && !"".equals(state))) {
        Page<Food> foodPage = foodService.listFood(pageable, name, typeId, state);
        List<FoodDTO> dtoList = foodPage.stream().map(food -> {
            FoodDTO dto = new FoodDTO();
            BeanUtils.copyProperties(food, dto);

            // Find the corresponding DiningCar for the user
            food.getDiningCars().stream()
                    .filter(dc -> dc.getUser().getId() == user.getId())
                    .findFirst()
                    .ifPresent(diningCar -> dto.setCartId(diningCar.getId()));

            return dto;
        }).filter(foodDTO -> !"下架".equals(foodDTO.getState())).collect(Collectors.toList());

        // Create a PageImpl with the DTO list while retaining original page information
        Page<FoodDTO> dtos = new PageImpl<>(dtoList, pageable, foodPage.getTotalElements());
        model.addAttribute("page", dtos);

        return "user/index :: foodList";
//		}
		/*if (typeId != null && typeId != 0) {
			Page<Food> foodPage = foodService.listFood(pageable, typeId);
			List<FoodDTO> dtoList = foodPage.stream().map(food -> {
				FoodDTO dto = new FoodDTO();
				BeanUtils.copyProperties(food, dto);

				// Find the corresponding DiningCar for the user
				food.getDiningCars().stream()
						.filter(dc -> dc.getUser().getId() == user.getId())
						.findFirst()
						.ifPresent(diningCar -> dto.setCartId(diningCar.getId()));

				return dto;
			}).collect(Collectors.toList());

			// Create a PageImpl with the DTO list while retaining original page information
			Page<FoodDTO> dtos = new PageImpl<>(dtoList, pageable, foodPage.getTotalElements());
			model.addAttribute("page",dtos);
		} else {
			Page<Food> foodPage = foodService.listFood(pageable);
			List<FoodDTO> dtoList = foodPage.stream().map(food -> {
				FoodDTO dto = new FoodDTO();
				BeanUtils.copyProperties(food, dto);

				// Find the corresponding DiningCar for the user
				food.getDiningCars().stream()
						.filter(dc -> dc.getUser().getId() == user.getId())
						.findFirst()
						.ifPresent(diningCar -> dto.setCartId(diningCar.getId()));

				return dto;
			}).collect(Collectors.toList());

			// Create a PageImpl with the DTO list while retaining original page information
			Page<FoodDTO> dtos = new PageImpl<>(dtoList, pageable, foodPage.getTotalElements());
			model.addAttribute("page",dtos);
		}*/

        /*return "user/index :: foodList";*/
    }

    @PostMapping("/getCartDetails")
    @ResponseBody
    public JSONObject getCartDetails(@RequestParam Integer cartId, HttpSession session) {
        JSONObject result = new JSONObject();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            result.put("success", false);
            result.put("message", "登录已失效，请重新登录！");
        } else {
            Optional<DiningCar> cart = diningCarService.findById(cartId);
            if (cart == null) {
                result.put("success", false);
                result.put("message", "加入点餐车失败！");
            } else {
                result.put("success", true);
                DiningCar car = cart.get();
                result.put("quantity", car.getQuantity());
                result.put("size", car.getSize());
                result.put("toppings", car.getToppings());
            }
        }
        return result;
    }


    /**
     * 添加菜品到点餐车
     */
    @PostMapping("/add")
    @ResponseBody
    public JSONObject addFoodToCar(@RequestParam Integer id,
                                   @RequestParam("quantity") String quantity,
                                   @RequestParam("size") String size,
                                   @RequestParam("toppings") String toppings, HttpSession session) {
        JSONObject result = new JSONObject();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            result.put("success", false);
            result.put("message", "登录已失效，请重新登录！");
        } else {
            DiningCar diningCar = new DiningCar();
            diningCar.setSize(size);
            diningCar.setToppings(toppings);
            diningCar.setQuantity(quantity);
            diningCar.setUser(user);
            Food food = foodService.getFood(id);
            if ("售空".equals(food.getState())) {
                result.put("success", false);
                result.put("message", "该商品已售空！");
                return result;
            }
            diningCar.setFood(foodService.getFood(id));
            foodService.updateViews(id);
            DiningCar d = diningCarService.saveDiningCar(diningCar);
            if (d == null) {
                result.put("success", false);
                result.put("message", "加入点餐车失败！");
            } else {
                result.put("success", true);
                result.put("message", "加入点餐车成功！");
            }
        }
        return result;
    }

    /**
     * 更新点餐车中的菜品
     */
    @PostMapping("/update")
    @ResponseBody
    public JSONObject updateFoodInCar(@RequestParam Integer id,
                                      @RequestParam("quantity") String quantity,
                                      @RequestParam("size") String size,
                                      @RequestParam("toppings") String toppings, HttpSession session) {
        JSONObject result = new JSONObject();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            result.put("success", false);
            result.put("message", "登录已失效，请重新登录！");
        } else {
            // Attempt to find the dining cart entry
            Optional<DiningCar> optionalDiningCar = diningCarService.findById(id);
            if (!optionalDiningCar.isPresent()) {
                result.put("success", false);
                result.put("message", "未找到指定的点餐车条目！");
            } else {
                DiningCar diningCar = optionalDiningCar.get();
                // Update properties
                diningCar.setSize(size);
                diningCar.setToppings(toppings);
                diningCar.setQuantity(quantity);
                DiningCar updatedDiningCar = diningCarService.saveDiningCar(diningCar);
                if (updatedDiningCar == null) {
                    result.put("success", false);
                    result.put("message", "更新点餐车失败！");
                } else {
                    result.put("success", true);
                    result.put("message", "更新点餐车成功！");
                }
            }
        }
        return result;
    }

    @Autowired
    private OrderNumberUtil orderNumberUtil;

    /**
     * 用户提交订单
     */
    @PostMapping("/order")
    public String submitOrder(OrderInfo orderInfo, HttpSession session) {
        JSONObject result = new JSONObject();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            result.put("success", false);
            result.put("message", "登录已失效，请重新登录！");
        } else {
            orderInfo.setUserId(user.getId());
            DiningCar driverCarByFoodId = diningCarService.getDriverCarByFoodId(orderInfo.getFoodId(), user.getId());
            orderInfo.setQuantity(Integer.valueOf(driverCarByFoodId.getQuantity()));
            orderInfo.setSize(driverCarByFoodId.getSize());
            orderInfo.setToppings(driverCarByFoodId.getToppings());
            //查询订单
            List<OrderInfo> select = orderRepository.getNumByCodeLike(orderNumberUtil.generateOrderNumber(0L, "select"), PageRequest.of(0, 1));
            long l = 0;
            if (!CollectionUtils.isEmpty(select) && !Objects.isNull(select.get(0))) {
                l = orderNumberUtil.splitCode(select.get(0).getOrdCode());
            }

            //生成订单编号
            orderInfo.setOrdCode(orderNumberUtil.generateOrderNumber(l, "insert"));
            //设置下单时间
            orderInfo.setCreatTime(new Date());
            OrderInfo order = orderRepository.save(orderInfo);
            if (order == null) {
                result.put("success", false);
                result.put("message", "下单失败！");
            } else {
                result.put("success", true);
                result.put("message", "下单成功！");
            }
            diningCarService.deleteByFoodId(order.getFoodId());
        }
        return "redirect:/user/index";
    }

    @PostMapping("/submitAllOrders")
    public ResponseEntity<?> submitAllOrders(@RequestBody OrderSubmissionDTO orderDetails, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("登录已失效，请重新登录！");
        }

        List<DiningCar> cartItems = diningCarService.findAllByUserId(user.getId());
        JSONObject result = new JSONObject();
        boolean allSuccess = true;

        for (DiningCar cartItem : cartItems) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setUserId(user.getId());
            orderInfo.setQuantity(Integer.valueOf(cartItem.getQuantity()));
            orderInfo.setSize(cartItem.getSize());
            orderInfo.setToppings(cartItem.getToppings());
            orderInfo.setFoodId(cartItem.getFood().getId());
            // Set shared details from the DTO
            orderInfo.setAddress(orderDetails.getAddress());
            orderInfo.setDeliveryMethod(orderDetails.getDeliveryMethod());
            orderInfo.setPaymentMethod(orderDetails.getPaymentMethod());
            orderInfo.setDeliveryFee(orderDetails.getDeliveryFee());
            OrderInfo savedOrder = orderRepository.save(orderInfo);
            if (savedOrder == null) {
                allSuccess = false;  // 如果有任何订单保存失败，则标记失败
            } else {
                diningCarService.deleteByFoodId(savedOrder.getFoodId());
            }
        }

        if (allSuccess) {
            result.put("success", true);
            result.put("message", "所有订单提交成功！");
            return ResponseEntity.ok(result.toString());
        } else {
            result.put("success", false);
            result.put("message", "部分或所有订单提交失败！");
            return ResponseEntity.badRequest().body(result.toString());
        }
    }

    @Autowired
    private OrderReviewsService orderReviewsService;

    @Autowired
    private UserService userService;


    /**
     * 用户提交评价
     */
    @PostMapping("/rating")
    @ResponseBody
    public JSONObject rating(@RequestParam("rating") String rating,
                             @RequestParam("comment") String comment,
                             @RequestParam("orderId") Integer orderId,
                             @RequestParam(value = "reviewsId", required = false) Integer reviewsId
            , HttpSession session) {
        JSONObject result = new JSONObject();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            result.put("success", false);
            result.put("message", "登录已失效，请重新登录！");
        } else {
            //原有功能弃用
//            Integer i = orderRepository.updateById(rating, comment, orderId);

            //功能扩展
            user = userService.getUser(user.getId());
            Integer res = orderReviewsService.addReviews(user, rating, comment, orderId, reviewsId);
            if (res <= 0) {
                result.put("success", false);
                result.put("message", "评价失败！");
            } else {
                result.put("success", true);
                result.put("message", "评价成功！");
            }
        }
        return result;
    }
}
