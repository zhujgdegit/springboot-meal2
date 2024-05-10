package top.naccl.controller.user;


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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.naccl.annotation.OnlyAdmin;
import top.naccl.bean.Food;
import top.naccl.bean.OrderInfo;
import top.naccl.bean.OrderInfoDTO;
import top.naccl.bean.User;
import top.naccl.dao.OrderRepository;
import top.naccl.service.DiningCarService;
import top.naccl.service.FoodService;
import top.naccl.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/user")
@OnlyAdmin
public class UserOrderController {

    @Autowired
    DiningCarService diningCarService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FoodService foodService;

    /**
     * 订单详情查找
     */
    @PostMapping("/orders/detail")
    public String search(@RequestParam Integer ordderId, Model model) {

        OrderInfo orderInfo = orderService.getOrderInfoById(ordderId);
        model.addAttribute("orderMap", model);

        return "user/orderInfoCar";
    }

    /**
     * user订单搜索
     */
    @PostMapping("/orders/search")
    public String search(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String ordCode, Model model, HttpSession session, HttpServletRequest request) {

        //TODO 待修复
        User user = (User) session.getAttribute("user");
        List<OrderInfoDTO> dtoList = new ArrayList<>();
        List<OrderInfo> orderInfoAll;
        if (StringUtils.isEmpty(ordCode)) {
            orderInfoAll = orderRepository.getOrderInfoAll(user.getId());
        } else {
            orderInfoAll = orderRepository.getOrderInfoAll(user.getId(), ordCode);
        }

        for (OrderInfo orderInfo : orderInfoAll) {
            Food food = foodService.getFood(orderInfo.getFoodId());
            OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
            orderInfoDTO.setFoodName(food.getName());
            orderInfoDTO.setFoodId(food.getId());
            orderInfoDTO.setPrice(food.getPrice());
            BeanUtils.copyProperties(orderInfo, orderInfoDTO);
            dtoList.add(orderInfoDTO);
        }
        Collections.sort(dtoList, Comparator.comparing(OrderInfoDTO::getFoodName));
        Page<OrderInfoDTO> page = new PageImpl<>(dtoList);
        model.addAttribute("page", page);
        if ("POST".equals(request.getMethod())) {
            return "user/orderInfo :: orderList";
        }
        return "user/orderInfo :: orderList";
    }

    /**
     * 订单删除
     */
    @RequestMapping("/orders/deleteByCode")
    public String deleteByCode(@RequestParam String ordCode, RedirectAttributes redirectAttributes) {
        orderService.deleteByCode(ordCode);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/user/orderInfo";
    }
}
