package BLL.Common;

import java.nio.charset.Charset;
import java.security.MessageDigest;

public class HashWorker {
    public String StringToHash(String plaintext, String hashType) {
        String hash = "";
        try {
            switch (hashType) {
                case "md5":
                    hash = Encrypt(plaintext, "MD5");
                    break;
                case "sha1":
                    hash = Encrypt(plaintext, "SHA-1");
                    break;
                case "sha256":
                    hash = Encrypt(plaintext, "SHA-256");
                    break;
                case "sha512":
                    hash = Encrypt(plaintext, "SHA-512");
                    break;
            }
        } catch (Exception e) {
            hash = "";
        }
        return hash;
    }

    private String Encrypt(String plaintext, String hashType) {
        MessageDigest md;
        String destStr = "";
        byte[] byteArr = plaintext.getBytes(Charset.forName("UTF-8"));
        try {
            md = MessageDigest.getInstance(hashType);
            md.update(byteArr);
            destStr = Bytes2Hex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return destStr;
    }

    private String Bytes2Hex(byte[] bytes) {
        String s = "";
        String tmp;
        for (byte b : bytes) {
            tmp = (Integer.toHexString(b & 0xFF));
            if (tmp.length() == 1) {
                s += "0";
            }
            s += tmp;
        }
        return s.toLowerCase();
    }
}
