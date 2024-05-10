package top.naccl.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import top.naccl.bean.OrderReviews;

import java.util.List;

@Repository
public interface OrderReviewsRepository extends JpaRepository<OrderReviews, Integer> {

    @Query("select r from OrderReviews r where r.foodId = ?1 order by r.creatTime desc ")
    List<OrderReviews> getReviewsInfosByFoodId(@Param("foodId") Integer foodId);
}
