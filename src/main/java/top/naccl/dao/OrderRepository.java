package top.naccl.dao;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import top.naccl.bean.OrderInfo;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderInfo, Integer> {

    @Modifying
    @Transactional
    @Query("update OrderInfo f set f.rating = :rating, f.comment = :comment where f.id = :id")
    int updateById(@Param("rating") String rating, @Param("comment") String comment, @Param("id") Integer id);


    @Query("select o from OrderInfo o left join food f on f.id = o.foodId where f.id is NOT NULL and o.userId = ?1")
    List<OrderInfo> getOrderInfoAll(Integer id);

    @Query("select o from OrderInfo o  where o.userId = ?1 and o.ordCode = ?2")
    List<OrderInfo> getOrderInfoAll(Integer id,String ordCode);


    @Query("SELECT COALESCE(AVG(o.rating), 0) FROM OrderInfo o WHERE o.foodId = :foodId")
    Double findAverageRatingByFoodId(@Param("foodId") Integer foodId);

    @Query("SELECT o FROM OrderInfo o WHERE o.foodId = :foodId AND o.comment IS NOT NULL ORDER BY o.id DESC")
    List<OrderInfo> findTopCommentsByFoodId(@Param("foodId") Integer foodId, Pageable pageable);

    @Query("select o from OrderInfo o  where o.ordCode = ?1")
    OrderInfo getOrderInfoByCode(@Param("ordCode") String ordCode);

    @Query("select o from OrderInfo o  where o.id = ?1")
    OrderInfo getOrderInfoById(@Param("id") Integer orderId);

    @Query("select o from OrderInfo o  where o.ordCode like  %:code% order by o.ordCode desc")
    List<OrderInfo> getNumByCodeLike(@Param("code") String code,Pageable pageable);
}
