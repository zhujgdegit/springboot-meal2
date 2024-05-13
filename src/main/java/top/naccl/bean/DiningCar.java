package top.naccl.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.*;

/**
 * @Description: Order food truck entity class
 * @Author: Naccl
 * @Date: 2020-05-17
 */

@Entity(name = "diningcar")
@Table
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DiningCar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//Example Set the auto-increment policy for MySQL
	private Integer id;

	@ManyToOne
	private User user;//The user of the ordering car

	@ManyToOne
	private Food food;//Order the food in the dining car according to the project design document, is there any problem with the logic?

	private String quantity;// Quantity ordered

	private String size; // component

	private String toppings; // flavour

}
