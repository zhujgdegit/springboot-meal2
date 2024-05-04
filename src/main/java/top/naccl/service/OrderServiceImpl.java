package top.naccl.service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.naccl.bean.OrderInfo;
import top.naccl.dao.OrderRepository;
import top.naccl.exception.NotFoundException;
import top.naccl.dao.FoodRepository;
import top.naccl.bean.Food;
import top.naccl.bean.Type;
import top.naccl.util.MyBeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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


    private static final String[] STATUSES = {"PENDING", "ACCEPTED", "PREPARING", "SHIPPING", "DELIVERED", "COMPLETED"};

    @Scheduled(fixedRate = 10000) // 每20秒执行一次
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
    }}
