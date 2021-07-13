package zero.our.piece.barbers.barbers_api._security.infrastructure.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

import javax.crypto.SecretKey
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.time.LocalDate

class JwtUsernameAndPasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

    AuthenticationManager authenticationManager
    @Autowired
    private JwtConfig jwtConfig
    @Autowired
    private SecretKey secretKey

    @Override
    Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsernameAndPasswordAuthRequest authRequest = new ObjectMapper().readValue(request.inputStream, UsernameAndPasswordAuthRequest.class)
            /*TODO:
            *       Tener en cuenta que dentro de UsernamePasswordAuthenticationToken van dos objetos de tipo Object:
            *       @principal  -> En este objeto podriamos guardar toda la data que quisieramos cargar en el contexto a utilizar por ejemplo el dto de User.
            *       @credentials -> En este objeto deberiamos de usarlo unicamente para cuando sea necesario percisir o leer algun tipo de credencial encriptado.
            *       @authoritires -> es una extension, requiere un Collection de GrantedAutorities, sirve para saber que puede hacer ese usuario
            *       Todo esto se podria llegar a usar desde el principal en el controler, y pasarlo al servicio para alguna logica.
            * */
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
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.expirationDays)))
                .signWith(secretKey)
                .compact()

        response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.prefix + token)
    }

    /*  Todo:
    *       Verificacion y envio de mail, con token para validar usuario, y validaciones de expiracion, crud de token, pesistencia en tabla para que podamos identificar cuantos tokens y cuando vencen los mismos.,
    *    -> Recurso : https://youtu.be/QwQuro7ekvc?t=1061
    *    -> Por otro lado, hacer que el token devuelva toda la info del User ya que si no deberia de hacer una llamada mas a mi endpoint de /user/v1/login/ y bucar los datos para encriptarlos o tokenizarlos.
    *       Revisar el TODO que deje mas arriba
    */


}
