package top.naccl.util;


import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 生成订单号工具类
 */
@Component
public class OrderNumberUtil {

    /**
     * 生成订单号
     *
     * @return
     */
    public String generateOrderNumber() {
        // 获取当前日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = dateFormat.format(new Date());

        // 生成随机数
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000; // 生成1000到9999之间的随机数

        // 拼接订单号，可以根据实际需求制定一定规则
        String orderNumber = "ORD" + date + randomNumber;

        return orderNumber;
    }
}
