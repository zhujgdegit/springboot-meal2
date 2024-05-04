package top.naccl.bean;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class FoodDTO {
	private Integer id;

	private String name;

	private String feature;//特色
	private String material;//材料

	private Integer price;//价格

	private Type type;//1家常 2凉菜 3主食 4饮品

	private String picture;//图片路径 默认NULL
	private Integer hits;//点餐次数 默认为0

	private String seasoning; // 调料


	private Integer comment;//整数代表特价菜的价格，0代表厨师推荐，-1表示为正常菜品

	private List<DiningCar> diningCars;

	private Integer totalPrice;

	private String quantity;

	private String toppings;

	private String size;

	private Integer cartId;
}
