package hjk.algo.utils.cipher;

import hjk.algo.utils.cipher.exception.*;
import lombok.NoArgsConstructor;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@NoArgsConstructor
public class AES {

    private static Cipher cipher;

    public static void validateInputValue(String inputText, String secretKey, String iv, String encType) throws CipherUtilityException {
        if (!inputText.isEmpty() && !inputText.isBlank()) {
            if (!secretKey.isEmpty() && !secretKey.isBlank()) {
                if (secretKey.length() != 32) {
                    throw new InvalidArgumentException("Secret KEY 길이가 올바르지 않습니다.");
                } else if (!iv.isEmpty() && !iv.isBlank()) {
                    if (iv.length() != 16) {
                        throw new InvalidArgumentException("IV 길이가 올바르지 않습니다.");
                    } else if (!encType.isEmpty() && !encType.isBlank()) {
                        if (!encType.equals("BASE64") && !encType.equals("HEX")) {
                            throw new InvalidArgumentException("해당되는 인코딩 타입이 없습니다. BASE64, HEX 중 입력해주세요.");
                        }
                    } else {
                        throw new InvalidArgumentException("인코딩 타입이 없습니다. BASE64, HEX 중 입력해주세요.");
                    }
                } else {
                    throw new InvalidArgumentException("IV 값이 없습니다.");
                }
            } else {
                throw new InvalidArgumentException("Secret KEY 값이 없습니다.");
            }
        } else {
            throw new InvalidArgumentException("암/복호화할 값이 없습니다.");
        }
    }

    private static void initInstance() throws CipherUtilityException {
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchPaddingException var1) {
            throw new CipherInstanceException("AES Padding 매커니즘을 찾을수 없습니다.");
        } catch (NoSuchAlgorithmException var2) {
            throw new CipherInstanceException("AES 알고리즘을 찾을수 없습니다.");
        }
    }

    private static void init(int cyperMode, SecretKeySpec secretKeySpec, IvParameterSpec ivParameterSpec) throws CipherUtilityException {
        try {
            cipher.init(cyperMode, secretKeySpec, ivParameterSpec);
        } catch (InvalidAlgorithmParameterException var4) {
            throw new CipherInitException("AES 암호화에 유효한 Parameter가 아닙니다. [" + var4.getMessage() + "]");
        } catch (InvalidKeyException var5) {
            throw new CipherInitException("AES 암호화에 유효한 Secret Key가 아닙니다.");
        }
    }

    public static String encryptBASE64(String inputText, String cryptHead, String secretKey, String iv) throws CipherUtilityException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
        initInstance();
        init(1, secretKeySpec, ivParameterSpec);
        byte[] strByte = inputText.getBytes(StandardCharsets.UTF_8);
        byte[] encryptionByte = doFinal(strByte);
        return cryptHead + Base64.getEncoder().encodeToString(encryptionByte);
    }

    public static String decryptBASE64(String encryptText, String secretKey, String iv) throws CipherUtilityException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
        initInstance();
        init(2, secretKeySpec, ivParameterSpec);
        byte[] decryptionByte = Base64.getDecoder().decode(encryptText);
        return new String(doFinal(decryptionByte), StandardCharsets.UTF_8);
    }

    public static String encryptHEX(String inputText, String cryptHead, String secretKey, String iv) throws CipherUtilityException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
        initInstance();
        init(1, secretKeySpec, ivParameterSpec);
        byte[] strByte = inputText.getBytes(StandardCharsets.UTF_8);
        byte[] encryptionByte = doFinal(strByte);
        return cryptHead + CipherHelper.byteToHexStr(encryptionByte);
    }

    private static byte[] doFinal(byte[] bytes) throws CipherUtilityException {
        try {
            return cipher.doFinal(bytes);
        } catch (IllegalBlockSizeException var3) {
            throw new CipherProcessException("블럭 사이즈가 올바르지 않습니다.");
        } catch (BadPaddingException var4) {
            throw new CipherProcessException("패딩이 올바르지 않습니다.");
        }
    }

    public static String decryptHEX(String encryptText, String secretKey, String iv) throws CipherUtilityException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
        initInstance();
        init(2, secretKeySpec, ivParameterSpec);
        byte[] decryptionByte = CipherHelper.HexStrToBytes(encryptText);
        return new String(doFinal(decryptionByte), StandardCharsets.UTF_8);
    }
}
