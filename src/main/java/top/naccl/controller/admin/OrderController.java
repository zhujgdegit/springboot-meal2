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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
     * 订单删除
     */
    @PostMapping("/orders/deleteByCode")
    public String deleteByCode(@RequestParam String ordCode, RedirectAttributes redirectAttributes) {
        orderService.deleteByCode(ordCode);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/orderInfo";
    }
}
