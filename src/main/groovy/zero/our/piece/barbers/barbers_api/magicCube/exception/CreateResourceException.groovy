package zero.our.piece.barbers.barbers_api.magicCube.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class CreateResourceException extends RuntimeException {

    CreateResourceException() { super() }

    CreateResourceException(String message, Throwable cause) { super(message, cause) }

    CreateResourceException(String message) { super(message) }

    CreateResourceException(Throwable cause) { super(cause) }
}
