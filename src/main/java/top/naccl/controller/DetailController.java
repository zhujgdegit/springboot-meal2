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
 * @Description: menu deatils page
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
     * Menu details page
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        model.addAttribute("food", foodService.getFood(id));
        model.addAttribute("averageRating", orderService.getAverageRatingByFoodId(id).intValue()); // Round up the average score and pass it on
        model.addAttribute("comments", orderService.getTopCommentsByFoodId(id, 5)); // Get up to 5 reviews


        List<OrderReviews> reviewss = orderReviewsService.getReviewsInfosByFoodId(id);
        if (Objects.nonNull(user)) {
            Integer userId = user.getId();
            reviewss.forEach(re -> {
                if ((int) userId == re.getUserId()) {
                    re.setIsDelete(true);
                }
            });
        }
        model.addAttribute("reviewsLst", reviewss); // Get up to 5 reviews
        return "detail";
    }

    /**
     * delete comment
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteInfo")
    public String deleteInfo(@RequestParam("id") Integer id, @RequestParam("foodId") Integer foodId, RedirectAttributes redirectAttributes) {
        Integer i = orderReviewsService.deleteInfo(id);
        redirectAttributes.addFlashAttribute("message", "Delete Successfully");
        return "redirect:/detail/" + foodId;
    }
}
