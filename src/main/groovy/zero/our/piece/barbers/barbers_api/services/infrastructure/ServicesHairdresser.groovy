package zero.our.piece.barbers.barbers_api.services.infrastructure

/**********   PELUQUERIA  *************/
enum ServicesHairdresser {
    CORTE                           (250),
    LAVADO                          (50),
    BRUSHING                        (200),
    DEPILACION                      (70),
    BOTOX                           (690),
    HIDROCAUTERIZACION              (590),
    BRUSHING_PROGRESIVO             (1200),
    CLARITOS                        (900),
    MECHAS                          (900),
    REFLEJOS                        (900),
    BALAYAGE                        (1600),
    CALIFORNIANAS                   (1600),
    COLOR                           (600),
    COLORES_FANTASIA                (600),
    APLICACION                      (280),
    TRENZAS                         (200)

    Double value

    ServicesHairdresser(Double value) {
        this.value = value
    }

    // We have implicit get and set on groovy, but..
    def getValue() {
        this.value
    }
}