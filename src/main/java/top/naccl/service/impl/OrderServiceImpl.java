package top.naccl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import top.naccl.bean.OrderInfo;
import top.naccl.dao.OrderRepository;
import top.naccl.service.OrderService;
import top.naccl.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;



@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository; // 假设你有一个OrderRepository

    @Autowired
    private UserService userService; // 假设你有一个UserService

    @Override
    public Double getAverageRatingByFoodId(Integer foodId) {
        return orderRepository.findAverageRatingByFoodId(foodId);
    }

    @Override
    public List<OrderInfo> getTopCommentsByFoodId(Integer foodId, int limit) {
        return orderRepository.findTopCommentsByFoodId(foodId, PageRequest.of(0, limit));
    }

    @Override
    public void deleteByCode(String ordCode) {
        OrderInfo info = orderRepository.getOrderInfoByCode(ordCode);
        if (Objects.isNull(info)) {
            throw new RuntimeException("删除订单信息不存在：订单编号：" + ordCode);
        }
        orderRepository.delete(info);
    }

    @Override
    public OrderInfo getOrderInfoById(Integer orderId) {
        return orderRepository.getOrderInfoById(orderId);
    }

    @Override
    public void updateByCode(String ordCode) {
        OrderInfo orderInfoByCode = orderRepository.getOrderInfoByCode(ordCode);
        orderInfoByCode.setStatus("ACCEPTED");
    }

    private static final String[] STATUSES = {"PENDING", "ACCEPTED", "PREPARING", "SHIPPING", "DELIVERED", "COMPLETED"};

    // 检查订单是否处于状态pending

    @Scheduled(fixedRate = 70000) // 每20秒执行一次
    public void updateOrderStatus() {
        List<OrderInfo> orders = orderRepository.findAll(); // 假设这里简单地获取所有订单
        for (OrderInfo order : orders) {
            String currentStatus = order.getStatus();
            int nextIndex = Arrays.asList(STATUSES).indexOf(currentStatus) + 1;
            if (nextIndex < STATUSES.length) {
                order.setStatus(STATUSES[nextIndex]);
                orderRepository.save(order);
            }
        }
    }

}
