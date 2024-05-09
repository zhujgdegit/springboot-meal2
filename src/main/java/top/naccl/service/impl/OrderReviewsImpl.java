package top.naccl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.naccl.bean.OrderInfo;
import top.naccl.bean.OrderReviews;
import top.naccl.bean.User;
import top.naccl.dao.OrderReviewsRepository;
import top.naccl.service.OrderReviewsService;
import top.naccl.service.OrderService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class OrderReviewsImpl implements OrderReviewsService {

    @Autowired
    private OrderReviewsRepository orderReviewsRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public Integer addReviews(User user, String rating, String comment, Integer orderId, Integer reviewsId) {
        if (Objects.isNull(reviewsId)) {
            reviewsId = 0;
        }

        //查询订单信息
        OrderInfo orderInfo =  orderService.getOrderInfoById(orderId);
        if (Objects.isNull(orderInfo)){
            throw new RuntimeException("订单信息不存在，请刷新！");
        }
        OrderReviews reviews = new OrderReviews();
        reviews.setFoodId(orderInfo.getFoodId());
        reviews.setDiningcarId(orderId);
        reviews.setParentId(reviewsId);
        reviews.setUserId(user.getId());
        reviews.setUserName(user.getNackname());
        reviews.setRating(rating);
        reviews.setComment(comment);
        reviews.setCreatTime(new Date());
        OrderReviews save = orderReviewsRepository.save(reviews);
        if (Objects.isNull(save.getId())){
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public OrderReviews getReviewsInfo(Integer Id) {
        return null;
    }

    @Override
    public List<OrderReviews> getReviewsInfos(OrderReviews reviews) {
        return null;
    }

    @Override
    public Integer deleteInfo(Integer id) {
        orderReviewsRepository.deleteById(id);
        return 1;
    }

    @Override
    public List<OrderReviews> getReviewsInfosByFoodId(Integer orderId) {
        return orderReviewsRepository.getReviewsInfosByFoodId(orderId);
    }
}
