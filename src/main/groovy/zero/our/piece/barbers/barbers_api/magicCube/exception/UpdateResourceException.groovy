package zero.our.piece.barbers.barbers_api.magicCube.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class UpdateResourceException extends RuntimeException {
    UpdateResourceException() { super() }

    UpdateResourceException(String message, Throwable cause) { super(message, cause) }

    UpdateResourceException(String message) { super(message) }

    UpdateResourceException(Throwable cause) { super(cause) }
}
