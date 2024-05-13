package top.naccl.service;

import top.naccl.bean.OrderReviews;
import top.naccl.bean.User;

import java.util.List;

/**
 * Order reviews related interface
 */
public interface OrderReviewsService {

    /**
     * add reviews
     * @param user
     * @param rating
     * @param comment
     * @param orderId
     * @return
     */
    Integer addReviews(User user, String rating, String comment, Integer orderId, Integer reviewsId);


    /**
     * Query order evaluation information
     * @param Id
     * @return
     */
    OrderReviews getReviewsInfo(Integer Id);


    /**
     * Order reviews condition query
     * @param reviews
     * @return
     */
    List<OrderReviews> getReviewsInfos(OrderReviews reviews);


    /**
     * delete
     * @param id
     * @return
     */
    Integer deleteInfo(Integer id);


    /**
     * Query reviews based on product ID
     * @param foodId
     * @return
     */
    List<OrderReviews> getReviewsInfosByFoodId(Integer foodId);
}
