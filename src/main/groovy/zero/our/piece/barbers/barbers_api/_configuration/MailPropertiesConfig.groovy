package zero.our.piece.barbers.barbers_api._configuration

import com.sun.mail.util.MailSSLSocketFactory
import groovy.util.logging.Slf4j
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import javax.mail.*
import java.security.GeneralSecurityException

@Configuration
@ConfigurationProperties(prefix = "mail")
@Slf4j
class MailPropertiesConfig {

    String username
    String password
    String host
    String port
    String[] to
    String auth
    String enable
    String required


    private Properties setMailProperties() throws GeneralSecurityException {
        MailSSLSocketFactory sslSocketToManageAllHost = new MailSSLSocketFactory()
        sslSocketToManageAllHost.setTrustAllHosts(true)
        Properties propertiesRequiredByMail = new Properties()
        propertiesRequiredByMail.put("mail.smtp.starttls.enable", this.enable)
        propertiesRequiredByMail.put("mail.smtp.user", this.username)
        propertiesRequiredByMail.put("mail.smtp.password", this.password)
        propertiesRequiredByMail.put("mail.smtp.host", this.host)
        propertiesRequiredByMail.put("mail.smtp.port", this.port)
        propertiesRequiredByMail.put("mail.smtp.auth", this.auth)
        propertiesRequiredByMail.put("mail.smtp.ssl.socketFactory", sslSocketToManageAllHost)
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


}
