package top.naccl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.naccl.bean.StatisticsDTO;

import java.util.Arrays;

@Service
public class StatisticsService {

    @Autowired
    private UserService userService;
    @Autowired
    private FoodService foodService;

    public StatisticsDTO getStatistics() {
        StatisticsDTO dto = new StatisticsDTO();
        return dto;
    }

}
