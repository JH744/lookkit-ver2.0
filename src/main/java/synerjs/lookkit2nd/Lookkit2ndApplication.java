package synerjs.lookkit2nd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Lookkit2ndApplication {

	public static void main(String[] args) {
		SpringApplication.run(Lookkit2ndApplication.class, args);
	}

}
