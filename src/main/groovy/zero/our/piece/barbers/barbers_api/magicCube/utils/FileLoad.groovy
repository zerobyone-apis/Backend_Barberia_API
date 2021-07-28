package zero.our.piece.barbers.barbers_api.magicCube.utils

import groovy.util.logging.Slf4j

import java.nio.file.Files
import java.nio.file.Paths

@Slf4j
class FileLoad {

    static String getConfirmBodyEmailHTML(String name, String url) {
        try {
            return new String(Files.readAllBytes(Paths.get( EMAIL_TEMPLATE_CLIENT_CONFIRMATION )))
                    .replace("[USERNAME]", name)
                    .replace("[URL]", url)
        } catch (IOException e) {
            log.error("Something went wrong -> " + e.getMessage())
        }
        return ""
    }

    //Emails templates.
    private final static  String HTML_TEMPLATE_EMAIL_CLIENT_CONFIRM = "src/main/resources/mail/bundles/ConfirmationTokenEmail.html"
    private final static  String EMAIL_TEMPLATE_CLIENT_CONFIRMATION = "src/main/resources/mail/bundles/RegisterClient/EmailConfirmAccount.html"
}
