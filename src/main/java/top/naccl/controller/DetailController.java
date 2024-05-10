package top.naccl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.naccl.bean.OrderReviews;
import top.naccl.bean.User;
import top.naccl.service.FoodService;
import top.naccl.service.OrderReviewsService;
import top.naccl.service.OrderService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 * @Description: 菜品详情
 * @Author: Naccl
 * @Date: 2020-05-17
 */

@Controller
public class DetailController {
	@Autowired
	FoodService foodService;

	@Autowired
	OrderService orderService;

	@Autowired
	private OrderReviewsService orderReviewsService;


	/**
	 * 菜品详情页
	 */
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable Integer id, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");

		model.addAttribute("food", foodService.getFood(id));
		model.addAttribute("averageRating", orderService.getAverageRatingByFoodId(id).intValue()); // 将平均评分取整后传递
		model.addAttribute("comments", orderService.getTopCommentsByFoodId(id, 5)); // 获取最多5条评论


		List<OrderReviews> reviewss = orderReviewsService.getReviewsInfosByFoodId(id);
		if (Objects.nonNull(user)) {
			Integer userId = user.getId();
			reviewss.forEach(re -> {
				if ((int) userId == re.getUserId()) {
					re.setIsDelete(true);
				}
			});
		}
		model.addAttribute("reviewsLst", reviewss); // 获取最多5条评论
		return "detail";
	}

	/**
	 * 评论删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteInfo")
	public String deleteInfo(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
		Integer i = orderReviewsService.deleteInfo(id);
		redirectAttributes.addFlashAttribute("message", "删除成功");
		return "redirect:/detail";
	}
}
