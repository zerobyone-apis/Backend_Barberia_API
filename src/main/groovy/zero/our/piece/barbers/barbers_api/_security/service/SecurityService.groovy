package zero.our.piece.barbers.barbers_api._security.service

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api._security.model.UserSecurity
import zero.our.piece.barbers.barbers_api.magicCube.exception.ResourceNotFoundException

@Service
@Slf4j
class SecurityService {

    @Autowired
    UserSecurityService userSecurityService

    UserSecurity loginUser(String username) throws ResourceNotFoundException{
        UserSecurity user = userSecurityService.loadUserByUsername(username)
        if(!user.isEnabled()) throw new ResourceNotFoundException("User is pending to confirm.. please check your email, and validate your account.")

        // Authenticar al usuario.
        UserSecurity userAuthenticated = setPrincipal(user)
        if (userAuthenticated) {
            log.info("************************************************")
            log.info(" USER AUTHENTICATED -> ${ userAuthenticated.username }")
            log.info("************************************************")
            userAuthenticated
        }
    }

    static UserSecurity setPrincipal(userSecurity) throws ResourceNotFoundException{
        Authentication authentication = new UsernamePasswordAuthenticationToken (
                userSecurity,
                null,
                userSecurity.getAuthorities()
        )
        SecurityContextHolder.getContext().setAuthentication(authentication)
        if ((authentication instanceof AnonymousAuthenticationToken)) throw new ResourceNotFoundException("User is not authenticated")

        UserSecurity userPrincipal = (UserSecurity)authentication.getPrincipal()
        log.info("************************************************")
        log.info(" User principal name -> " + userPrincipal.username)
        log.info(" Is user enabled -> " + userPrincipal.enabled)
        log.info("************************************************")

        userPrincipal
    }
}
