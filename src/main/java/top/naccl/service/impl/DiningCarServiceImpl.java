package top.naccl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.naccl.bean.DiningCar;
import top.naccl.bean.Food;
import top.naccl.bean.User;
import top.naccl.dao.DiningCarRepository;
import top.naccl.service.DiningCarService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description: 点餐管理操作实现类
 * @Author: Naccl
 * @Date: 2020-05-18
 */

@Service
public class DiningCarServiceImpl implements DiningCarService {
	@Autowired
	DiningCarRepository diningCarRepository;

	@Override
	public List<Food> getUserFoods(Integer id) {

		return diningCarRepository.findByUserId(id);
	}

	public Optional<DiningCar> findById(Integer cartId) {
		return diningCarRepository.findById(cartId);
	}

	@Override
	public Page<Food> getUserFoods(Integer id, Pageable pageable) {
		return diningCarRepository.findByUserId(id, pageable);
	}

	@Override
	public Map<User, List<Food>> getOrders() {
		//public Map<User, List<Food>> getOrders() {
		/*
		List<DiningCar> diningCars = diningCarRepository.findGroupUser();//按用户分组 获取每一个用户的全部点餐车
		Map<User, List<Food>> map = new LinkedHashMap<>();
		for (DiningCar diningCar : diningCars) {//遍历每一个用户的全部点餐车
			List<Food> foods = getUserFoods(diningCar.getUser().getId());//获取该用户的全部菜品
			Food total = new Food();//添加一个合计价格 好麻烦直接这样写了
			total.setName("合计");
			total.setPrice(diningCarRepository.sumPrice(diningCar.getUser().getId()));
			total.setComment(-1);
			foods.add(total);
			map.put(diningCar.getUser(), foods);
		}
		return map;
		*/

		List<String[]> result = diningCarRepository.findGroupUser();


		return null;
	}

	@Override
	public List<String[]> getOrdersV2() {
		List<String[]> result = diningCarRepository.findOrderUser();
		return result;
	}

	@Override
	public List<String[]> getOrdersV2BYCode(String ordCode) {
		List<String[]> result = diningCarRepository.findOrderUserByCode(ordCode);
		return result;
	}

	@Transactional
	@Override
	public DiningCar saveDiningCar(DiningCar diningCar) {
		return diningCarRepository.save(diningCar);
	}

	@Transactional
	@Override
	public void deleteDiningCarByUserIdAndFoodId(Integer userId, Integer foodId) {
		diningCarRepository.deleteByUserIdAndFoodId(userId, foodId);
	}

	// 根据 foodId 删除所有相关的 diningcar 行
	public void deleteByFoodId(Integer foodId){
		diningCarRepository.deleteByFoodId(foodId);
	}

	@Override
	public DiningCar getDriverCarByFoodId(Integer foodId, Integer userId) {
		return diningCarRepository.findByUserIdAndFoodId(foodId, userId);
	}

	@Override
	public List<DiningCar> findAllByUserId(Integer userId) {
		return diningCarRepository.findAllByUserId(userId);
	}


}
