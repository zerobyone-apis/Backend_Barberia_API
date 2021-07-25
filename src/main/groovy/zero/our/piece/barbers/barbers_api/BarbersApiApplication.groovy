package zero.our.piece.barbers.barbers_api

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAutoConfiguration
@EnableAsync
class BarbersApiApplication {

	static void main(String[] args) {
		SpringApplication.run(BarbersApiApplication, args)
	}

}
