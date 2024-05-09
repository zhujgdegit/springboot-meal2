package top.naccl.controller.admin;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.naccl.annotation.OnlyAdmin;
import top.naccl.bean.User;
import top.naccl.service.DiningCarService;
import top.naccl.service.OrderService;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.spi.SyncResolver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/admin")
@OnlyAdmin
public class OrderController {
    @Autowired
    DiningCarService diningCarService;

    @Autowired
    private OrderService orderService;

    /**
     * 查看用户点餐情况
     */
    @GetMapping("/orders")
    public String orders(Model model) {
		/*List<String[]> ordersV2 = diningCarService.getOrdersV2();

		List<List<String>> orders = new ArrayList<>();
		for (String[] array : ordersV2) {
			orders.add(Arrays.asList(array));
		}*/

        model.addAttribute("orderMap", diningCarService.getOrdersV2());

        //model.addAttribute("orderMap", diningCarService.getOrders());
        return "admin/orders";
    }

    /**
     * 按分类查询菜品
     */
    @PostMapping("/orders/search")
    public String search(@RequestParam String ordCode, Model model) {

        if (StringUtils.isEmpty(ordCode)) {
            model.addAttribute("orderMap", diningCarService.getOrdersV2());
        } else {
            model.addAttribute("orderMap", diningCarService.getOrdersV2BYCode(ordCode));
        }
        return "admin/orders :: orderList";
    }

    /**
     * 按分类查询菜品
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
