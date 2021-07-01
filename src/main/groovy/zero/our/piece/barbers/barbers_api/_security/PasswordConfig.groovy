package zero.our.piece.barbers.barbers_api._security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class PasswordConfig {

    @Bean
    static PasswordEncoder passwordEncoder(){
        new BCryptPasswordEncoder(10)
    }
}
