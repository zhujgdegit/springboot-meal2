package top.naccl.controller.user;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.naccl.annotation.OnlyAdmin;
import top.naccl.bean.OrderInfo;
import top.naccl.bean.User;
import top.naccl.service.DiningCarService;
import top.naccl.service.OrderService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
@OnlyAdmin
public class UserOrderController {

    @Autowired
    DiningCarService diningCarService;

    @Autowired
    private OrderService orderService;

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
    public String search(@RequestParam String ordCode, Model model) {

        if (StringUtils.isEmpty(ordCode)) {
            model.addAttribute("orderMap", diningCarService.getOrdersV2());
        } else {
            model.addAttribute("orderMap", diningCarService.getOrdersV2BYCode(ordCode));
        }
        return "user/orderInfo :: orderList";
    }

    /**
     * 订单删除
     */
    @GetMapping("/orders/deleteByCode")
    @ResponseBody
    public JSONObject deleteByCode(@RequestParam String ordCode, HttpSession session) {

        JSONObject result = new JSONObject();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            result.put("success", false);
            result.put("message", "登录已失效，请重新登录！");
        } else {
            orderService.deleteByCode(ordCode);
            result.put("success", true);
            result.put("message", "订单删除成功");
        }
        return result;
    }
}
