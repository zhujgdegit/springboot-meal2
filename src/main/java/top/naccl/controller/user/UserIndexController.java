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
 * @Description: User Ordering Page
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
     * View the list of dishes
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
        }).filter(foodDTO -> !"Off shelves".equals(foodDTO.getState())).collect(Collectors.toList());
        // Create a PageImpl with the DTO list while retaining original page information
        Page<FoodDTO> dtos = new PageImpl<>(dtoList, pageable, foodPage.getTotalElements());

        model.addAttribute("page", dtos);
        return "user/index";
    }

    /**
     * Query dishes
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
        }).filter(foodDTO -> !"Off shelves".equals(foodDTO.getState())).collect(Collectors.toList());

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
            result.put("message", "Login is invalid, please login again!");
        } else {
            Optional<DiningCar> cart = diningCarService.findById(cartId);
            if (cart == null) {
                result.put("success", false);
                result.put("message", "Join the order truck failed!");
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
     * Add dishes to the dining car
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
            result.put("message", "Login expired, please log in again!");
        } else {
            DiningCar diningCar = new DiningCar();
            diningCar.setSize(size);
            diningCar.setToppings(toppings);
            diningCar.setQuantity(quantity);
            diningCar.setUser(user);
            Food food = foodService.getFood(id);
            if ("Sold out".equals(food.getState())) {
                result.put("success", false);
                result.put("message", "This item is sold out!");
                return result;
            }
            diningCar.setFood(foodService.getFood(id));
            foodService.updateViews(id);
            DiningCar d = diningCarService.saveDiningCar(diningCar);
            if (d == null) {
                result.put("success", false);
                result.put("message", "Failed to add to dining car!");
            } else {
                result.put("success", true);
                result.put("message", "Successfully added to dining car!");
            }
        }
        return result;
    }

    /**
     * Update dishes in the dining car
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
            result.put("message", "Login expired, please log in again!");
        } else {
            // Attempt to find the dining cart entry
            Optional<DiningCar> optionalDiningCar = diningCarService.findById(id);
            if (!optionalDiningCar.isPresent()) {
                result.put("success", false);
                result.put("message", "The specified dining cart entry was not found!");
            } else {
                DiningCar diningCar = optionalDiningCar.get();
                // Update properties
                diningCar.setSize(size);
                diningCar.setToppings(toppings);
                diningCar.setQuantity(quantity);
                DiningCar updatedDiningCar = diningCarService.saveDiningCar(diningCar);
                if (updatedDiningCar == null) {
                    result.put("success", false);
                    result.put("message", "Failed to update dining car!");
                } else {
                    result.put("success", true);
                    result.put("message", "Successfully updated dining car!");
                }
            }
        }
        return result;
    }

    @Autowired
    private OrderNumberUtil orderNumberUtil;

    /**
     * User submits order
     */
    @PostMapping("/order")
    public String submitOrder(OrderInfo orderInfo, HttpSession session) {
        JSONObject result = new JSONObject();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            result.put("success", false);
            result.put("message", "Login expired, please log in again!");
        } else {
            orderInfo.setUserId(user.getId());
            DiningCar driverCarByFoodId = diningCarService.getDriverCarByFoodId(orderInfo.getFoodId(), user.getId());
            orderInfo.setQuantity(Integer.valueOf(driverCarByFoodId.getQuantity()));
            orderInfo.setSize(driverCarByFoodId.getSize());
            orderInfo.setToppings(driverCarByFoodId.getToppings());
            // Query order
            List<OrderInfo> select = orderRepository.getNumByCodeLike(orderNumberUtil.generateOrderNumber(0L, "select"), PageRequest.of(0, 1));
            long l = 0;
            if (!CollectionUtils.isEmpty(select) && !Objects.isNull(select.get(0))) {
                String ordCode = select.get(0).getOrdCode();
                if (ordCode.contains("-")) {
                    ordCode = ordCode.substring(0, 14);
                }
                l = orderNumberUtil.splitCode(ordCode);
            }

            // Generate order number
            orderInfo.setOrdCode(orderNumberUtil.generateOrderNumber(l, "insert"));
            // Set order time
            orderInfo.setCreatTime(new Date());
            OrderInfo order = orderRepository.save(orderInfo);
            if (order == null) {
                result.put("success", false);
                result.put("message", "Failed to place order!");
            } else {
                result.put("success", true);
                result.put("message", "Order placed successfully!");
            }
            diningCarService.deleteByFoodId(order.getFoodId());
        }
        return "redirect:/user/index";
    }

    /**
     * Batch orders
     *
     * @param orderDetails
     * @param session
     * @return
     */
    @PostMapping("/submitAllOrders")
    public ResponseEntity<?> submitAllOrders(@RequestBody OrderSubmissionDTO orderDetails, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login expired, please log in again!");


        }

        List<DiningCar> cartItems = diningCarService.findAllByUserId(user.getId());
        JSONObject result = new JSONObject();
        boolean allSuccess = true;

        //查询订单
        List<OrderInfo> select = orderRepository.getNumByCodeLike(orderNumberUtil.generateOrderNumber(0L, "select"), PageRequest.of(0, 1));
        long l = 0;
        if (!CollectionUtils.isEmpty(select) && !Objects.isNull(select.get(0))) {
            String ordCode = select.get(0).getOrdCode();
            if (ordCode.contains("-")) {
                ordCode = ordCode.substring(0, 14);
            }
            l = orderNumberUtil.splitCode(ordCode);
        }

        //生成订单编号
        String code = orderNumberUtil.generateOrderNumber(l, "insert");

        int i = 0;
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

            orderInfo.setOrdCode(code + "-" + (i + 1));
            //设置下单时间
            orderInfo.setCreatTime(new Date());
            OrderInfo savedOrder = orderRepository.save(orderInfo);
//            orderInfo.setArrivalTime(orderDetails.getArrivalTime());
            orderInfo.setDeliveryTime(orderDetails.getDeliveryTime());
            orderInfo.setRemark(orderDetails.getRemark());
            if (savedOrder == null) {
                allSuccess = false;  // If any order fails to save, mark as failed
            } else {
                diningCarService.deleteByFoodId(savedOrder.getFoodId());
            }
            i++;
        }

        if (allSuccess) {
            result.put("success", true);
            result.put("message", "All orders submitted successfully!");
            return ResponseEntity.ok(result.toString());
        } else {
            result.put("success", false);
            result.put("message", "Partial or all orders failed to submit!");
            return ResponseEntity.badRequest().body(result.toString());
        }
    }

    @Autowired
    private OrderReviewsService orderReviewsService;

    @Autowired
    private UserService userService;


    /**
     * User submits review
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
            result.put("message", "Login expired, please log in again!");
        } else {
            // Original function deprecated
//            Integer i = orderRepository.updateById(rating, comment, orderId);

            // Function expansion
            user = userService.getUser(user.getId());
            Integer res = orderReviewsService.addReviews(user, rating, comment, orderId, reviewsId);
            if (res <= 0) {
                result.put("success", false);
                result.put("message", "Failed to review!");
            } else {
                result.put("success", true);
                result.put("message", "Reviewed successfully!");
            }
        }
        return result;
    }
}