package top.naccl.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.naccl.annotation.OnlyUser;
import top.naccl.bean.*;
import top.naccl.dao.OrderRepository;
import top.naccl.service.DiningCarService;
import top.naccl.service.FoodService;
import top.naccl.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


@Controller
@RequestMapping("/user")
//@OnlyUser
public class DiningCarController {
    @Autowired
    DiningCarService diningCarService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    FoodService foodService;

    @Autowired
    UserService userService;

    /**
     * View Dining Car, accept GET and POST (paging load method needs POST)
     */
    @RequestMapping("/diningcar")
    public String DiningCar(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                            Model model, HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        user = userService.getUser(user.getId());
        Page<Food> foods = diningCarService.getUserFoods(user.getId(), pageable);
        List<FoodDTO> dtoList = new ArrayList<>();
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
                int price = 0;
                try {
                    price = Integer.parseInt(diningCar.getQuantity()) * food.getPrice();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (!StringUtils.isEmpty(diningCar.getToppings())) {
                    price += 1;
                }
                if ("Medium".equals(diningCar.getSize())) {
                    price += 1;
                }
                if ("Large".equals(diningCar.getSize())) {
                    price += 2;
                }
                dto.setTotalPrice(price);
                dto.setSize(diningCar.getSize());
                dto.setToppings(diningCar.getToppings());
                dto.setQuantity(diningCar.getQuantity());
            }
            dtoList.add(dto);
        }
        Page<FoodDTO> dtos = new PageImpl<>(dtoList);

        model.addAttribute("page", dtos);

        if ("POST".equals(request.getMethod())) {
            return "user/diningcar :: foodList";
        }
        return "user/diningcar";
    }

    @GetMapping("/getAddr")
    @ResponseBody
    public JSONArray getAddr(HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        user = userService.getUser(user.getId());
        List<Map<Integer, String>> maps = addressList(user);
        JSONArray jsonArray = (JSONArray) JSONArray.toJSON(maps);
        return jsonArray;
    }

    private List<Map<Integer, String>> addressList(User user) {
        List<Map<Integer, String>> addressList = new ArrayList<>();
        String[] split = user.getAddress().split(",");

        for (int i = 0; i < split.length; i++) {
            Map<Integer, String> addressMap = new HashMap<>();
            addressMap.put(i, split[i].trim()); // Remove spaces and store address parts
            addressList.add(addressMap);
        }

        return addressList;
    }


    @RequestMapping("/orderInfo")
    public String Order(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model, HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        List<OrderInfoDTO> dtoList = new ArrayList<>();
        List<OrderInfo> orderInfoAll = orderRepository.getOrderInfoAll(user.getId());
        for (OrderInfo orderInfo : orderInfoAll) {
            Food food = foodService.getFood(orderInfo.getFoodId());
            OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
            orderInfoDTO.setFoodName(food.getName());
            orderInfoDTO.setFoodId(food.getId());
            orderInfoDTO.setPrice(food.getPrice());
            BeanUtils.copyProperties(orderInfo, orderInfoDTO);
            dtoList.add(orderInfoDTO);
        }
        //Collections.sort(dtoList, Comparator.comparing(OrderInfoDTO::getFoodName));
        Collections.sort(dtoList, Comparator.comparingInt(OrderInfoDTO::getId).reversed());
        model.addAttribute("page", dtoList);
        if ("POST".equals(request.getMethod())) {
            return "user/diningcar :: foodList";
        }
        return "user/orderInfo";
    }

    @RequestMapping("/orderInfoNew")
    public String OrderNew(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                           Model model, HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        List<OrderInfo> orderInfoAll = orderRepository.getOrderInfoAll(user.getId());
        List<List<OrderInfoDTO>> groupedOrders = new ArrayList<>();

        List<OrderInfoDTO> currentGroup = new ArrayList<>();
        Date lastOrderTime = null;

        for (OrderInfo orderInfo : orderInfoAll) {
            Food food = foodService.getFood(orderInfo.getFoodId());
            OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
            orderInfoDTO.setFoodName(food.getName());
            orderInfoDTO.setFoodId(food.getId());
            BeanUtils.copyProperties(orderInfo, orderInfoDTO);

            int price = orderInfo.getQuantity() * food.getPrice();
            if (!StringUtils.isEmpty(orderInfo.getToppings())) {
                price += 1;
            }
            if ("Medium".equals(orderInfo.getSize())) {
                price += 1;
            }
            if ("Large".equals(orderInfo.getSize())) {
                price += 2;
            }
            if ("pickup".equals(orderInfoDTO.getDeliveryMethod())) {
                orderInfo.setDeliveryFee("0");
                orderInfoDTO.setDeliveryFee("0");
            }
            price += Integer.parseInt(orderInfo.getDeliveryFee());
            orderInfoDTO.setPrice(price);

            //todo: Why do this? Because we need to add fields to the database, temporarily commented out
            /*if (lastOrderTime != null && Math.abs(orderInfo.getCreatedAt().getTime() - lastOrderTime.getTime()) > 5000) {
                groupedOrders.add(new ArrayList<>(currentGroup));
                currentGroup.clear();
            }

            currentGroup.add(orderInfoDTO);
            lastOrderTime = orderInfo.getCreatedAt();*/
        }

        if (!currentGroup.isEmpty()) {
            groupedOrders.add(currentGroup);
        }

        model.addAttribute("groupedOrders", groupedOrders);
        if ("POST".equals(request.getMethod())) {
            return "user/diningcar :: food List";
        }
        return "user/orderInfo";
    }


    /**
     * Remove dishes from the dining car
     */
    @PostMapping("/del")
    @ResponseBody
    public JSONObject addFoodToCar(@RequestParam Integer id, HttpSession session) {
        JSONObject result = new JSONObject();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            result.put("success", false);
            result.put("message", "Login expired, please log in again!");
        } else {
            diningCarService.deleteDiningCarByUserIdAndFoodId(user.getId(), id);
            result.put("success", true);
            result.put("message", "Successfully removed from dining car!");
        }
        return result;
    }
}
