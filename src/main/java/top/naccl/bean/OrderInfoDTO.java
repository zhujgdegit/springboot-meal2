package top.naccl.bean;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class OrderInfoDTO {

    private String ordCode;

    private String deliveryMethod;

    private String paymentMethod;

    private String deliveryFee;

    private String address;

    private String deliveryTime;

    private String arrivalTime;

    private String foodName;

    private Integer quantity;

    private Integer price;

    private Integer id;
    private Integer foodId;

    private String status;
}
