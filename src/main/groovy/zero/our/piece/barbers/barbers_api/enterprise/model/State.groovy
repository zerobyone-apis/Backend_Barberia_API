package zero.our.piece.barbers.barbers_api.enterprise.model

import javax.validation.constraints.NotNull

class State {

    @NotNull
    Long id

    String name

    String iso_code
}
