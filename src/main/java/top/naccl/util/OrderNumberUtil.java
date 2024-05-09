package top.naccl.util;


import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    public static String generateOrderNumber(long l, String action) {
        // 获取当前日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = dateFormat.format(new Date());

        // 生成随机数
//        Random random = new Random();
//        int randomNumber = random.nextInt(9000) + 1000; // 生成1000到9999之间的随机数

        // 拼接订单号，可以根据实际需求制定一定规则

        if ("insert".equals(action)) {
            return "ORD" + date + String.format("%04d", (l + 1));
        } else if ("select".equals(action)) {
            return "ORD" + date;
        }
        return null;
    }

    public static void main(String[] args) {
        String s = generateOrderNumber(0, "insert");
        System.out.println(s);
    }

    public long splitCode(String ordCode) {

        String lastFour = ordCode.substring(ordCode.length() - 4);
        return Long.getLong(lastFour);
    }
}
