package zero.our.piece.barbers.barbers_api.magicCube.utils

import groovy.util.logging.Slf4j

import java.nio.file.Files
import java.nio.file.Paths

@Slf4j
class FileLoad {

    static String getConfirmBodyEmailHTML(String name, String url) {
        try {
            return new String(Files.readAllBytes(Paths.get( HTML_TEMPLATE_EMAIL_CLIENT_CONFIRM )))
                    .replace("[NAME]", name)
                    .replace("[URI]", url)
        } catch (IOException e) {
            log.error("Something went wrong -> " + e.getMessage())
        }
        return ""
    }

    //Emails templates.
    private final static  String HTML_TEMPLATE_EMAIL_CLIENT_CONFIRM = "src/main/groovy/zero/our/piece/barbers/barbers_api/magicCube/mailing/EmailServiceV2.groovy"
}
