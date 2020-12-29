package BLL.Common;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import java.nio.charset.Charset;

public class Base64Worker {
    public String Base64Encode(String s) {
        String destStr = "";
        try {
            byte[] byteArr = s.getBytes(Charset.forName("UTF-8"));
            destStr = new BASE64Encoder().encode(byteArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return destStr;
    }

    public String Base64Decode(String s) {
        String destStr = "";
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] byteArr = base64Decoder.decodeBuffer(s);
            destStr = new String(byteArr, Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return destStr;
    }
}
