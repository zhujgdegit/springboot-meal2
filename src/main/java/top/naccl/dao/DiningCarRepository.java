package top.naccl.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import top.naccl.bean.DiningCar;
import top.naccl.bean.DiningCarDTO;
import top.naccl.bean.Food;

import java.util.List;

public interface DiningCarRepository extends JpaRepository<DiningCar, Integer> {

	//
	//@Query("select d from diningcar d group by d.user order by d.user.id desc")
	//List<DiningCar> findGroupUser();

	@Query("SELECT u.username as userName,fo.name as foodName FROM user u LEFT JOIN diningcar d ON u.id = d.user.id LEFT JOIN food fo ON d.food.id = fo.id where fo.name IS NOT NULL ORDER BY u.username DESC")
	List<String[]> findGroupUser();


	@Query("SELECT u.username as userName,fo.name as foodName , o.comment as comments FROM OrderInfo o left JOIN user u on u.id = o.userId LEFT JOIN food fo on fo.id = o.foodId ")
	List<String[]> findOrderUser();


	@Query("select d.food from diningcar d where d.user.id = ?1 order by d.food.id desc")
	List<Food> findByUserId(Integer id);

	@Query("select d.food from diningcar d where d.user.id = ?1 order by d.food.id desc")
	Page<Food> findByUserId(Integer id, Pageable pageable);

	@Query("select sum(case when d.food.comment>0 then d.food.comment else d.food.price end) from diningcar d where d.user.id=?1")
	int sumPrice(Integer id);

	void deleteByUserIdAndFoodId(Integer userId, Integer foodId);


	// 根据 foodId 删除所有相关的 diningcar 行
	@Transactional
	@Modifying
	@Query("DELETE FROM diningcar d WHERE d.food.id = ?1")
	void deleteByFoodId(Integer foodId);

	@Query("SELECT d FROM diningcar d WHERE d.user.id = :userId AND d.food.id = :foodId")
	DiningCar findByUserIdAndFoodId(Integer foodId, Integer userId);

	// 新方法，基于用户ID查找所有购物车条目
	@Query("SELECT d FROM diningcar d WHERE d.user.id = :userId")
	List<DiningCar> findAllByUserId(Integer userId);
}
