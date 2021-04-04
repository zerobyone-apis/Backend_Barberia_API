package zero.our.piece.barbers.barbers_api.user.service

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api.magicCube.exception.ResourceNotFoundException
import zero.our.piece.barbers.barbers_api.user.model.RegisterLogins
import zero.our.piece.barbers.barbers_api.user.model.User
import zero.our.piece.barbers.barbers_api.user.repository.RegisterLoginRepository

import java.time.Instant

@Service
@Slf4j
class RegisterLoginService {

    @Autowired
    RegisterLoginRepository registerLoginRepository

    List<RegisterLogins> findAll() {
        registerLoginRepository.findAll()
    }

    RegisterLogins findById(Long id) {
        def foundUser = registerLoginRepository.findById(id).get()
        if (!foundUser?.id) throw new ResourceNotFoundException("REGISTER_NOT_FOUND")

        return foundUser
    }

    void login(User login) {
        registerLoginRepository.save(createLogSession(login, Action.LOGIN))
    }

    void logout(User user) {
        registerLoginRepository.save(createLogSession(user, Action.LOGOUT))
    }

    void updateSession(User user) {
        registerLoginRepository.save(createLogSession(user, Action.UPDATE))
    }

    void addEventsToSession(User user) {
        registerLoginRepository.save(createLogSession(user, Action.MORE_EVENTS))
    }

    static RegisterLogins createLogSession(User login, Action action) {
        return new RegisterLogins(
                username: login.username,
                email: login.email,
                social_number: login.social_number,
                barber_id: login?.barber_id,
                hairdresser_id: login?.hairdresser_id,
                client_id: login?.client_id,
                init_session: Instant.now(),
                end_session: action == Action.LOGOUT ? Instant.now() : null,
                permission: login.permission,
                action: action
        )
    }

}

enum Action {
    UPDATE,
    LOGIN,
    LOGOUT,
    MORE_EVENTS //Definir los tipos de eventos y sus nuevos campos.
}