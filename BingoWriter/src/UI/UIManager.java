package UI;

import BLL.Common.TimeMarker;
import BLL.Config.ConfigManager;
import BLL.Common.LogManager;
import Model.ModelConfigInfo;
import org.joda.time.DateTime;

import javax.swing.*;
import java.awt.*;

public class UIManager {
    private ProgressPanel glassPane = null;
    private TimeMarker timeMarker = new TimeMarker();

    public Point GetCenterLocationPoint(JFrame frame) {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        Point p = new Point();
        p.x = screenSize.width / 2 - frame.getWidth() / 2;
        p.y = screenSize.height / 2 - frame.getHeight() / 2;
        return p;
    }

    public void AddProgressPanel(JFrame frame, String msg) {
        glassPane = new ProgressPanel(msg);
        frame.setGlassPane(glassPane);
        frame.pack();
        glassPane.start();
    }

    public void RemoveProgressPanel() {
        if (glassPane != null) {
            glassPane.stop();
        }
    }

    public void UpdateTaskInfo(BingoWriterUI ui) {
        try {
            long validCount = ui.modelTaskInfo.getValidCount();
            long invalidCount = ui.modelTaskInfo.getInvalidCount();
            ui.lblValidateVal.setText("Valid " + String.valueOf(validCount) + " / Invalid " + String.valueOf(invalidCount));
            ui.lblElapsedTimeVal.setText(String.valueOf(timeMarker.GetDateTimeMillisMinus(
                    ui.modelTaskInfo.getDtStart(),
                    DateTime.now()) / 1000) + " s"
            );
            if (ui.modelConfigInfo.getHBaseTbHashChecked() || !ui.lblHBaseTbHashElapsedTimeVal.getText().equals("0 s")) {
                long hBaseTbHashSCount = ui.modelTaskInfo.getHBaseTbHashSucceededCount();
                long hBaseTbHashFCount = ui.modelTaskInfo.getHBaseTbHashFailedCount();
                long hBaseTbHashElapsedTime = ui.modelTaskInfo.getHBaseTbHashElapsedTime();
                int hBaseTbHashAverageRate = 0;
                if (hBaseTbHashElapsedTime > 0) {
                    hBaseTbHashAverageRate = (int) (((hBaseTbHashSCount + hBaseTbHashFCount) / (double) hBaseTbHashElapsedTime) * 1000);
                }
                int hBaseTbHashMaxRate = ui.modelTaskInfo.getHBaseTbHashMaxRate();
                if (hBaseTbHashAverageRate > hBaseTbHashMaxRate) {
                    hBaseTbHashMaxRate = hBaseTbHashAverageRate;
                    ui.modelTaskInfo.setHBaseTbHashMaxRate(hBaseTbHashMaxRate);
                }
                ui.lblHBaseTbHashValidateVal.setText("Succeeded " + String.valueOf(hBaseTbHashSCount) + " / Failed " + String.valueOf(hBaseTbHashFCount));
                ui.lblHBaseTbHashElapsedTimeVal.setText(String.valueOf(hBaseTbHashElapsedTime / 1000) + " s");
                ui.lblHBaseTbHashMaxRateVal.setText(String.valueOf(hBaseTbHashMaxRate) + " /s");
                ui.lblHBaseTbHashAverageRateVal.setText(String.valueOf(hBaseTbHashAverageRate) + " /s");
            }
            if (ui.modelConfigInfo.getESTbHashChecked() || !ui.lblESTbHashElapsedTimeVal.getText().equals("0 s")) {
                long esTbHashSCount = ui.modelTaskInfo.getESTbHashSucceededCount();
                long esTbHashFCount = ui.modelTaskInfo.getESTbHashFailedCount();
                long esTbHashElapsedTime = ui.modelTaskInfo.getESTbHashElapsedTime();
                int esTbHashAverageRate = 0;
                if (esTbHashElapsedTime > 0) {
                    esTbHashAverageRate = (int) (((esTbHashSCount + esTbHashFCount) / (double) esTbHashElapsedTime) * 1000);
                }
                int esTbHashMaxRate = ui.modelTaskInfo.getESTbHashMaxRate();
                if (esTbHashAverageRate > esTbHashMaxRate) {
                    esTbHashMaxRate = esTbHashAverageRate;
                    ui.modelTaskInfo.setESTbHashMaxRate(esTbHashMaxRate);
                }
                ui.lblESTbHashValidateVal.setText("Succeeded " + String.valueOf(esTbHashSCount) + " / Failed " + String.valueOf(esTbHashFCount));
                ui.lblESTbHashElapsedTimeVal.setText(String.valueOf(esTbHashElapsedTime / 1000) + " s");
                ui.lblESTbHashMaxRateVal.setText(String.valueOf(esTbHashMaxRate) + " /s");
                ui.lblESTbHashAverageRateVal.setText(String.valueOf(esTbHashAverageRate) + " /s");
            }
            if (ui.modelConfigInfo.getHBaseTbBingoChecked() || !ui.lblHBaseTbBingoElapsedTimeVal.getText().equals("0 s")) {
                long hBaseTbBingoSCount = ui.modelTaskInfo.getHBaseTbBingoSucceededCount();
                long hBaseTbBingoFCount = ui.modelTaskInfo.getHBaseTbBingoFailedCount();
                long hBaseTbBingoElapsedTime = ui.modelTaskInfo.getHBaseTbBingoElapsedTime();
                int hBaseTbBingoAverageRate = 0;
                if (hBaseTbBingoElapsedTime > 0) {
                    hBaseTbBingoAverageRate = (int) (((hBaseTbBingoSCount + hBaseTbBingoFCount) / (double) hBaseTbBingoElapsedTime) * 1000);
                }
                int hBaseTbBingoMaxRate = ui.modelTaskInfo.getHBaseTbBingoMaxRate();
                if (hBaseTbBingoAverageRate > hBaseTbBingoMaxRate) {
                    hBaseTbBingoMaxRate = hBaseTbBingoAverageRate;
                    ui.modelTaskInfo.setHBaseTbBingoMaxRate(hBaseTbBingoMaxRate);
                }
                ui.lblHBaseTbBingoValidateVal.setText("Succeeded " + String.valueOf(hBaseTbBingoSCount) + " / Failed " + String.valueOf(hBaseTbBingoFCount));
                ui.lblHBaseTbBingoElapsedTimeVal.setText(String.valueOf(hBaseTbBingoElapsedTime / 1000) + " s");
                ui.lblHBaseTbBingoMaxRateVal.setText(String.valueOf(hBaseTbBingoMaxRate) + " /s");
                ui.lblHBaseTbBingoAverageRateVal.setText(String.valueOf(hBaseTbBingoAverageRate) + " /s");
            }
            if (ui.modelConfigInfo.getESTbBingoChecked() || !ui.lblESTbBingoElapsedTimeVal.getText().equals("0 s")) {
                long esTbBingoSCount = ui.modelTaskInfo.getESTbBingoSucceededCount();
                long esTbBingoFCount = ui.modelTaskInfo.getESTbBingoFailedCount();
                long esTbBingoElapsedTime = ui.modelTaskInfo.getESTbBingoElapsedTime();
                int esTbBingoAverageRate = 0;
                if (esTbBingoElapsedTime > 0) {
                    esTbBingoAverageRate = (int) (((esTbBingoSCount + esTbBingoFCount) / (double) esTbBingoElapsedTime) * 1000);
                }
                int esTbBingoMaxRate = ui.modelTaskInfo.getESTbBingoMaxRate();
                if (esTbBingoAverageRate > esTbBingoMaxRate) {
                    esTbBingoMaxRate = esTbBingoAverageRate;
                    ui.modelTaskInfo.setESTbBingoMaxRate(esTbBingoMaxRate);
                }
                ui.lblESTbBingoValidateVal.setText("Succeeded " + String.valueOf(esTbBingoSCount) + " / Failed " + String.valueOf(esTbBingoFCount));
                ui.lblESTbBingoElapsedTimeVal.setText(String.valueOf(esTbBingoElapsedTime / 1000) + " s");
                ui.lblESTbBingoMaxRateVal.setText(String.valueOf(esTbBingoMaxRate) + " /s");
                ui.lblESTbBingoAverageRateVal.setText(String.valueOf(esTbBingoAverageRate) + " /s");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ReloadConfigInfo(BingoWriterUI ui) {
        try {
            ConfigManager configManager = new ConfigManager();
            if (configManager.IsConfigFileExist()) {
                ModelConfigInfo mci = configManager.ConfigRead();
                if (mci != null) {
                    String zkHost = mci.getZKHost();
                    String zkPort = String.valueOf(mci.getZKPort());
                    boolean isZKChecked = mci.getZKChecked();
                    String saltBuckets = String.valueOf(mci.getSaltBuckets());
                    String esHost = mci.getESHost();
                    String esPort = String.valueOf(mci.getESPort());
                    boolean isESChecked = mci.getESChecked();
                    String esCluster = mci.getESCluster();
                    String separator = mci.getSeparator();
                    String cacheNumber = String.valueOf(mci.getCacheNumber());
                    String columns = "";
                    for (String s : mci.getColumnsList()) {
                        columns += (s + ",");
                    }
                    if (columns.contains(",")) {
                        columns = columns.substring(0, columns.lastIndexOf(","));
                    }
                    String columnNumber = String.valueOf(mci.getColumnNumber());
                    boolean isMessyCodeChecked = mci.getMessyCodeChecked();
                    boolean isInvalidDataChecked = mci.getInvalidDataChecked();
                    boolean isMixedDataChecked = mci.getMixedDataChecked();
                    String indexTags = "";
                    for (int i : mci.getIndexTagsList()) {
                        indexTags += (String.valueOf(i) + ",");
                    }
                    if (indexTags.contains(",")) {
                        indexTags = indexTags.substring(0, indexTags.lastIndexOf(","));
                    }
                    String inputFile = mci.getInputFile();
                    String tbHash = mci.getTbHash();
                    boolean isHBaseTbHashChecked = mci.getHBaseTbHashChecked();
                    boolean isESTbHashChecked = mci.getESTbHashChecked();
                    int tbHashIndexNumber = mci.getTbHashIndexNumber();
                    String tbHashColumns = "";
                    for (String s : mci.getTbHashColumnsList()) {
                        tbHashColumns += (s + ",");
                    }
                    if (tbHashColumns.contains(",")) {
                        tbHashColumns = tbHashColumns.substring(0, tbHashColumns.lastIndexOf(","));
                    }
                    String tbHashValues = "";
                    for (String s : mci.getTbHashValuesList()) {
                        tbHashValues += (s + ",");
                    }
                    if (tbHashValues.contains(",")) {
                        tbHashValues = tbHashValues.substring(0, tbHashValues.lastIndexOf(","));
                    }
                    String tbBingo = mci.getTbBingo();
                    boolean isHBaseTbBingoChecked = mci.getHBaseTbBingoChecked();
                    boolean isESTbBingoChecked = mci.getESTbBingoChecked();
                    int tbBingoIndexNumber = mci.getTbBingoIndexNumber();
                    String tbBingoColumns = "";
                    for (String s : mci.getTbBingoColumnsList()) {
                        tbBingoColumns += (s + ",");
                    }
                    if (tbBingoColumns.contains(",")) {
                        tbBingoColumns = tbBingoColumns.substring(0, tbBingoColumns.lastIndexOf(","));
                    }
                    String tbBingoValues = "";
                    for (String s : mci.getTbBingoValuesList()) {
                        tbBingoValues += (s + ",");
                    }
                    if (tbBingoValues.contains(",")) {
                        tbBingoValues = tbBingoValues.substring(0, tbBingoValues.lastIndexOf(","));
                    }
                    int hBaseMaxThreadNumber = mci.getHBaseMaxThreads();
                    int hBaseBatchNumber = mci.getHBaseBatchNumber();
                    int esMaxThreadNumber = mci.getESMaxThreads();
                    int esBatchNumber = mci.getESBatchNumber();
                    ui.tfdZKHost.setText(zkHost);
                    ui.tfdZKPort.setText(zkPort);
                    ui.ckbZK.setSelected(isZKChecked);
                    ui.tfdSaltBuckets.setText(saltBuckets);
                    ui.tfdESHost.setText(esHost);
                    ui.tfdESPort.setText(esPort);
                    ui.ckbES.setSelected(isESChecked);
                    ui.tfdESCluster.setText(esCluster);
                    ui.tfdSeparator.setText(separator);
                    ui.tfdCacheNum.setText(cacheNumber);
                    ui.tfdColumns.setText(columns);
                    ui.tfdColumnNum.setText(columnNumber);
                    ui.ckbMessyCode.setSelected(isMessyCodeChecked);
                    ui.ckbInvalidData.setSelected(isInvalidDataChecked);
                    ui.ckbMixedData.setSelected(isMixedDataChecked);
                    ui.tfdIndexTag.setText(indexTags);
                    ui.tfdInputFile.setText(inputFile);
                    ui.tfdTbHash.setText(tbHash);
                    ui.ckbHBaseTbHash.setSelected(isHBaseTbHashChecked);
                    ui.ckbESTbHash.setSelected(isESTbHashChecked);
                    ui.cbbTbHashIndexNum.setSelectedIndex(configManager.GetIndexFromIndexNumber(tbHashIndexNumber));
                    ui.tfdTbHashColumns.setText(tbHashColumns);
                    ui.tfdTbHashValues.setText(tbHashValues);
                    ui.tfdTbBingo.setText(tbBingo);
                    ui.ckbHBaseTbBingo.setSelected(isHBaseTbBingoChecked);
                    ui.ckbESTbBingo.setSelected(isESTbBingoChecked);
                    ui.cbbTbBingoIndexNum.setSelectedIndex(configManager.GetIndexFromIndexNumber(tbBingoIndexNumber));
                    ui.tfdTbBingoColumns.setText(tbBingoColumns);
                    ui.tfdTbBingoValues.setText(tbBingoValues);
                    ui.cbbHBaseMaxThreads.setSelectedIndex(configManager.GetIndexFromThreadNumber(hBaseMaxThreadNumber));
                    ui.cbbHBaseBatchNum.setSelectedIndex(configManager.GetIndexFromBatchNumber(hBaseBatchNumber));
                    ui.cbbESMaxThreads.setSelectedIndex(configManager.GetIndexFromThreadNumber(esMaxThreadNumber));
                    ui.cbbESBatchNum.setSelectedIndex(configManager.GetIndexFromBatchNumber(esBatchNumber));
                    LogManager logManager = new LogManager();
                    String logFilePath = "";
                    String inputFileName = "";
                    if (inputFile.contains("\\")) {
                        logFilePath = inputFile.substring(0, inputFile.lastIndexOf("\\"));
                        inputFileName = inputFile.substring(inputFile.lastIndexOf("\\") + 1);
                    } else if (inputFile.contains("/")) {
                        logFilePath = inputFile.substring(0, inputFile.lastIndexOf("/"));
                        inputFileName = inputFile.substring(inputFile.lastIndexOf("/") + 1);
                    }
                    logManager.RemoveOldLogFile(logFilePath, inputFileName);
                    logManager.RemoveOldInvalidDataFile(logFilePath, inputFileName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
