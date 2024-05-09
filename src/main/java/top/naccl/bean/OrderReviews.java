package top.naccl.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 订单评价实体
 */
@Data
@Entity
@Table(name = "order_reviews")
public class OrderReviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; //ID

    @Column(name = "food_id")
    private Integer foodId; //商品Id

    @Column(name = "diningcar_id")
    private Integer diningcarId; //订单ID

    @Column(name = "parent_id")
    private Integer parentId; //父节点Id，默认0,根节点

    @Column(name = "user_id")
    private Integer userId; //用户Id

    @Column(name = "user_name")
    private String userName; //用户名称

    @Column(name = "rating")
    private String rating; //评价等级

    @Column(name = "comment")
    private String comment; //评价内容

    @Column(name = "creat_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;  //创建时间
}
