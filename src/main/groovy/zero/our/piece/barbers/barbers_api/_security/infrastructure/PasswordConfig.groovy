package zero.our.piece.barbers.barbers_api._security.infrastructure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class PasswordConfig {

    /*
    todo:
        Aprovechar este bcrypt para crear las validaciones y decodeo/ encodeo de los datos sensibles de los usuarios como
        - password,
        - email,
        - nombres de fotos etc..
     */

    @Bean
    static PasswordEncoder passwordEncoder(){
        new BCryptPasswordEncoder(10)
    }
}
