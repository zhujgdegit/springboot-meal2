package top.naccl.bean;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "orders")
public class OrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //订单编号
    @Column(name = "ordcode")
    private String ordCode;

    @Column(name = "deliveryMethod")
    private String deliveryMethod;

    @Column(name = "paymentMethod")
    private String paymentMethod;

    @Column(name = "deliveryFee")
    private String deliveryFee;

    @Column(name = "address")
    private String address;

    @Column(name = "delivery_time")
    private String deliveryTime;

    @Column(name = "arrival_time")
    private String arrivalTime;

    @Column(name = "foodId")
    private Integer foodId;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "size")
    private String size;

    @Column(name = "toppings")
    private String toppings;

    // 评价等级
    @Column(name = "rating")
    private String rating;

    // 评价内容
    private String comment;
    @Column(name = "status")
    private String status;

    //下单时间
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    @Column(name = "creat_time")
    private Date creatTime;

    @Column(name = "remark")
    private String remark;

}
