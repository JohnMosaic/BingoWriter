package BLL.Common;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class LogManager {
    public void LogRecord(String logFilePath, String inputFileName, String msg, String dbName) {
        String logFileName = "";
        if (logFilePath.contains("\\")) {
            logFileName = logFilePath + "\\" + inputFileName + ".log";
        } else if (logFilePath.contains("/")) {
            logFileName = logFilePath + "/" + inputFileName + ".log";
        }
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try {
            fos = new FileOutputStream(logFileName, true);
            osw = new OutputStreamWriter(fos, Charset.forName("UTF-8"));
            String info = "[" + dbName + "]" + new TimeMarker().GetCurrentDateTime() + " " + msg;
            osw.write(info + "\n");
            osw.flush();
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (osw != null) {
                    osw.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void InvalidDataRecord(String invalidDataFilePath, String inputFileName, String invalidData) {
        String invalidDataFileName = "";
        if (invalidDataFilePath.contains("\\")) {
            invalidDataFileName = invalidDataFilePath + "\\" + inputFileName + ".invalid";
        } else if (invalidDataFilePath.contains("/")) {
            invalidDataFileName = invalidDataFilePath + "/" + inputFileName + ".invalid";
        }
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try {
            fos = new FileOutputStream(invalidDataFileName, true);
            osw = new OutputStreamWriter(fos, Charset.forName("UTF-8"));
            osw.write(invalidData + "\n");
            osw.flush();
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (osw != null) {
                    osw.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void RemoveOldLogFile(String logFilePath, String inputFileName) {
        String logFileName = "";
        if (logFilePath.contains("\\")) {
            logFileName = logFilePath + "\\" + inputFileName + ".log";
        } else if (logFilePath.contains("/")) {
            logFileName = logFilePath + "/" + inputFileName + ".log";
        }
        try {
            File file = new File(logFileName);
            if (file.exists()) {
                int res = JOptionPane.showConfirmDialog(
                        null,
                        "Do you want to delete the old log file?",
                        "Tips",
                        JOptionPane.YES_NO_OPTION
                );
                if (res == JOptionPane.YES_OPTION) {
                    boolean b = file.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void RemoveOldInvalidDataFile(String invalidDataFilePath, String inputFileName) {
        String invalidDataFileName = "";
        if (invalidDataFilePath.contains("\\")) {
            invalidDataFileName = invalidDataFilePath + "\\" + inputFileName + ".invalid";
        } else if (invalidDataFilePath.contains("/")) {
            invalidDataFileName = invalidDataFilePath + "/" + inputFileName + ".invalid";
        }
        try {
            File file = new File(invalidDataFileName);
            if (file.exists()) {
                int res = JOptionPane.showConfirmDialog(
                        null,
                        "Do you want to delete the old invalid data file?",
                        "Tips",
                        JOptionPane.YES_NO_OPTION
                );
                if (res == JOptionPane.YES_OPTION) {
                    boolean b = file.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
