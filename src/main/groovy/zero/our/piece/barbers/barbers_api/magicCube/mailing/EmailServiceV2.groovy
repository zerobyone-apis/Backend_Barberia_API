package zero.our.piece.barbers.barbers_api.magicCube.mailing

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api.magicCube.exception.CreateResourceException

import javax.mail.MessagingException
import javax.mail.internet.MimeMessage
import java.nio.file.Files
import java.nio.file.Paths

@Service
@Slf4j
@EnableAsync
class EmailServiceV2 implements EmailSender{

    @Value('${mail.content.subject}')
    String subject

    @Value('${mail.to}') String[] to
    @Value('${mail.from}') String from

    @Value('${mail.content.description}')
    String description

    private JavaMailSender mailSender

    @Override
    @Async
    void send(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage()
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8")
            helper.setText(email, true)
            helper.setTo(to)
            helper.setSubject("Confirm your email")
            helper.setFrom(this.from)
            mailSender.send(mimeMessage)
        }catch(MessagingException msg){
            log.error("Failed to send email ${msg}")
            throw new CreateResourceException("Failed to send email ${msg}")
        }
    }
    String getReservesFromJson(String name, String url) {
        try {
            return new String(Files.readAllBytes(Paths.get( HTML_TEMPLATE_EMAIL_CLIENT_CONFIRM )))
                    .replace("[NAME]", name)
                    .replace("[URI]", url)
        } catch (IOException e) {
            log.error("Something went wrong -> " + e.getMessage())
        }
        return ""
    }

    private final String HTML_TEMPLATE_EMAIL_CLIENT_CONFIRM = "src/main/groovy/zero/our/piece/barbers/barbers_api/magicCube/mailing/EmailServiceV2.groovy"
}

/*
todo: probar el mail sender a ver si lo manda.
      COnfigurar los puertos y demas.
      https://youtu.be/QwQuro7ekvc?t=6033
* */
