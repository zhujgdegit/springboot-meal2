package top.naccl.service;

import top.naccl.bean.OrderInfo;

import java.util.List;

public interface OrderService {
    Double getAverageRatingByFoodId(Integer foodId);
    List<OrderInfo> getTopCommentsByFoodId(Integer foodId, int limit);
}
