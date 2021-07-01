package zero.our.piece.barbers.barbers_api.services.infrastructure
/***********  PROMOS  *************/
enum Promos {
    CORTE_BARBA             (5),
    CORTE_BARBA_CEJAS       (10),
    CORTE_CEJAS             (15),
    CORTE_BLACKMASK         (10)

    Double value

    Promos(Double value) {
        this.value = value
    }

    // We have implicit get and set on groovy, but..
    def getValue() {
        this.value
    }
}