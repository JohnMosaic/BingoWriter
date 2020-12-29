package BLL.Common;

import org.joda.time.DateTime;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeChecker {
    public boolean IsAFile(String inputFile) {
        boolean ret;
        try {
            File file = new File(inputFile);
            ret = file.exists();
        } catch (Exception e) {
            ret = false;
        }
        return ret;
    }

    public boolean IsFileEmpty(String inputFile) {
        boolean ret = false;
        try {
            File file = new File(inputFile);
            long fileSize = file.length();
            if (fileSize <= 0) {
                ret = true;
            }
        } catch (Exception e) {
            ret = true;
        }
        return ret;
    }

    public boolean IsAPositiveNumber(String number) {
        boolean ret = true;
        try {
            int a = Integer.parseInt(number);
            if (a <= 0) {
                ret = false;
            }
        } catch (Exception e) {
            ret = false;
        }
        return ret;
    }

    public boolean IsANumber(String number) {
        boolean ret = true;
        try {
            int a = Integer.parseInt(number);
            if (a < 0) {
                ret = false;
            }
        } catch (Exception e) {
            ret = false;
        }
        return ret;
    }

    public boolean IsAPort(String port) {
        boolean ret = true;
        try {
            int a = Integer.parseInt(port);
            if (a < 0 || a > 65535) {
                ret = false;
            }
        } catch (Exception e) {
            ret = false;
        }
        return ret;
    }

    public boolean IsADateTime(String datetime, List<SimpleDateFormat> sdfList) {
        boolean ret = true;
        try {
            if (datetime.contains("/")) {
                datetime = datetime.replace("/", "-");
            }
            DateTime dt = DateTime.parse(datetime);
        } catch (Exception e) {
            for (SimpleDateFormat sdf : sdfList) {
                try {
                    sdf.parse(datetime);
                    ret = true;
                    break;
                } catch (Exception ex) {
                    ret = false;
                }
            }
        }
        return ret;
    }

    public boolean IsAIP(String ip) {
        boolean ret;
        try {
            if (ip.equals("") || ip.length() < 7 || ip.length() > 15) {
                ret = false;
            } else {
                String regex = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(ip);
                ret = matcher.find();
            }
        } catch (Exception e) {
            ret = false;
        }
        return ret;
    }
}
