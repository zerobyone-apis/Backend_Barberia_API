package zero.our.piece.barbers.barbers_api.barber.service

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api.barber.infrastructure.EnterpriseRoll
import zero.our.piece.barbers.barbers_api.barber.model.Barber
import zero.our.piece.barbers.barbers_api.barber.model.DTO.BarberRequestDTO
import zero.our.piece.barbers.barbers_api.barber.model.DTO.BarberResponseDTO
import zero.our.piece.barbers.barbers_api.barber.repository.BarberRepository
import zero.our.piece.barbers.barbers_api.barber.repository.BarberUsersRepository
import zero.our.piece.barbers.barbers_api.enterprise.service.ShopTimeService
import zero.our.piece.barbers.barbers_api.magicCube.exception.CreateResourceException
import zero.our.piece.barbers.barbers_api.magicCube.exception.ResourceNotFoundException
import zero.our.piece.barbers.barbers_api.user.infrastructure.BarberUsers
import zero.our.piece.barbers.barbers_api.user.infrastructure.UsersRoles
import zero.our.piece.barbers.barbers_api.user.model.User
import zero.our.piece.barbers.barbers_api.user.service.UserService

import java.time.Instant

@Service
@Slf4j
class BarberService {

    @Autowired
    BarberRepository barberRepository

    @Autowired
    BarberUsersRepository barberUsersRepository

    @Autowired
    ShopTimeService shopTimeService

    @Autowired
    UserService userService

    void confirmUserBarber(Barber brb) {
        try {
            barberRepository.save(brb)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException("Error in the confirmed email.. ${ex.message}")
        }
    }

    List<BarberResponseDTO> findAll() {
        try {
            barberRepository.findAll().collect { it -> decoratorPatternBarber(it) }
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    List<BarberResponseDTO> findAllActives() {
        try {
            barberRepository.findAll().stream().filter { it.is_active }.collect { it -> decoratorPatternBarber(it) }
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    BarberResponseDTO findById(Long id) {
        try {
            def foundUser = barberRepository.findById(id).get()
            if (!foundUser?.id) throw new ResourceNotFoundException("BARBER_NOT_FOUND")

            return decoratorPatternBarber(foundUser)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    BarberResponseDTO findByUserId(Long user_id) {
        try {
            Barber barber = barberRepository.findByUserId(user_id)
            if (!barber?.id) throw new ResourceNotFoundException("Barber with this User ID Not found: " + user_id)

            return decoratorPatternBarber(barber)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    Barber getBarberById(Long brbId) {
        try {
            Barber barber = barberRepository.findById(brbId).get()
            if (!barber?.id) throw new ResourceNotFoundException("Barber with this ID Not found: " + brbId)

            return barber
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    BarberResponseDTO create(BarberRequestDTO body) {
        try {
            Barber barber = createBarber(body)
            User user = createUser(body)
            barber.user_id = user.id

            def savedBarber = barberRepository.save(barber)
            user.barber_id = savedBarber.id
            userService.saveUser(user, 'Updating barberID')
            barberUsersRepository.save(new BarberUsers(barberId: user.barber_id, userId: user.id))

            return decoratorPatternBarber(savedBarber)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    BarberResponseDTO update(BarberRequestDTO body, Long barberId) {
        try {
            def existentBarber = barberRepository.findById(barberId)
            if (!existentBarber.isPresent()) throw new ResourceNotFoundException("CLIENT NOT FOUND")

            Barber barber = updateBarber(existentBarber.get(), body)
            updateUser(body)

            def savedBarber = barberRepository.save(barber)
            return decoratorPatternBarber(savedBarber)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    protected static BarberResponseDTO decoratorPatternBarber(Barber brb) {
        new BarberResponseDTO(
                id: brb?.id,
                userId: brb?.user_id,
                name: brb?.name,
                phone: brb?.phone,
                username: brb?.username,
                open_work_time: brb?.open_work_time,
                duration_cuts_time: brb?.duration_cut_time,
                description: brb?.description,
                email: brb?.email,
                enterprise_id: brb?.enterprise_id,
                roll: brb?.roll,
                is_admin: brb?.roll == EnterpriseRoll.ADMIN || EnterpriseRoll.SUPERVISOR ?: false,
                prestige: brb?.prestige ?: DEFAULT_PRESTIGE,
                facebook_username: brb?.facebook_username,
                fb_image_profile_url: brb?.fb_image_profile_url,
                inst_username: brb?.inst_username,
                inst_image_profile_url: brb?.inst_image_profile_url,
                accept_facebook_integration: brb?.accept_facebook_integration,
                accept_integration: brb?.accept_integration
        )
    }

    void logicDelete(Long barberId) {
        try {
            Barber barber = barberRepository.findById(barberId).get()
            barber.is_active = false
            userService.delete(barber.user_id)
            barberRepository.save(barber)
        } catch (Exception ex) {
            log.error("ERROR: Trying to save User due to: ${ex.getMessage()}")
            throw new CreateResourceException("Error: ${ex.getMessage()}")
        }

    }

    protected static Barber createBarber(barber) {
        Long enterpriseId = barber.enterprise_id ?: DEFAULT_ENTERPRISE
        new Barber(
                name: barber.name,
                username: barber.username,
                phone: barber.phone,
                email: barber.email,
                description: barber?.description,
                enterprise_id: enterpriseId,
                roll: barber.roll ?: EnterpriseRoll.BARBER,
                is_admin: barber?.roll == EnterpriseRoll.ADMIN || EnterpriseRoll.SUPERVISOR ?: false,
                open_work_time: barber.open_work_time ?: enterpriseOpenTime(enterpriseId),
                duration_cut_time: barber.duration_cuts_time ?: DEFAULT_DURATION_ART_CUTS_TIME,
                prestige: barber?.prestige ?: 0.0,
                facebook_username: barber?.facebook_username,
                fb_image_profile_url: barber?.fb_image_profile_url,
                inst_image_profile_url: barber?.inst_image_profile_url,
                inst_username: barber?.inst_username,
                accept_integration: barber?.accept_integration,
                accept_facebook_integration: barber?.accept_facebook_integration,
                created_on: Instant.now()
        )
    }

    protected User createUser(barber) {
        def user = userService.createUser(
                new User(
                        username: barber.username,
                        password: barber.password,
                        email: barber.email,
                        enterprise_id: barber.enterprise_id ?: 1,
                        roles: barber.roll ? UsersRoles.valueOf(barber.roll.name()) : UsersRoles.BARBER
                ))
        return userService.saveUser(user, 'CREATE')
    }

    protected static Barber updateBarber(Barber existent, newBarber) {

        // Barber Info
        existent.name = newBarber?.name
        existent.username = newBarber?.username
        existent.phone = newBarber?.phone
        existent.email = newBarber?.email
        existent.description = newBarber?.description

        // Enterprise data
        existent.enterprise_id = newBarber?.enterprise_id
        existent.roll = newBarber?.roll
        existent.is_admin = newBarber?.roll == EnterpriseRoll.ADMIN || EnterpriseRoll.SUPERVISOR ?: false
        existent.open_work_time = newBarber?.open_work_time ?: enterpriseOpenTime(existent.enterprise_id)

        // Barber social media
        existent.inst_username = newBarber?.inst_username
        existent.inst_image_profile_url = newBarber?.inst_image_profile_url
        existent.facebook_username = newBarber?.facebook_username
        existent.fb_image_profile_url = newBarber?.fb_image_profile_url

        // Barber integrations
        existent.accept_integration = newBarber.accept_integration ?: false
        existent.accept_facebook_integration = newBarber.accept_facebook_integration ?: false

        existent
    }

    protected User updateUser(barber) {
        User existentUser
        try {
            existentUser = userService.findByEmail(barber.username)
            def user = userService.updateUser(
                    new User(
                            id: existentUser.id,
                            username: barber.username,
                            password: barber.password,
                            email: barber.email,
                            enterprise_id: barber.enterprise_id
                    ))

            return userService.saveUser(user, 'UPDATE')
        } catch (Exception ex) {
            throw new CreateResourceException("User with Username: ${barber.username} Not Exists... ERROR: $ex.message",)
        }
    }

    protected String enterpriseOpenTime(enterpriseId) {
        def shopTime = shopTimeService.getShopTimeByShopTimeId(enterpriseId)
        return shopTime.workDays.toString() ?: ''
    }

    protected static Long DEFAULT_ENTERPRISE = 1
    protected static String DEFAULT_DURATION_ART_CUTS_TIME = '40 min'
    protected static Double DEFAULT_PRESTIGE = 3.5

}
