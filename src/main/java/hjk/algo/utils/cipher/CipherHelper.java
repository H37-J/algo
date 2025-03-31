package hjk.algo.utils.cipher;

public class CipherHelper {
    public CipherHelper() {
    }

    public static String[] splitHeader(String fullText) {
        String cryptHeader = null;
        String encryptedText = null;
        int headerIndex = fullText.indexOf(125);
        if (headerIndex == 9) {
            cryptHeader = fullText.substring(0, 10);
            encryptedText = fullText.substring(10);
        } else {
            cryptHeader = "";
            encryptedText = fullText;
        }

        return new String[]{cryptHeader, encryptedText};
    }

    public static int getStrength(String cryptHeader) {
        String strStrength = cryptHeader.substring(7, 9);
        return Integer.parseInt(strStrength);
    }

    public static int getCryptIdx(String cryptHeader, int current) {
        return cryptHeader.isEmpty() ? current : Integer.parseInt(cryptHeader.substring(8, 9));
    }

    public static String byteToHexStr(byte[] data) {
        StringBuilder sb = new StringBuilder();
        byte[] var2 = data;
        int var3 = data.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            sb.append(Integer.toString((b & 255) + 256, 16).substring(1).toUpperCase());
        }

        return sb.toString();
    }

    public static byte[] HexStrToBytes(String strHex) {
        byte[] bytes = new byte[strHex.length() / 2];

        for(int count = 0; count < strHex.length(); count += 2) {
            bytes[count / 2] = (byte)Integer.parseInt(strHex.substring(count, count + 2), 16);
        }

        return bytes;
    }
}
