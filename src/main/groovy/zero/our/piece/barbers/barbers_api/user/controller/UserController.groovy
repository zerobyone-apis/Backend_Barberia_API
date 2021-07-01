package zero.our.piece.barbers.barbers_api.user.controller

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
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
    def getUsers() {
         userService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
    def getUserById(@PathVariable("id") Long idUser) {
        userService.findById(idUser);
    }

    @GetMapping("/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    def getUserByUsername(@PathVariable("username") String username) {
        userService.findByUsername(username);
    }

    @PostMapping("/v1/login")
    @ResponseStatus(HttpStatus.OK)
    def login(@RequestBody RequestUserLoginDTO login) {
        userService.login(login);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteById(@PathVariable("id") Long id) {
        userService.delete(id);
    }
}
