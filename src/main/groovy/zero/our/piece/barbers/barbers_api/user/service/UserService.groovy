package zero.our.piece.barbers.barbers_api.user.service

import groovy.util.logging.Slf4j
import org.hibernate.exception.SQLGrammarException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api._security.infrastructure.PasswordConfig
import zero.our.piece.barbers.barbers_api._security.model.ConfirmationToken
import zero.our.piece.barbers.barbers_api._security.service.ConfirmationTokenService
import zero.our.piece.barbers.barbers_api.barber.model.Barber
import zero.our.piece.barbers.barbers_api.barber.model.DTO.BarberResponseDTO
import zero.our.piece.barbers.barbers_api.barber.service.BarberService
import zero.our.piece.barbers.barbers_api.client.model.Client
import zero.our.piece.barbers.barbers_api.client.model.DTO.ClientResponseDTO
import zero.our.piece.barbers.barbers_api.client.service.ClientService
import zero.our.piece.barbers.barbers_api.enterprise.infrastructure.EnterpriseUsers
import zero.our.piece.barbers.barbers_api.enterprise.repository.EnterpriseUsersRepository
import zero.our.piece.barbers.barbers_api.magicCube.exception.CreateResourceException
import zero.our.piece.barbers.barbers_api.magicCube.exception.ResourceNotFoundException
import zero.our.piece.barbers.barbers_api.user.infrastructure.UsersRoles
import zero.our.piece.barbers.barbers_api.user.model.DTO.RequestUserLoginDTO
import zero.our.piece.barbers.barbers_api.user.model.DTO.ResponseUserLoginDTO
import zero.our.piece.barbers.barbers_api.user.model.DTO.UserResponseDTO
import zero.our.piece.barbers.barbers_api.user.model.User
import zero.our.piece.barbers.barbers_api.user.repository.UserRepository

import java.time.Instant
import java.time.LocalDateTime

@Service
@Slf4j
class UserService {

    @Autowired
    UserRepository userRepository

    @Autowired
    BarberService barberService

    @Autowired
    ClientService clientService

    @Autowired
    RegisterLoginService registerLoginService

    @Autowired
    BCryptPasswordEncoder passwordEncoder

    @Autowired
    ConfirmationTokenService tokenService

    @Autowired
    EnterpriseUsersRepository enterpriseUsersRepository

    private Long FIRST_SOCIAL_NUMBER = 600L

    List<UserResponseDTO> findAll() {
        try {
            userRepository.findAll().collect { it -> decoratorPatternUser(it) }
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    UserResponseDTO findById(Long id) {
        User foundUser = findUserById(id)
        return decoratorPatternUser(foundUser)
    }

    User findUserById(Long id) {
        try {
            userRepository.findById(id).get()
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    User findByEmail(String email) {
        try {
            User foundUser = userRepository.findByEmail(email)
            if (foundUser.email) {
                return foundUser
            }
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    User findByUsername(String username) {
        try {
            User foundUser = userRepository.findByUsername(username)
            if (foundUser.username) {
                return foundUser
            }
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    ResponseUserLoginDTO login(RequestUserLoginDTO login) {
        ResponseUserLoginDTO response = new ResponseUserLoginDTO()
        User user = loginProcess(login)
        response = getUserByroles(user)
        response.user = decoratorPatternUser(user)

        response
    }

    UserResponseDTO create(User user) {
        User newUser = createUser(user)
        newUser = saveUser(newUser, 'CREATE')
        return decoratorPatternUser(newUser)
    }

    UserResponseDTO update(User user) {
        User newUser = updateUser(user)
        newUser = saveUser(newUser, 'UPDATE')
        return decoratorPatternUser(newUser)
    }

    void delete(Long id) {
        if (!id) throw new CreateResourceException("INVALID_ID")
        User foundUser = userRepository.findById(id).get()
        if (!foundUser?.username) throw new ResourceNotFoundException("USER_NOT_FOUND")

        log.info("Delete user.. ${foundUser.username}")
        //userRepository.delete(DUMMY) //TODO: -> nosotros borramos logicamente no en DB , se deja esta linea para vaciar base de datos a futuro.
        foundUser.is_active = Boolean.FALSE
        foundUser.update_on = Instant.now()
        update(foundUser)
    }

    User createUser(User user) {
        if (!user) throw new CreateResourceException("ERROR_CREATING_USER")
        new User(
                id: user?.id,
                username: user?.username,
                password: user?.password,
                email: user?.email,
                roles: user?.roles,
                barber_id: user?.barber_id,
                enterprise_id: user?.enterprise_id ?: 1,
                hairdresser_id: user?.hairdresser_id,
                client_id: user?.client_id,
                created_on: Instant.now(),
                social_number: setSocialNumber(user),
                update_on: user?.is_active == Boolean.FALSE ? Instant.now() : null
        )
    }

    User updateUser(User user) {
        if (!user) throw new CreateResourceException("ERROR_CREATING_USER")
        User existentUser = userValidation(user, 'EXIST')
        User updatedUser = new User(
                id: existentUser.id,
                username: user?.username,
                password: user?.password,
                roles: user?.roles ?: UsersRoles.CLIENT,
                barber_id: existentUser?.barber_id ?: user?.barber_id,
                enterprise_id: user?.enterprise_id ?: existentUser?.enterprise_id,
                hairdresser_id: existentUser?.hairdresser_id ?: user?.hairdresser_id,
                client_id: user?.client_id,
                created_on: existentUser.created_on,
                social_number: setSocialNumber(user),
                update_on: Instant.now(),
                is_active: user?.is_active // antes era siempre TRUE -> ahora sera por medio de confirmacion en mail
        )
        updatedUser
    }

    User saveUser(User user, String action) {
        userValidation(user, action)
        try {
            user.password = passwordEncoder.encode(user.password)
            def savedUser = userRepository.save(user)
            enterpriseUsersRepository.save(new EnterpriseUsers(enterprise_id: savedUser.enterprise_id, user_id: savedUser.id, social_number: savedUser.social_number))
            return savedUser
        } catch (Exception ex) {
            log.error("ERROR: Trying to save User due to: ${ex.getMessage()}")
            throw new CreateResourceException("Error: ${ex.getMessage()}")
        }
    }

    // Used by Reserves to find user data info
    ResponseUserLoginDTO findUserToFillReserve(Long userId) {
        def user = this.findUserById(userId)
        def response = getUserByroles(user)
        response.user = decoratorPatternUser(user)
        response
    }

    protected ResponseUserLoginDTO getUserByroles(User user) {
        ResponseUserLoginDTO response = new ResponseUserLoginDTO()
        switch (user.roles) {
            case UsersRoles.CLIENT:
                response.client = checkUserIsClient(user)
                break
            case UsersRoles.BARBER:
                response.barber = checkUserIsBarber(user)
                break
            case UsersRoles.HAIRDRESSER:
                //todo: hairdresser search
                response.barber = checkUserIsBarber(user)
                break
            case UsersRoles.SUPERVISOR:
                //todo: Supervisor search
                response.barber = checkUserIsBarber(user)
                break
            case UsersRoles.ADMIN:
                //todo: Admin search
                response.barber = checkUserIsBarber(user)
                break
            default:
                log.error("roles Denied.. you have no access to this information.")
                break
        }
        response
    }

/*
    FIXME: BUG
    todo: Revisar como poder logearte si tenemos la password encriptada.
         No matchea el nuevo encode del password con el que esta en la base de datos.
         - Se me ocurre, buscar por email, siempre que exista solo uno, obtener el user, decodear el password, validar con el que nos llega del request.
            y si va to_do bien, devolvemos el usario logeado,

            Pero capas que hay mejores opciones. investigarlo.

 */
    protected User loginProcess(RequestUserLoginDTO user) {
        try {
            User existentUser = new User()
            //todo: deprecated logic -> Solo se logea por email de momento, esta funcionalidad no esta activa.
            if (user?.social_number && user?.password) {
                existentUser = userRepository.findBySocialNumberAndPassword(user.social_number, user.password)
                if (!existentUser?.id) throw new CreateResourceException("Social number or Password are wrong.")
            }

            if (user?.email && user?.password) {
                existentUser = userRepository.findByEmailAndPassword(user.email, user.password)
                if (!existentUser?.id) throw new CreateResourceException("Email or Password are wrong.")
            }

            // Todo: Registro de login para anliticas
            registerLoginService.login(existentUser)

            return existentUser
        } catch (SQLGrammarException | CreateResourceException ex) {
            log.error("ERROR_LOGIN: ${ex.getMessage()}")
            throw new CreateResourceException("ERROR_LOGIN: ${ex.getMessage()}")
        }
    }

    protected BarberResponseDTO checkUserIsBarber(User user) {
        barberService.findByUserId(user.id)
    }

    protected ClientResponseDTO checkUserIsClient(User user) { clientService.findByUserId(user.id) }

    protected Long setSocialNumber(User user) {
        def socialNumeber = (user.social_number != null && user.social_number > 0)
        try {
            if (socialNumeber) {
                return user.social_number
            } else {
                //todo: El enterprise ID se obtiene de la pagina o Empresa a la que el usuario ser registro, cada barberia o peluqueria va a tener su propio
                //      Sitio web por lo que tendra su propio EnterpriseID al registrarse de un sitio , se consigue el Enterprise ID de esa EMpresa,
                //      Esto nos ayuda a tener claro de que empresa es cada usuario y calcular las metricas correctas con estos datos.
                //      En este caso separamos tambien el Numero Social de los usuarios por sus respectivas Empresas.

                Long maxValue = enterpriseUsersRepository.findMaxSocialNumber(user.enterprise_id)
                if (!maxValue) {
                    log.info("Set first social Number!!!! " + FIRST_SOCIAL_NUMBER)
                    return FIRST_SOCIAL_NUMBER
                } else {
                    return maxValue + 1
                }
            }
        } catch (Exception ex) {
            log.error("ERROR: Finding the MAX Social Number... ${ex.getMessage()}")
            return -1
        }
    }

    protected def userValidation(User user, String action) {
        switch (action) {
            case 'EXIST':
                if (user?.id) throw new CreateResourceException("USER_ID_CANNOT_BE_NULL")

                User checkUsername = userRepository.findById(user.id)
                if (checkUsername?.username) throw new CreateResourceException("User ID: ${user.id} Not Exists.")

                return checkUsername
                break
            case 'CREATE':
                User checkUsername =  userRepository.findByUsername(user.username)
                if (checkUsername?.username) throw new CreateResourceException("User: ${user.username} already exists, please try with another Username.")

                User checkEmail = userRepository.findByEmail(user.email)
                if (checkEmail?.username) throw new CreateResourceException("User: ${user.email} already exists, please try with another Email.")
                break
            case 'UPDATE':
                User checkUsername = userRepository.findByUsername(user.username)
                if (checkUsername?.username) throw new CreateResourceException("User: ${user.username} Not Found.")
                if (checkUsername?.id != user.id) throw new CreateResourceException("User: ${user.id} Not Found.")

                User checkEmail = userRepository.findByEmail(user.email)
                if (checkEmail?.email) throw new CreateResourceException("User: ${user.email} Not Found.")
                if (checkEmail?.id != user.id) throw new CreateResourceException("User: ${user.id} Not Found.")
                break
            default:
                log.info("Action: Action provided is not listed..")
        }

    }

    protected static UserResponseDTO decoratorPatternUser(User user) {
        new UserResponseDTO(
                email: user?.email,
                username: user?.username,
                socialNumber: user?.social_number,
                roles: user?.roles,
                isAdmin: user?.roles == UsersRoles.ADMIN ?: false
        )
    }




}
