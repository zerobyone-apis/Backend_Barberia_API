package zero.our.piece.barbers.barbers_api._security.infrastructure.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

import javax.crypto.SecretKey
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenVerifier extends OncePerRequestFilter {

    @Autowired
    JwtConfig jwtConfig
    @Autowired
    SecretKey secretKey

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(jwtConfig?.getAuthorizationHeader())

        if (!authorizationHeader || !authorizationHeader?.startsWith(jwtConfig.prefix)) {
            filterChain.doFilter(request, response)
            return
        }

        String token = authorizationHeader?.replace(jwtConfig.prefix, "")

        try {
            Jws<Claims> cliamsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            Claims body = cliamsJws.body
            String username = body.getSubject()
            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = body.get("authorities").collect { it -> new SimpleGrantedAuthority(it.get("authority")) }.toSet()

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username: username,
                    null,
                    simpleGrantedAuthorities
            )

            SecurityContextHolder.getContext().setAuthentication(authentication)
        } catch (JwtException ex) {
            throw new IllegalStateException("TOKEN_CONNOT_BE_TRUSTED: ${token}, MESSAGE: ${ex.message}")
        }

        filterChain.doFilter(request, response)
    }
}
