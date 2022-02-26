package zero.our.piece.barbers.barbers_api.magicCube.mailing

interface EmailSender {
    Boolean send(String to, String subject,String body)
}