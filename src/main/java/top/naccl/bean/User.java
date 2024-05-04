package top.naccl.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "user")
@Table
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//设置MySQL的自增策略
	private Integer id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String nackname;

	@Column(nullable = false)
	private String birthday;

	@Column(nullable = false)
	private String introduction;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private Integer ident;//0普通用户 1管理员

	@Column(nullable = false)
	private String telephone;

	@Column(nullable = false)
	private String address;

	@OneToMany(mappedBy = "user")//一个用户对应多个点餐车，User作为被维护端，通过Diningcar的user建立关联
	private List<DiningCar> diningCars;
}
