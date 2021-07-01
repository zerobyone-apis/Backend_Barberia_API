package zero.our.piece.barbers.barbers_api.services.infrastructure
/**********  BARBERIA   ***************/
enum ServicesBarber {
    FADE                             (270),
    CLASICO                          (230),
    BARBA                            (120),
    CEJA                             (70),
    AFEITADO_CLASICO                 (160),
    BRUSHING_PROGRESIVO_BARBER       (500),
    MECHAS_BARBER                    (500),
    PLANCHADO                        (1200),
    COLORES_FANTASIA_BARBER          (800)

    Double value

    ServicesBarber(Double value) {
        this.value = value
    }

    // We have implicit get and set on groovy, but..
    def getValue() {
        this.value
    }
}