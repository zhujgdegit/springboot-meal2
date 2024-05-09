package top.naccl.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.naccl.bean.OrderReviews;

@Repository
public interface OrderReviewsRepository extends JpaRepository<OrderReviews, Integer> {
}
