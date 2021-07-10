package zero.our.piece.barbers.barbers_api._security.infrastructure.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.time.LocalDate

class JwtUsernameAndPasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

    // todo: Secure this key, por alguna razon no funciona si lo pongo en *.yaml
    //@Value ('${jwt.secretkey}')
    private String SECRET_KEY = "THIS_IS_MY_SECURE_SECRET_KEY_BLABLABLALABLABLABLABLA_ZERO"

    AuthenticationManager authenticationManager

    @Override
    Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsernameAndPasswordAuthRequest authRequest = new ObjectMapper().readValue(request.inputStream, UsernameAndPasswordAuthRequest.class)
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authRequest.username,
                    authRequest.password
            )
            Authentication auth = authenticationManager.authenticate(authentication)
            auth
        } catch (IOException ex) {
            throw new RuntimeException("MapperError: ${ex}")
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String token = Jwts.builder()
                .setSubject(authResult.name)
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact()

        response.addHeader("Authorization", "Bearer ${token}")
    }


}
