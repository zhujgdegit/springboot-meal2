package top.naccl.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import top.naccl.bean.OrderReviews;

import java.util.List;

@Repository
public interface OrderReviewsRepository extends JpaRepository<OrderReviews, Integer> {

    @Query("select r from OrderReviews r order by r.creatTime desc ")
    List<OrderReviews> getReviewsInfosByFoodId(Integer foodId);
}
