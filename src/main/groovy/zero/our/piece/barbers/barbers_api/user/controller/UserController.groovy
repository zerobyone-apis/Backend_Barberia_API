package zero.our.piece.barbers.barbers_api.user.controller

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import zero.our.piece.barbers.barbers_api._configuration.RestCrossOriginController
import zero.our.piece.barbers.barbers_api.user.model.DTO.RequestUserLoginDTO
import zero.our.piece.barbers.barbers_api.user.model.User
import zero.our.piece.barbers.barbers_api.user.service.UserService

@Slf4j
@RestCrossOriginController("/user")
class UserController {

    @Autowired
    UserService userService

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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    def login(@RequestBody RequestUserLoginDTO login) {
        userService.login(login);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    void deleteById(@PathVariable("id") Long id) {
        userService.delete(id);
    }
}
