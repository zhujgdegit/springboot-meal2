package top.naccl.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.naccl.annotation.OnlyAdmin;
import top.naccl.bean.Food;
import top.naccl.bean.StatisticsDTO;
import top.naccl.service.DiningCarService;
import top.naccl.service.FoodService;
import top.naccl.service.StatisticsService;
import top.naccl.service.TypeService;
@Controller
@RequestMapping("/admin")
//@OnlyAdmin
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/statistics")
    public String statistics(Model model) {
        // 可以添加模型属性，例如统计数据
        return "admin/statistics";  // 指向统计页面的Thymeleaf模板
    }
    @GetMapping("/statistics/sales")
    public ResponseEntity<StatisticsDTO> getSalesData() {
        return ResponseEntity.ok(new StatisticsDTO());
    }
}
