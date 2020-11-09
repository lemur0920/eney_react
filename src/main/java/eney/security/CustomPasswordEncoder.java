package eney.security;


import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class CustomPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence value) {
        String encryptData = "";

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");

            sha.update(value.toString().getBytes());

            byte[] digest = sha.digest();
            for (int i=0; i<digest.length; i++) {
                encryptData += Integer.toHexString(digest[i] & 0xFF).toUpperCase();
            }
        }
        catch(NoSuchAlgorithmException e) {

            System.out.println(e);

        }

        return encryptData;
    }
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String rawEncoded = encode(rawPassword);
        return Objects.equals(rawEncoded, encodedPassword);
    }

}