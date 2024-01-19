package site.hyundai.wewrite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WewriteApplication {

	public static void main(String[] args) {
		SpringApplication.run(WewriteApplication.class, args);
	}

}
