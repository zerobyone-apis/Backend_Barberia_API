package zero.our.piece.barbers.barbers_api._configuration

import com.sun.mail.util.MailSSLSocketFactory
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

import javax.mail.Authenticator
import javax.mail.PasswordAuthentication
import javax.mail.Session
import java.security.GeneralSecurityException

@Configuration
@ConfigurationProperties(prefix = "spring.mail")
@Slf4j
class MailPropertiesConfig {

    String username
    String password
    String host
    Long port
    String auth
    String enable
    String protocol
    String required

    @Value('${spring.mail.to}') String[] to


    private Properties setMailProperties() throws GeneralSecurityException {
       // MailSSLSocketFactory sslSocketToManageAllHost = new MailSSLSocketFactory()
       // sslSocketToManageAllHost.setTrustAllHosts(true)
        Properties propertiesRequiredByMail = new Properties()
        propertiesRequiredByMail.put("mail.smtp.starttls.enable", this.enable)
        propertiesRequiredByMail.put("mail.smtp.user", this.username)
        propertiesRequiredByMail.put("mail.smtp.password", this.password)
        propertiesRequiredByMail.put("mail.smtp.host", this.host)
        propertiesRequiredByMail.put("mail.smtp.port", this.port)
        propertiesRequiredByMail.put("mail.smtp.auth", this.auth)
        propertiesRequiredByMail.put("mail.smtp.to", this.to)
      //  propertiesRequiredByMail.put("spring.mail.smtp.ssl.socketFactory", sslSocketToManageAllHost)
        return propertiesRequiredByMail
    }

    @Bean
    Session getSession() {
        try {
            return Session.getInstance(setMailProperties(), new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password)
                }
            })
        } catch (GeneralSecurityException e) {
            log.error("ERROR CREATING SESSION INSTANCE: " + e.getMessage())
        }
        return Session.getDefaultInstance(setMailProperties())
    }


    @Bean
    JavaMailSenderImpl getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.host);
        mailSender.setPort(this.port.toInteger());

        mailSender.setUsername(this.username);
        mailSender.setPassword(this.password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", this.protocol);
        props.put("mail.transport.Requiere", this.required);
        props.put("mail.smtp.auth", this.auth);
        props.put("mail.smtp.starttls.enable", this.enable);
        props.put("mail.debug", "true");

        return mailSender;
    }
}
