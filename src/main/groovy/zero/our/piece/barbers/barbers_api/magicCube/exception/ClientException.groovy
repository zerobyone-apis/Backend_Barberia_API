package zero.our.piece.barbers.barbers_api.magicCube.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class ClientException extends RuntimeException {

    ClientException() {
        super()
    }

    ClientException(String message, Throwable cause) {
        super(message, cause)
    }

    ClientException(String message) {
        super(message)
    }

    ClientException(Throwable cause) {
        super(cause)
    }
}
