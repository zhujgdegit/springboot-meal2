package top.naccl.service;

import top.naccl.bean.OrderReviews;
import top.naccl.bean.User;

import java.util.List;

/**
 * 订单评价相关接口
 */
public interface OrderReviewsService {

    /**
     * 添加评论
     * @param user
     * @param rating
     * @param comment
     * @param orderId
     * @return
     */
    Integer addReviews(User user, String rating, String comment, Integer orderId, Integer reviewsId);


    /**
     * 查询订单评价信息
     * @param Id
     * @return
     */
    OrderReviews getReviewsInfo(Integer Id);


    /**
     * 订单评价条件查询
     * @param reviews
     * @return
     */
    List<OrderReviews> getReviewsInfos(OrderReviews reviews);


    /**
     * 删除
     * @param id
     * @return
     */
    Integer deleteInfo(Integer id);


    /**
     * 根据商品ID查询评论
     * @param foodId
     * @return
     */
    List<OrderReviews> getReviewsInfosByFoodId(Integer foodId);
}
