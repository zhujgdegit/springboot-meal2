package top.naccl.service;

import top.naccl.bean.OrderInfo;

import java.util.List;

public interface OrderService {
    Double getAverageRatingByFoodId(Integer foodId);
    List<OrderInfo> getTopCommentsByFoodId(Integer foodId, int limit);

    /**
     * Order deletion - persistent deletion
     * @param ordCode order code
     */
    void deleteByCode(String ordCode);

    /**
     * gain order information
     * @param orderId
     * @return
     */
    OrderInfo getOrderInfoById(Integer orderId);

    /**
     * accept orders
     * @param ordCode
     */
    void updateByCode(String ordCode);
}
