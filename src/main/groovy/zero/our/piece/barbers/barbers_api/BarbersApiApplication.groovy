package zero.our.piece.barbers.barbers_api

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@EnableAutoConfiguration
class BarbersApiApplication {

	static void main(String[] args) {
		SpringApplication.run(BarbersApiApplication, args)
	}

}
