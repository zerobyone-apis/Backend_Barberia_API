package zero.our.piece.barbers.barbers_api.client.infrastructure

enum ClientType {
    NEW_CLIENT("0 - 5 Reserves"),
    FREQUENT("5 - 15 Reserves"),
    MONTHLY("15 - 25 Reserves"),
    WEEKLY("25 - 50 Reserves")

    String value

    ClientType(String value){
        this.value = value
    }

    def getValue(){
       return this.value
    }
}