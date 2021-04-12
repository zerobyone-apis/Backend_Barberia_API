package zero.our.piece.barbers.barbers_api.user.controller

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
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
