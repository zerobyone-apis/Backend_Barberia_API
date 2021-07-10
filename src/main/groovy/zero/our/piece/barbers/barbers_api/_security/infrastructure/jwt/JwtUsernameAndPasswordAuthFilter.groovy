package zero.our.piece.barbers.barbers_api._security.infrastructure.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtUsernameAndPasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return this.getAuthenticationManager().authenticate(null);
    }
}
