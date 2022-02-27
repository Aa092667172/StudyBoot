package boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.entity")
@EnableJpaRepositories("com.service")
public class WebAppTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebAppTestApplication.class, args);
	}

}
