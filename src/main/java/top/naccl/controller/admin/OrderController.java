package top.naccl.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.naccl.annotation.OnlyAdmin;
import top.naccl.service.DiningCarService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/admin")
@OnlyAdmin
public class OrderController {
	@Autowired
	DiningCarService diningCarService;

	/**
	 * 查看用户点餐情况
	 */
	@GetMapping("/orders")
	public String orders(Model model) {
		List<String[]> ordersV2 = diningCarService.getOrdersV2();

		List<List<String>> orders = new ArrayList<>();
		for (String[] array : ordersV2) {
			orders.add(Arrays.asList(array));
		}

		model.addAttribute("orderMap", diningCarService.getOrdersV2());

		//model.addAttribute("orderMap", diningCarService.getOrders());
		return "admin/orders";
	}
}
