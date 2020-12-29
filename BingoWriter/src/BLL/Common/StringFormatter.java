package BLL.Common;

import java.util.*;

public class StringFormatter {
    public boolean IsSeparatorCountValid(String s, String separator, int columnNum) {
        boolean ret = true;
        try {
            if (separator.equals("\\t")) {
                int i = s.length() - s.replace("\t", "").length();
                int count = i / "\t".length();
                if (count != columnNum - 1 || count <= 0) {
                    ret = false;
                }
            } else {
                int i = s.length() - s.replace(separator, "").length();
                int count = i / separator.length();
                if (count != columnNum - 1 || count <= 0) {
                    ret = false;
                }
            }
        } catch (Exception e) {
            ret = false;
        }
        return ret;
    }

    public List<String> SplitString(String s, String partition) {
        List<String> strList = new ArrayList<>();
        try {
            if (partition.equals("\\t")) {
                while (s.contains("\t")) {
                    strList.add(s.substring(0, s.indexOf("\t")));
                    s = s.substring(s.indexOf("\t") + "\t".length());
                }
                strList.add(s);
            } else {
                while (s.contains(partition)) {
                    strList.add(s.substring(0, s.indexOf(partition)));
                    s = s.substring(s.indexOf(partition) + partition.length());
                }
                strList.add(s);
            }
        } catch (Exception e) {
            strList = null;
        }
        return strList;
    }

    public String RemoveRedundancy(String s, String mode) {
        String destStr = "";
        try {
            if (mode.equals("HB")) {
                destStr = s.trim().replace("\0", "").replace("\\", "\\\\").replace("'", "''");
            } else if (mode.equals("ES")) {
                destStr = s.trim().replace("\\", "\\\\").replace("\"", "\\\"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return destStr;
    }

    public String ReplaceMessyCode(String s) {
        String destStr = s;
        try {
            char[] cArr = s.toCharArray();
            for (char c : cArr) {
                if (c >= 0x4e00 && c <= 0x9fa5) {
                    destStr = "";
                    break;
                }
            }
        } catch (Exception e) {
            destStr = "";
        }
        return destStr;
    }

    public String ReplaceInvalidData(String s) {
        String destStr = "";
        try {
            if (s.contains("&amp;")) {
                s = "";
            }
            if (s.contains("&#") && s.contains(";")) {
                int minus = s.indexOf(";") - s.indexOf("&#");
                if (minus > 4 && minus < 8) {
                    s = "";
                }
            }
            destStr = s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return destStr;
    }

    public String ReplaceMixedData(String s) {
        String destStr = s;
        try {
            if (s.contains("http:")) {
                destStr = s.replace("http:", "http#");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return destStr;
    }

    public String RecoverMixedData(String s) {
        String destStr = s;
        try {
            if (s.contains("http#")) {
                destStr = s.replace("http#", "http:");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return destStr;
    }
}
