package diplom.sura.rassai.config;

import java.time.Instant;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class PasswordResetLinkGenerator {
    @Value("${pass.reset.secret}")
    private String secret;

    @Value("${pass.reset.path}")
    private String path;

    @Value("${pass.reset.expiration.s}")
    private int expirationSeconds;

    public String generatePasswordResetLink(String email){
        String token = Base64.getEncoder().encodeToString((email + secret).getBytes());

        // Generate the expiration timestamp
        Instant expiration = Instant.now().plusSeconds(expirationSeconds);

        // Encode the email, token, and expiration as URL parameters
        String emailParam = "email=" + Base64.getEncoder().encodeToString(email.getBytes());
        String tokenParam = "token=" + token;
        String expirationParam = "expires=" + expiration.getEpochSecond();

        String resetLink = path + "?" + emailParam + "&" + tokenParam + "&" + expirationParam;

        return resetLink;
    }

    public String decodeEmail(String encodedEmail){
        byte[] decodedBytes = Base64.getDecoder().decode(encodedEmail);
        String email = new String(decodedBytes);
        return email;
    }
    
}
