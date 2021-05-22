package zero.our.piece.barbers.barbers_api.barber.service

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api.barber.infrastructure.EnterpriseRoll
import zero.our.piece.barbers.barbers_api.barber.model.Barber
import zero.our.piece.barbers.barbers_api.barber.model.DTO.BarberResponseDTO
import zero.our.piece.barbers.barbers_api.barber.repository.BarberRepository
import zero.our.piece.barbers.barbers_api.magicCube.exception.ResourceNotFoundException

@Service
@Slf4j
class BarberService {

    @Autowired
    BarberRepository barberRepository

    List<BarberResponseDTO> findAll() {
        barberRepository.findAll().each { it ->
            decoratorPatternBarber(it)
        } as List<BarberResponseDTO>
    }

    BarberResponseDTO findById(Long id) {
        def foundUser = barberRepository.findById(id).get()
        if (!foundUser?.id) throw new ResourceNotFoundException("USER_NOT_FOUND")

        return decoratorPatternBarber(foundUser)
    }

    BarberResponseDTO findByUserId(Long user_id) {
        Barber barber = barberRepository.findByUserId(user_id)
        if (!barber?.id) throw new ResourceNotFoundException("Barber with this User ID Not found: " + user_id)

        return decoratorPatternBarber(barber)
    }

    protected static BarberResponseDTO decoratorPatternBarber(Barber brb) {
        new BarberResponseDTO(
                id: brb?.id,
                name: brb?.name,
                username: brb?.username,
                open_work_time: brb?.open_work_time,
                duration_cuts_time: brb?.duration_cut_time,
                description: brb?.description,
                email: brb?.email,
                enterprise_id: brb?.enterprise_id,
                roll: brb?.roll,
                is_admin: brb?.roll == EnterpriseRoll.ADMIN ?: false,
                prestige: brb?.prestige,
                facebook_username: brb?.facebook_username,
                fb_image_profile_url: brb?.fb_image_profile_url,
                inst_username: brb?.inst_username,
                inst_image_profile_url: brb?.inst_image_profile_url,
        )
    }


}
