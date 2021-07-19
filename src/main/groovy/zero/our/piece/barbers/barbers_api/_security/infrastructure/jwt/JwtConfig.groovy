package zero.our.piece.barbers.barbers_api._security.infrastructure.jwt

import com.google.common.net.HttpHeaders
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class JwtConfig {

    @Value('${auth.jwt.secret-key}')
    protected String secretKey

    @Value('${auth.token.prefix}')
    protected String prefix

    @Value('${auth.token.expiration-days}')
    protected Long expirationDays

    static String getAuthorizationHeader(){
        HttpHeaders.AUTHORIZATION
    }
}
