package zero.our.piece.barbers.barbers_api.magicCube.mailing

import groovy.util.logging.Slf4j
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api._configuration.MailPropertiesConfig
import zero.our.piece.barbers.barbers_api.magicCube.exception.CreateResourceException

import javax.mail.*
import javax.mail.internet.*
import java.nio.file.Files
import java.nio.file.Paths

@Service
@Slf4j
class SendMailService {

    @Autowired
    Session mailSession
    @Autowired
    MailPropertiesConfig mailProperties

    @Value('${mail.content.subject}')
    String subject
    @Value('${mail.contact.recipient.to}')
    String contactMail
    @Value('${mail.content.description}')
    String description


    void notifyAndSendEmail(String detailsEmails, String username, String dateTimesReserve, String clientEmail) {
        log.info("STARTING SEND EMAIL PROCESS (> 0 _ 0 )> . . .  ")
        Optional<MimeMessage> mailContent =
                buildContentMail(detailsEmails, description, subject, username, dateTimesReserve, clientEmail)
        mailContent.ifPresent(this.&connectAndSendEmail)
    }

    void contactEmail(String description, String subjectMessage, String emailFrom) {
        log.info("STARTING SEND EMAIL PROCESS (> 0 _ 0 )> . . .  ")
        try {
            log.info("CREATING CONTENT EMAIL . . . ")
            MimeMessage mailContent = new MimeMessage(mailSession)
            mailContent.setSubject(subjectMessage)
            setListOfAddresses(mailContent, emailFrom, true)

            log.info("BUILDING EMAIL CONTENT BY MIME BODY PART ( W_W )? . . .  ")
            MimeBodyPart partOfContentText = new MimeBodyPart()
            partOfContentText.setContent(description, "text/plain")

            log.info("BUILDING THE MULTIPART TO THE BODY EMAIL . . .")
            Multipart multipartOfMail = new MimeMultipart()
            multipartOfMail.addBodyPart(partOfContentText)

            log.info("ADDING THE MULTIPART TO THE EMAIL . . .")
            mailContent.setContent(multipartOfMail)

            connectAndSendEmail(mailContent)
        } catch (MessagingException e) {
            log.error("Error creating and sending the contact email " + e.getMessage())
            throw new CreateResourceException("Something was wrong sending this email error message: " + e.getLocalizedMessage())
        }

    }

    private Optional<MimeMessage> buildContentMail(String detailsEmails,String description, String subject, String username,
                                                   String dateTimesReserve, String clientEmail) {
        try {
            log.info("BUILDING EMAIL CONTENT BY MULTIPARTS BODY ( W_W )? . . .  ")
            MimeMessage mailContent = buildContentTextMail(subject, detailsEmails, clientEmail)

            // Se crea la part que contendra el texto de las reserva con la descripcion.
            // Podria ir perfectamente el detalle de la reserva.
            log.info("CREATING MULTIPART OF EMAIL TEXT")
            MimeBodyPart partOfContentText =
                    getContentPartAddTheText(detailsEmails, description, username, dateTimesReserve)


            // TODO:Aca se crea la el archivo a enviar en caso de enviar uno.
            //  -> MimeBodyPart partOfContentFile = getContentPartAddTheFile();
            //log.info("CREATING MULTIPART OF EMAIL TEXT");
            //DataSource swapSource = new ByteArrayDataSource(csvFile, "text/csv");
            //partOfContentFile.setDataHandler(new DataHandler(swapSource));

            log.info("ADDING THE MULTIPART TO THE BODY EMAIL . . .")
            Multipart multipartOfMail = new MimeMultipart()
            log.info("Multipart: CONTENT TEXT")
            multipartOfMail.addBodyPart(partOfContentText)
            //log.info("Multipart: CONTENT FILE");
            //multipartOfMail.addBodyPart(partOfContentFile);

            mailContent.setContent(multipartOfMail)
            return Optional.of(mailContent)
        } catch (MessagingException ex) {
            log.error("ERROR SENDING EMAIL INDEX OF ERROR: " + ex.getMessage())
        }
        return Optional.empty()
    }

    private void connectAndSendEmail(MimeMessage mailContent) {
        try {
            log.info("SENDING EMAIL (>-e_-e)>  . . .  ")
            Transport accountAction = mailSession.getTransport("smtp")
            accountAction.connect(mailProperties.getHost(), mailProperties.getUsername(), mailProperties.getPassword())
            accountAction.sendMessage(mailContent, mailContent.getAllRecipients())
            accountAction.close()
            log.info("EMAIL SENDED SUCCESSFULLY!! \\( ^ - ^ )/  ")
        } catch (MessagingException me) {
            log.error("ERROR SENDING EMAIL: " + me.getMessage())
        }
    }

    private MimeMessage buildContentTextMail(String subject, String detailsEmail, String clientEmail) throws MessagingException {
        log.info("CREATING CONTENT EMAIL . . . ")
        MimeMessage mailContent = new MimeMessage(mailSession)
        mailContent.setSubject(subject)
        //mailContent.setText(detailsEmail);
        setListOfAddresses(mailContent, clientEmail, true)
        return mailContent
    }

    private static MimeBodyPart getContentPartAddTheFile() throws MessagingException {
        log.info("CREATING CONTENT FILE FOR EMAIL . . . ")
        MimeBodyPart partOfContentFile = new MimeBodyPart()

        partOfContentFile.setFileName("TEST-REPORT.csv")
        return partOfContentFile
    }

    private MimeBodyPart getContentPartAddTheText(String detailsEmail, String description, String username, String dateTimeReserve) {
        MimeBodyPart partOfContentText = new MimeBodyPart()
        String htmlTemplate = getReservesFromJson(HTML_TEMPLATE_EMAIL_RESERVE_CONFIRM)
                .replace("[USERNAME]", username)
                .replace("[DETALLES]", detailsEmail)
                .replace("[DATETIME_RESERVE]", dateTimeReserve)

        try {
            partOfContentText.setContent(htmlTemplate, "text/html")
        } catch (MessagingException e) {
            log.error("ERROR FROM ADDRESS EXCEPTION INDEX OF ERROR: " + e.getMessage())
        }
        return partOfContentText
    }

    private void setListOfAddresses(MimeMessage mailContent, String clientEmail, boolean isContactMail) {
        try {
            if (isContactMail) {
                setListOfInternetAddresses(clientEmail, mailContent, true)
            } else {
                setListOfInternetAddresses("", mailContent, false)
            }
        } catch (AddressException ex) {
            log.error("ERROR FROM ADDRESS EXCEPTION INDEX OF ERROR: " + ex.getMessage())
        } catch (MessagingException ex) {
            log.error("ERROR SENDING EMAIL INDEX OF ERROR: " + ex.getMessage())
        }
    }

    private void setListOfInternetAddresses(String clientEmail, MimeMessage mailContent, boolean isContactMail) throws MessagingException {
        mailContent.setFrom(new InternetAddress(mailProperties.username))

        // New Array Emails
        if (isContactMail) {
            InternetAddress client = new InternetAddress(clientEmail)
            InternetAddress enterprise = new InternetAddress(contactMail)
            mailContent.setRecipient(Message.RecipientType.TO, client)
            log.info("Adding recipient To sent -> " + client.getAddress())
            mailContent.setRecipient(Message.RecipientType.CC, enterprise)
            log.info("Adding recipient To sent -> " + enterprise.getAddress())

        } else {
            InternetAddress[] listAddresses = new InternetAddress[mailProperties.to.length]
            for (int i = 0; i < mailProperties.to.length; i++) {
                listAddresses[i] = new InternetAddress(mailProperties.to[i])
                log.info("Adding mail To sent -> " + mailProperties.to[i])
            }
            for (int i = 0; i < listAddresses.length; i++) {
                mailContent.setRecipient(Message.RecipientType.TO, listAddresses[i])
                log.info("Adding recipient To sent -> " + mailProperties.to[i])
            }
        }
    }

    private static String getReservesFromJson(String src) {
        try {
            return new String(Files.readAllBytes(Paths.get(src)))
        } catch (IOException e) {
            log.error("Something went wrong -> " + e.getMessage())
        }
        return ""
    }

    private final String HTML_TEMPLATE_EMAIL_RESERVE_CONFIRM = "src/main/resources/emailTemplates/EmailTemplateReservesConfirm.html"

}
