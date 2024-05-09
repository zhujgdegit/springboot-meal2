package top.naccl.controller.admin;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.naccl.bean.OrderReviews;
import top.naccl.bean.User;
import top.naccl.service.OrderReviewsService;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * 订单评论相关接口——admin操作
 */
@Controller
@RequestMapping("/admin/reviews")
public class OrderReviewsController {

    @Autowired
    private OrderReviewsService orderReviewsService;

    @GetMapping("/getReviewsList")
    public String getReviewsList(@RequestParam("foodId") Integer foodId, Model model, HttpSession session) {


        List<OrderReviews> reviewss = orderReviewsService.getReviewsInfosByFoodId(foodId);
        reviewss.forEach(re -> {
            re.setIsDelete(false);
        });
        model.addAttribute("reviewsList", reviewss);

        return "admin/orderReviews::reviewsList";
    }

    @GetMapping("/deleteInfo")
    public JSONObject deleteInfo(@RequestParam("id") Integer id) {
        Integer i = orderReviewsService.deleteInfo(id);
        JSONObject result = new JSONObject();
        if (i > 0) {
            result.put("success", true);
            result.put("message", "评论删除成功！");
        } else {
            result.put("success", false);
            result.put("message", "评论删除失败！");
        }
        return result;
    }

}
