package zero.our.piece.barbers.barbers_api._security.infrastructure.jwt

import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import javax.crypto.SecretKey

@Configuration
class JwtSecretKey {

    @Autowired
    JwtConfig jwtConfig

    @Bean
    SecretKey getSecretKey(){
        Keys.hmacShaKeyFor(jwtConfig.secretKey.getBytes())
    }

}
