package zero.our.piece.barbers.barbers_api.magicCube.utils

import groovy.util.logging.Slf4j

import java.nio.file.Files
import java.nio.file.Paths
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Slf4j
class FileLoad {

    static String getConfirmBodyEmailHTML(String name, String url, Long socialNumber) {
        try {
            return new String(Files.readAllBytes(Paths.get( EMAIL_TEMPLATE_CLIENT_CONFIRMATION )))
                    .replace("[USERNAME]", name)
                    .replace("[URL]", url)
                    .replace("[SOCIAL_NUMBER]", socialNumber.toString())
        } catch (IOException e) {
            log.error("Something went wrong -> " + e.getMessage())
        }
        return ""
    }

    static String convertToShortDate(Instant date) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.from(ZoneOffset.UTC))
        String formattedDate = formatter.format(date)
        return formattedDate
    }

    //Emails templates.
    private final static  String HTML_TEMPLATE_EMAIL_CLIENT_CONFIRM = "src/main/resources/mail/bundles/ConfirmationTokenEmail.html"
    private final static  String EMAIL_TEMPLATE_CLIENT_CONFIRMATION = "src/main/resources/mail/bundles/RegisterClient/EmailConfirmAccount.html"
}
