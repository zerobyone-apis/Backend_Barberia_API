package zero.our.piece.barbers.barbers_api.enterprise.infrastructure

enum WeekDays {
    LUNES ("9:00 a 19:00"),
    MARTES("9:00 a 19:00"),
    MIERCOLES("9:00 a 19:00"),
    JUEVES("9:00 a 19:00"),
    VIERNES("9:00 a 19:00"),
    SABADO("9:00 a 19:00"),
    DOMINGO("9:00 a 14:00")

    String values;

    WeekDays(values){
        this.values = values
    }

    getWeekDays(){
        return this.name() + " " + this.values
    }
}