package zero.our.piece.barbers.barbers_api.magicCube.mailing

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api.magicCube.exception.CreateResourceException

import javax.mail.MessagingException
import javax.mail.internet.MimeMessage


@Service
@Slf4j
class EmailServiceV2 implements EmailSender {


    @Value('${mail.to}') String[] to
    @Value('${mail.from}') String from

    @Autowired
    private JavaMailSender mailSender

    @Override
    @Async
    void send(String to, String subject, String body) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage()
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8")
            helper.setText(body, true)
            helper.setTo(to)
            helper.setSubject(subject)
            helper.setFrom(this.from)
            mailSender.send(mimeMessage)
        }catch(MessagingException msg){
            log.error("Failed to send email ${msg}")
            throw new CreateResourceException("Failed to send email ${msg}")
        }
    }
}

/*
todo: probar el mail sender a ver si lo manda.
      COnfigurar los puertos y demas.
      https://youtu.be/QwQuro7ekvc?t=6033
* */
