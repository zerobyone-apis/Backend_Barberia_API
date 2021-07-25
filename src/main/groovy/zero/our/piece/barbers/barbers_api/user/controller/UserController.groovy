package zero.our.piece.barbers.barbers_api.user.controller

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import zero.our.piece.barbers.barbers_api._configuration.RestCrossOriginController
import zero.our.piece.barbers.barbers_api._security.model.UserSecurity
import zero.our.piece.barbers.barbers_api._security.service.ConfirmationTokenService
import zero.our.piece.barbers.barbers_api._security.service.SecurityService
import zero.our.piece.barbers.barbers_api.magicCube.exception.ResourceNotFoundException
import zero.our.piece.barbers.barbers_api.magicCube.mailing.EmailSender
import zero.our.piece.barbers.barbers_api.magicCube.utils.FileLoad
import zero.our.piece.barbers.barbers_api.user.model.DTO.RequestUserLoginDTO
import zero.our.piece.barbers.barbers_api.user.model.DTO.ResponseUserLoginDTO
import zero.our.piece.barbers.barbers_api.user.model.User
import zero.our.piece.barbers.barbers_api.user.service.UserService

@Slf4j
@RestCrossOriginController("/user")
class UserController {

    @Autowired
    UserService userService

    @Autowired
    SecurityService securityService

    @Autowired
    ConfirmationTokenService confirmationTokenService

    @Autowired
    EmailSender emailSender

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    def getUsers() {
         userService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    def create(@RequestBody User user) {
      userService.create(user);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    def update(@RequestBody User user) {
        userService.update(user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    def getUserById(@PathVariable("id") Long idUser) {
        userService.findById(idUser);
    }

    @GetMapping("/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    def getUserByUsername(@PathVariable("username") String username) {
        userService.findByUsername(username);
    }

    @PostMapping("/v1/login")
    @ResponseStatus(HttpStatus.OK)
    def login(@RequestBody RequestUserLoginDTO login) throws ResourceNotFoundException {
        UserSecurity user = securityService.loginUser(login.username)
        def token = confirmationTokenService.createHeaderToken(user)
        if(!login.password) throw new ResourceNotFoundException("Password is required Field... Please check the information.")
        if(!user?.username) throw new ResourceNotFoundException("User is not Authenticated...")

        // update dto to logged in.
        login.email = user.email
        //todo: crear una validacion para que el decode del password y el password recibido sean los mismos.
        login.password = user.password

        ResponseUserLoginDTO userDTO = userService.login(login)

        // Se devuelve el token con todas las authorizations del usuario, y la data del usuario.
        HttpHeaders bearerToken = new HttpHeaders()
        bearerToken.set(token.auth, token.value)
        ResponseEntity.ok()
                .headers(bearerToken)
                .body(userDTO)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    void deleteById(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @PatchMapping("/send/email")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    void sendEmail() {
        String to = "recohen949@dmsdmg.com"
        String subject = "Validation Email Sender Test"
        String name = "ADMIN"
        String url = "http://localhost:8080/test"
        emailSender.send(to, subject, FileLoad.getConfirmBodyEmailHTML(name, url));
    }
}
