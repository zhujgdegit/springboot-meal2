package top.naccl.controller.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.naccl.annotation.OnlyAdmin;
import top.naccl.bean.OrderInfo;
import top.naccl.service.DiningCarService;
import top.naccl.service.OrderService;

@Controller
@RequestMapping("/user")
@OnlyAdmin
public class UserOrderController {

    @Autowired
    DiningCarService diningCarService;

    @Autowired
    private OrderService orderService;

    /**
     * 按分类查询菜品
     */
    @PostMapping("/orders/detail")
    public String search(@RequestParam Integer ordderId, Model model) {

        OrderInfo orderInfo = orderService.getOrderInfoById(ordderId);
        model.addAttribute("orderMap", model);

        return "user/orderInfoCar";
    }
}
