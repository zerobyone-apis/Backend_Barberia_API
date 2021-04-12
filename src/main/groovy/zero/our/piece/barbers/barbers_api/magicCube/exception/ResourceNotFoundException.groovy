package zero.our.piece.barbers.barbers_api.magicCube.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResourceNotFoundException extends RuntimeException {

    ResourceNotFoundException() { super() }

    ResourceNotFoundException(String message, Throwable cause) { super(message, cause) }

    ResourceNotFoundException(String message) { super(message) }

    ResourceNotFoundException(Throwable cause) { super(cause) }
}
