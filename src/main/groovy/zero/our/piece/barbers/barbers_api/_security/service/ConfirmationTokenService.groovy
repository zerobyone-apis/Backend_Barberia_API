package zero.our.piece.barbers.barbers_api._security.service

import groovy.util.logging.Slf4j
import javassist.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import zero.our.piece.barbers.barbers_api._security.model.ConfirmationToken
import zero.our.piece.barbers.barbers_api._security.repository.ConfirmationTokenRepository
import zero.our.piece.barbers.barbers_api.barber.model.Barber
import zero.our.piece.barbers.barbers_api.barber.service.BarberService
import zero.our.piece.barbers.barbers_api.client.model.Client
import zero.our.piece.barbers.barbers_api.client.service.ClientService
import zero.our.piece.barbers.barbers_api.magicCube.exception.ClientException
import zero.our.piece.barbers.barbers_api.user.model.User
import zero.our.piece.barbers.barbers_api.user.service.UserService

import java.time.LocalDateTime

@Service
@Slf4j
class ConfirmationTokenService {

    @Autowired
    ConfirmationTokenRepository repository

    @Autowired
    UserService userService

    @Autowired
    BarberService barberService

    @Autowired
    ClientService clientService

    void saveConfirmationToken(ConfirmationToken token){
       try{
           repository.save(token)
       } catch(Exception e){
           log.error("Somwthing was wrong saving the current token...  error: ${e.message}")
       }
    }

    void setConfirmedAt(ConfirmationToken confirmationToken){
       try{
           confirmationToken.confirmAt = LocalDateTime.now()
           repository.save(confirmationToken)
       } catch(Exception e){
           log.error("Somwthing was wrong Updating confirm at from token... ${e.message}")
       }
    }

    ConfirmationToken getToken(String token){
       try{
           repository.findByToken(token)
       } catch(Exception e){
           log.error("Token Not Found...  error: ${e.message}")
           throw new NotFoundException("Token Not Found...  error: ${e.message}")
       }
    }

    String createToken(User user) {
        String token = UUID.randomUUID().toString()
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token: token,
                createdOn: LocalDateTime.now(),
                expiresOn: LocalDateTime.now().plusHours(2),
                user: user
        )

        // Guardamos el Token que se enviara.
        this.saveConfirmationToken(confirmationToken)
        token
    }

    @Transactional
    def verifyToken(String token) {
        ConfirmationToken confirmationToken = this.getToken(token)
        if (!confirmationToken?.createdOn) throw new ClientException("Token Not Found..")

        if (confirmationToken.confirmAt) throw new ClientException("Email already confirmed..")

        LocalDateTime expiresOn = confirmationToken.expiresOn
        if (expiresOn.isBefore(LocalDateTime.now())) throw new ClientException("Token expired")

        this.setConfirmedAt(confirmationToken)
        this.confirmUser(confirmationToken.user.email)
        log.info("User confirmed! ")

        [token: token , message: "Token validated successfully", status: "CONFIRMED"]
    }

    protected void confirmUser(String email) {
        User user = userService.findByEmail(email)
        user.is_active = Boolean.TRUE

        if (user?.barber_id) {
            Barber barber = barberService.getBarberById(user.barber_id)
            barber.is_active = Boolean.TRUE
            barberService.confirmUserBarber(barber)
        }

        if (user?.client_id) {
            Client client = clientService.findClientById(user.client_id)
            client.is_active = Boolean.TRUE
            clientService.confirmUserClient(client)

            /* TODO: Crear el flujo completo de Hairdresser.
            if ( user?.hairdresser_id ){
                Hairdresser hairdresser = hairdresserService.findById(user.hairdresser_id)
                hairdresser.is_active = Boolean.TRUE
                hairdresserService.confirmUserClient(hairdresser)
            }
            */
        }
    }



}
