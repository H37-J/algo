package hjk.algo.utils.cipher;

import hjk.algo.utils.cipher.exception.CipherUtilityException;
import hjk.algo.utils.cipher.exception.InvalidArgumentException;
import org.mindrot.jbcrypt.BCrypt;
public class Bcrypt {

    public Bcrypt() {

    }

    public static void validateInputValue(String inputText, int strength) throws CipherUtilityException {
        if (!inputText.isEmpty() && !inputText.isBlank()) {
            if (strength == 0) {
                throw new InvalidArgumentException("strength 값이 0 입니다.");
            }
        } else {
            throw new InvalidArgumentException("암/복호화할 값이 없습니다.");
        }
    }

    public static String encrypt(String inputText, String cryptHead, int strength) {
        String hashed = BCrypt.hashpw(inputText, BCrypt.gensalt(strength));
        return cryptHead + hashed;
    }

    public static boolean match(String rawText, String encText) {
        return BCrypt.checkpw(rawText, encText);
    }
}
