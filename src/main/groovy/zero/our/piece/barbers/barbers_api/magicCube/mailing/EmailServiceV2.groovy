package zero.our.piece.barbers.barbers_api.magicCube.mailing

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api.magicCube.exception.CreateResourceException

import javax.mail.Address
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


@Service
@Slf4j
class EmailServiceV2 implements EmailSender {


    @Value('${spring.mail.to}') String[] to
    @Value('${spring.mail.from}') String from

    @Autowired
    Session session

    @Autowired
    private JavaMailSenderImpl mailSender

    @Override
    @Async
    Boolean send(String to, String subject, String body) {
        try {
            mailSender.session = session
            MimeMessage mimeMessage = mailSender.createMimeMessage()
           MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8")
            helper.setText(body, true)
            helper.setTo(to)
            helper.setSubject(subject)
            helper.setFrom(this.from)
            mailSender.send(mimeMessage)
            return Boolean.TRUE
        }catch(MessagingException msg){
            log.error("Failed to send email ${msg}")
            throw new CreateResourceException("Failed to send email ${msg}")
        }
    }
}


/* @Deprecated - hasta no ver si queda bien de la otra forma
  mimeMessage = new MimeMessage(session)
  mimeMessage.setFrom(new InternetAddress(this.from))
  mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to))
  mimeMessage.setSubject(subject)
  mimeMessage.setContent(body, "text/html")
*/

