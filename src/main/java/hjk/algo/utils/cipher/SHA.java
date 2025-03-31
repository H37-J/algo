package hjk.algo.utils.cipher;

import hjk.algo.utils.cipher.exception.CipherInitException;
import hjk.algo.utils.cipher.exception.CipherInstanceException;
import hjk.algo.utils.cipher.exception.CipherUtilityException;
import hjk.algo.utils.cipher.exception.InvalidArgumentException;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SHA {

    private static MessageDigest messageDigest256;

    private static MessageDigest messageDigest512;

    public SHA() throws CipherUtilityException {
        messageDigest256 = getInstance("SHA-256");
        messageDigest512 = getInstance("SHA-512");
    }

    public static void validateInputValue(String inputText, String salt, int hashSize, String encType) throws CipherUtilityException {
        if (!inputText.isEmpty() && !inputText.isBlank()) {
            if (!salt.isEmpty() && !salt.isBlank()) {
                if (hashSize == 0) {
                    throw new InvalidArgumentException("해쉬 사이즈 값이 없습니다. 숫자 256, 512 중 입력해주세요.");
                } else if (hashSize != 256 && hashSize != 512) {
                    throw new InvalidArgumentException("해당되는 해쉬 사이즈가 없습니다. 숫자 256, 512 중 입력해주세요.");
                } else if (!encType.isEmpty() && !encType.isBlank()) {
                    if (!encType.equals("BASE64") && !encType.equals("HEX")) {
                        throw new InvalidArgumentException("해당되는 인코딩 타입이 없습니다. BASE64, HEX 중 입력해주세요.");
                    }
                } else {
                    throw new InvalidArgumentException("인코딩 타입이 없습니다. BASE64, HEX 중 입력해주세요.");
                }
            } else {
                throw new InvalidArgumentException("salt 값이 null 입니다.");
            }
        } else {
            throw new InvalidArgumentException("암/복호화할 값이 없습니다.");
        }
    }

    private static MessageDigest getInstance(String algorithmName) throws CipherUtilityException {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance(algorithmName);
            return messageDigest;
        } catch (NoSuchAlgorithmException var3) {
            throw new CipherInstanceException("SHA 알고리즘을 찾을수 없습니다.");
        }
    }

    public static String encryptBASE64(String inputText, String cryptHeader, String salt, int hashSize) throws CipherUtilityException {
        MessageDigest messageDigest = null;
        if (hashSize == 256) {
            messageDigest = messageDigest256;
        } else {
            if (hashSize != 512) {
                throw new CipherInitException("Hash Size가 유효하지 않습니다.");
            }

            messageDigest = messageDigest512;
        }

        inputText = salt + inputText;
        messageDigest.update(inputText.getBytes(StandardCharsets.UTF_8));
        byte[] hashedData = messageDigest.digest();
        return cryptHeader + Base64.getEncoder().encodeToString(hashedData);
    }

    public static String encryptHEX(String inputText, String cryptHeader, String salt, int hashSize) throws CipherUtilityException {
        MessageDigest messageDigest = null;
        if (hashSize == 256) {
            messageDigest = messageDigest256;
        } else {
            if (hashSize != 512) {
                throw new CipherInitException("Hash Size가 유효하지 않습니다.");
            }

            messageDigest = messageDigest512;
        }

        inputText = salt + inputText;
        messageDigest.update(inputText.getBytes(StandardCharsets.UTF_8));
        byte[] hashedData = messageDigest.digest();
        return cryptHeader + CipherHelper.byteToHexStr(hashedData);
    }
}
