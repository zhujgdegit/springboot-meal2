package top.naccl.service;

import top.naccl.bean.OrderInfo;

import java.util.List;

public interface OrderService {
    Double getAverageRatingByFoodId(Integer foodId);
    List<OrderInfo> getTopCommentsByFoodId(Integer foodId, int limit);

    /**
     * 订单删除 ——持久化删除
     * @param ordCode 订单编码
     */
    void deleteByCode(String ordCode);
}
