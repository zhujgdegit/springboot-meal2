package top.naccl.bean;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class StatisticsDTO {
    private Map<String, Long> salesData;
    private long totalUsers;
    private long totalFoodItems;
    private long cartAdditions;
}
