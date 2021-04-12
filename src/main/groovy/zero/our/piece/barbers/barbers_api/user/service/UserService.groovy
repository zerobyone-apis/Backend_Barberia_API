package zero.our.piece.barbers.barbers_api.user.service

import groovy.util.logging.Slf4j
import org.hibernate.exception.SQLGrammarException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api.barber.model.DTO.BarberResponseDTO
import zero.our.piece.barbers.barbers_api.barber.service.BarberService
import zero.our.piece.barbers.barbers_api.client.model.DTO.ClientResponseDTO
import zero.our.piece.barbers.barbers_api.client.service.ClientService
import zero.our.piece.barbers.barbers_api.enterprise.infrastructure.EnterpriseUsers
import zero.our.piece.barbers.barbers_api.enterprise.repository.EnterpriseUsersRepository
import zero.our.piece.barbers.barbers_api.magicCube.exception.CreateResourceException
import zero.our.piece.barbers.barbers_api.magicCube.exception.ResourceNotFoundException
import zero.our.piece.barbers.barbers_api.user.infrastructure.UsersPermission
import zero.our.piece.barbers.barbers_api.user.model.DTO.RequestUserLoginDTO
import zero.our.piece.barbers.barbers_api.user.model.DTO.ResponseUserLoginDTO
import zero.our.piece.barbers.barbers_api.user.model.DTO.UserResponseDTO
import zero.our.piece.barbers.barbers_api.user.model.User
import zero.our.piece.barbers.barbers_api.user.repository.UserRepository

import java.time.Instant
import java.util.stream.Collectors

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
    EnterpriseUsersRepository enterpriseUsersRepository

    private Long FIRST_SOCIAL_NUMBER = 600L

    List<UserResponseDTO> findAll() {
        try {
            userRepository.findAll()
                    .stream()
                    .map({ it -> decoratorPatternUser(it) })
                    .collect(Collectors.toList())
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    UserResponseDTO findById(Long id) {
        try {
            def foundUser = userRepository.findById(id).get()
            return decoratorPatternUser(foundUser)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    User findByEmail(String email) {
        try {
            Optional<User> foundUser = userRepository.findByEmail(email)
            if (foundUser.isPresent()) {
                return foundUser.get()
            }
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    User findByUsername(String username) {
        try {
            Optional<User> foundUser = userRepository.findByUsername(username)
            if (foundUser.isPresent()) {
                return foundUser.get()
            }
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    ResponseUserLoginDTO login(RequestUserLoginDTO login) {
        ResponseUserLoginDTO response = new ResponseUserLoginDTO()
        User user = loginProcess(login)
        response.user = decoratorPatternUser(user)

        switch (user.permission) {
            case UsersPermission.CLIENT:
                response.client = checkUserIsClient(user)
                break
            case UsersPermission.BARBER:
                response.barber = checkUserIsBarber(user)
                break
            case UsersPermission.HAIRDRESSER:
                response.barber = checkUserIsBarber(user)
                break
            case UsersPermission.SUPERVISOR:
                response.barber = checkUserIsBarber(user)
                break
            case UsersPermission.ADMIN:
                response.barber = checkUserIsBarber(user)
                break
            default:
                log.error("Permission Denied.. you have no access to this information.")
                break
        }
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
        foundUser.isActive = Boolean.FALSE
        update(foundUser)
    }

    User createUser(User user) {
        if (!user) throw new CreateResourceException("ERROR_CREATING_USER")
        new User(
                id: user?.id,
                username: user?.username,
                password: user?.password,
                email: user?.email,
                permission: user?.permission ?: UsersPermission.CLIENT,
                barber_id: user?.barber_id,
                enterprise_id: user?.enterprise_id,
                hairdresser_id: user?.hairdresser_id,
                client_id: user?.client_id,
                created_on: Instant.now(),
                social_number: setSocialNumber(user),
                update_on: user?.is_active == Boolean.FALSE ? Instant.now() : null,
                is_active: user?.is_active == Boolean.FALSE ?: true
        )
    }

    User updateUser(User user) {
        if (!user) throw new CreateResourceException("ERROR_CREATING_USER")
        User existentUser = userValidation(user, 'EXIST')
        User updatedUser = new User(
                id: existentUser.id,
                username: user?.username,
                password: user?.password,
                permission: user?.permission ?: UsersPermission.CLIENT,
                barber_id: existentUser?.barber_id ?: user?.barber_id,
                enterprise_id: existentUser?.enterprise_id ?: user?.enterprise_id,
                hairdresser_id: existentUser?.hairdresser_id ?: user?.hairdresser_id,
                client_id: user?.client_id,
                created_on: existentUser.created_on,
                social_number: setSocialNumber(user),
                update_on: Instant.now(),
                is_active: user?.is_active == Boolean.FALSE ?: true
        )
        updatedUser
    }

    User saveUser(User user, String action) {
        userValidation(user, action)
        try {
            def savedUser = userRepository.save(user)
            enterpriseUsersRepository.save(new EnterpriseUsers(enterprise_id: savedUser.enterprise_id, user_id: savedUser.id, social_number: savedUser.social_number))
            return savedUser
        } catch (Exception ex) {
            log.error("ERROR: Trying to save User due to: ${ex.getMessage()}")
            throw new CreateResourceException("Error: ${ex.getMessage()}")
        }
    }

    protected User loginProcess(RequestUserLoginDTO user) {
        try {
            User foundUser = new User()
            if (user?.social_number && user?.password) {
                foundUser = userRepository.findBySocialNumberAndPassword(user.social_number, user.password)
                if (!foundUser?.id) throw new CreateResourceException("Social number or Password are wrong.")
            }

            if (user?.email && user?.password) {
                foundUser = userRepository.findByEmailAndPassword(user.email, user.password)
                if (!foundUser?.id) throw new CreateResourceException("Email or Password are wrong.")
            }

            //todo: analitycs table.
            registerLoginService.login(foundUser)

            return foundUser
        } catch (SQLGrammarException | CreateResourceException ex) {
            log.error("ERROR_LOGIN: ${ex.getMessage()}")
            throw new CreateResourceException("ERROR_LOGIN: ${ex.getMessage()}")
        }
    }

    protected BarberResponseDTO checkUserIsBarber(User user) {
        barberService.findByUserId(user.id)
    }

    protected ClientResponseDTO checkUserIsClient(User user) {
        clientService.findByUserId(user.id)
    }

    protected Long setSocialNumber(User user) {
        try {
            if (!user?.social_number) {
                //todo: El enterprise ID se obtiene de la pagina o Empresa a la que el usuario ser registro, cada barberia o peluqueria va a tener su propio
                // Sitio web por lo que tendra su propio EnterpriseID al registrarse de un sitio , se consigue el Enterprise ID de esa EMpresa,
                // ESto nos ayuda a tener claro de que empresa es cada usuario y calcular las metricas correctas con estos datos.
                // En este caso separamos tambien el Numero Social de los usuarios por sus respectivas Empresas.

                Long maxValue = enterpriseUsersRepository.findMaxSocialNumber(user.enterprise_id)
                if (!maxValue) {
                    log.info("Set first social Number!!!! " + FIRST_SOCIAL_NUMBER)
                    return FIRST_SOCIAL_NUMBER
                } else {
                    return maxValue + 1
                }
            } else {
                return user.social_number
            }
        } catch (Exception ex) {
            log.error("ERROR: Finding the MAX Social Number... ${ex.getMessage()}")
            return -1
        }
    }

    protected def userValidation(User user, String action) {
        switch (action) {
            case 'EXIST':
                if (!user?.id) throw new CreateResourceException("USER_ID_CANNOT_BE_NULL")

                Optional<User> checkUsername = userRepository.findById(user.id)
                if (!checkUsername.isPresent()) throw new CreateResourceException("User ID: ${checkUsername.get().id} Not Exists.")

                return checkUsername.get()
                break
            case 'CREATE':
                Optional<User> checkUsername = userRepository.findByUsername(user.username)
                if (checkUsername.isPresent()) throw new CreateResourceException("User: ${checkUsername.get().username} already exists, please try with another Username.")

                Optional<User> checkEmail = userRepository.findByEmail(user.email)
                if (checkEmail.isPresent()) throw new CreateResourceException("User: ${user.email} already exists, please try with another Email.")
                break
            case 'UPDATE':
                Optional<User> checkUsername = userRepository.findByUsername(user.username)
                if (!checkUsername.isPresent()) throw new CreateResourceException("User: ${checkUsername.get().username} Not Found.")
                if (checkUsername.get()?.id != user.id) throw new CreateResourceException("User: ${checkUsername.get().id} Not Found.")

                Optional<User> checkEmail = userRepository.findByEmail(user.email)
                if (!checkEmail.isPresent()) throw new CreateResourceException("User: ${checkEmail.get().email} Not Found.")
                if (checkEmail.get()?.id != user.id) throw new CreateResourceException("User: ${checkEmail.get().id} Not Found.")
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
                permissionRol: user?.permission,
                isAdmin: user?.permission == UsersPermission.ADMIN ?: false
        )
    }
}
