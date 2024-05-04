package top.naccl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class MealApplication {

	public static void main(String[] args) {
		SpringApplication.run(MealApplication.class, args);
	}

}
