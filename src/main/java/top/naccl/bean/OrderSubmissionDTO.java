package top.naccl.bean;

import lombok.Data;

import javax.persistence.Column;

@Data
public class OrderSubmissionDTO {
    private String deliveryMethod;

    private String paymentMethod;

    private String deliveryFee;

    private String address;

}
