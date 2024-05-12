package top.naccl.controller.user;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.naccl.bean.OrderReviews;
import top.naccl.bean.User;
import top.naccl.service.OrderReviewsService;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * 订单评论相关接口——用户操作
 */
@Controller
@RequestMapping("/user/reviews")
public class UserOrderReviewsController {

    @Autowired
    private OrderReviewsService orderReviewsService;

    @GetMapping("/getReviewsList")
    public String getReviewsList(@RequestParam("orderId") Integer orderId, Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");
        Integer userId = user.getId();
        List<OrderReviews> reviewss = orderReviewsService.getReviewsInfosByFoodId(orderId);
        reviewss.forEach(re -> {
            if ((int) userId == re.getUserId()) {
                re.setIsDelete(true);
            }
        });
        model.addAttribute("reviewsList", reviewss);

        return "user/orderReviews::reviewsList";
    }

    /**
     * 评论删除
     * @param id
     * @return
     */
    @RequestMapping("/deleteInfo")
    public String deleteInfo(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        Integer i = orderReviewsService.deleteInfo(id);
        redirectAttributes.addFlashAttribute("message", "Delete Successfully");
        return "redirect:/user/orderReviews";
    }

}
