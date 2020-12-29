package DAL;

import BLL.Common.LogManager;
import BLL.Common.TimeMarker;
import BLL.Common.TypeChecker;
import Model.ModelConfigInfo;
import UI.BingoWriterUI;

import org.joda.time.DateTime;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DbProcessor implements Runnable {
    private BingoWriterUI bbui;
    private JFrame frame;
    private String tbName;
    private HBaseHelper hBaseHelper = new HBaseHelper();
    private ESHelper esHelper = new ESHelper();
    private LogManager logManager = new LogManager();
    private TimeMarker timeMarker = new TimeMarker();

    public String CreateOrUpdateTbHash(ModelConfigInfo modelConfigInfo) {
        String ret = "[T]";
        List<String> strList = modelConfigInfo.getTbHashColumnsList();
        List<String> retStrList = new ArrayList<>();
        try {
            if (modelConfigInfo.getHBaseTbHashChecked()) {
                String pk = "";
                for (String s : strList) {
                    String r;
                    if (s.contains("*")) {
                        r = s.replace("*", "") + " VARCHAR NOT NULL";
                        pk += (s.replace("*", "") + ",");
                    } else {
                        r = s + " VARCHAR";
                    }
                    retStrList.add(r);
                }
                if (pk.contains(",")) {
                    pk = pk.substring(0, pk.lastIndexOf(","));
                }
                String res = hBaseHelper.CreateOrUpdateTable(
                        modelConfigInfo.getZKHost(),
                        String.valueOf(modelConfigInfo.getZKPort()),
                        modelConfigInfo.getTbHash(),
                        retStrList,
                        pk,
                        modelConfigInfo.getSaltBuckets()
                );
                if (!res.contains("[T]")) {
                    ret = res;
                }
            }
        } catch (Exception e) {
            ret = e.getMessage();
            logManager.LogRecord(
                    modelConfigInfo.getLogFilePath(),
                    modelConfigInfo.getInputFileName(),
                    e.getMessage() + " Create or update hbase table " + modelConfigInfo.getTbHash() + " failed...",
                    "HB"
            );
        }
        return ret;
    }

    public String CreateOrUpdateTbBingo(ModelConfigInfo modelConfigInfo) {
        String ret = "[T]";
        List<String> strList = modelConfigInfo.getTbBingoColumnsList();
        List<String> retStrList = new ArrayList<>();
        try {
            if (modelConfigInfo.getHBaseTbBingoChecked()) {
                String pk = "";
                for (String s : strList) {
                    String r;
                    if (s.contains("*")) {
                        r = s.replace("*", "") + " VARCHAR NOT NULL";
                        pk += (s.replace("*", "") + ",");
                    } else {
                        r = s + " VARCHAR";
                    }
                    retStrList.add(r);
                }
                if (pk.contains(",")) {
                    pk = pk.substring(0, pk.lastIndexOf(","));
                }
                String res = hBaseHelper.CreateOrUpdateTable(
                        modelConfigInfo.getZKHost(),
                        String.valueOf(modelConfigInfo.getZKPort()),
                        modelConfigInfo.getTbBingo(),
                        retStrList,
                        pk,
                        modelConfigInfo.getSaltBuckets()
                );
                if (!res.contains("[T]")) {
                    ret = res;
                }
            }
        } catch (Exception e) {
            ret = e.getMessage();
            logManager.LogRecord(
                    modelConfigInfo.getLogFilePath(),
                    modelConfigInfo.getInputFileName(),
                    e.getMessage() + " Create or update hbase table " + modelConfigInfo.getTbBingo() + " failed...",
                    "HB"
            );
        }
        return ret;
    }

    private void AddColumn() {
        try {
            String s = "";
            if (tbName.contains("tb1")) {
                s = bbui.tfdAddTbHashColumn.getText().trim();
                tbName = bbui.tfdTbHash.getText().trim();
                if (tbName.equals("")) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Please input the table's name.",
                            "Tips",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            } else if (tbName.contains("tb2")) {
                s = bbui.tfdAddTbBingoColumn.getText().trim();
                tbName = bbui.tfdTbBingo.getText().trim();
                if (tbName.equals("")) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Please input the table's name.",
                            "Tips",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
            if (s.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input a new column name.",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                TypeChecker typeChecker = new TypeChecker();
                String hostName = bbui.tfdZKHost.getText().trim();
                String zkPort = bbui.tfdZKPort.getText().trim();
                if (hostName.equals("")) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Please input the zookeeper host's name.\neg: (cdh1 | 192.168.222.1).",
                            "Tips",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else if (!typeChecker.IsAPort(zkPort)) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Please input the zookeeper's port.\nPort range: [0, 65535].",
                            "Tips",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    if (s.contains(",")) {
                        s = s.replace(",", " VARCHAR,");
                    }
                    s += " VARCHAR";
                    bbui.uiManager.AddProgressPanel(frame, "Adding...");
                    String ret = hBaseHelper.AddTableColumn(hostName, zkPort, tbName, s);
                    bbui.uiManager.RemoveProgressPanel();
                    if (ret.contains("[T]")) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Add succeeded.",
                                "Tips",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Add failed.\n" + ret.replace("[T]", ""),
                                "Warning",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void WriteIntoHBaseTbHash(BingoWriterUI ui, List<String> tbHashSqlList) {
        try {
            ui.lblStatusVal.setText("Writing hbase table " + ui.modelConfigInfo.getTbHash() + "...");
            DateTime dtStart = DateTime.now();
            long elapsedTimeOld = ui.modelTaskInfo.getHBaseTbHashElapsedTime();
            int errorCount = hBaseHelper.UpsertData(ui, tbHashSqlList);
            DateTime dtEnd = DateTime.now();
            long elapsedTimeAdd = timeMarker.GetDateTimeMillisMinus(dtStart, dtEnd);
            ui.modelTaskInfo.setHBaseTbHashElapsedTime(elapsedTimeOld + elapsedTimeAdd);
            long sCount = tbHashSqlList.size() - errorCount;
            long succeededCount = ui.modelTaskInfo.getHBaseTbHashSucceededCount();
            ui.modelTaskInfo.setHBaseTbHashSucceededCount(succeededCount + sCount);
            long failedCount = ui.modelTaskInfo.getHBaseTbHashFailedCount() + errorCount;
            ui.modelTaskInfo.setHBaseTbHashFailedCount(failedCount);
        } catch (Exception e) {
            long failedCount = ui.modelTaskInfo.getHBaseTbHashFailedCount() + tbHashSqlList.size();
            ui.modelTaskInfo.setHBaseTbHashFailedCount(failedCount);
            ui.lblStatusVal.setText("Writing hbase table " + ui.modelConfigInfo.getTbHash() + " failed...");
            logManager.LogRecord(
                    ui.modelConfigInfo.getLogFilePath(),
                    ui.modelConfigInfo.getInputFileName(),
                    e.getMessage() + " Writing hbase table " + ui.modelConfigInfo.getTbHash() + " failed...",
                    "HB"
            );
        }
    }

    public void WriteIntoESTbHash(BingoWriterUI ui, List<String> tbHashJsonList) {
        try {
            ui.lblStatusVal.setText("Writing elasticsearch index " + ui.modelConfigInfo.getTbHash() + "...");
            DateTime dtStart = DateTime.now();
            long elapsedTimeOld = ui.modelTaskInfo.getESTbHashElapsedTime();
            int errorCount = esHelper.UpsertData(ui, tbHashJsonList, ui.modelConfigInfo.getTbHash(),"t", ui.modelConfigInfo.getTbHashIndexNumber());
            DateTime dtEnd = DateTime.now();
            long elapsedTimeAdd = timeMarker.GetDateTimeMillisMinus(dtStart, dtEnd);
            ui.modelTaskInfo.setESTbHashElapsedTime(elapsedTimeOld + elapsedTimeAdd);
            long sCount = tbHashJsonList.size() - errorCount;
            long succeededCount = ui.modelTaskInfo.getESTbHashSucceededCount();
            ui.modelTaskInfo.setESTbHashSucceededCount(succeededCount + sCount);
            long failedCount = ui.modelTaskInfo.getESTbHashFailedCount() + errorCount;
            ui.modelTaskInfo.setESTbHashFailedCount(failedCount);
        } catch (Exception e) {
            long failedCount = ui.modelTaskInfo.getESTbHashFailedCount() + tbHashJsonList.size();
            ui.modelTaskInfo.setESTbHashFailedCount(failedCount);
            ui.lblStatusVal.setText("Writing elasticsearch index " + ui.modelConfigInfo.getTbHash() + " failed...");
            logManager.LogRecord(
                    ui.modelConfigInfo.getLogFilePath(),
                    ui.modelConfigInfo.getInputFileName(),
                    e.getMessage() + " Writing elasticsearch index " + ui.modelConfigInfo.getTbHash() + " failed...",
                    "ES"
            );
        }
    }

    public void WriteIntoHBaseTbBingo(BingoWriterUI ui, List<String> tbBingoSqlList) {
        try {
            ui.lblStatusVal.setText("Writing hbase table " + ui.modelConfigInfo.getTbBingo() + "...");
            DateTime dtStart = DateTime.now();
            long elapsedTimeOld = ui.modelTaskInfo.getHBaseTbBingoElapsedTime();
            int errorCount = hBaseHelper.UpsertData(ui, tbBingoSqlList);
            DateTime dtEnd = DateTime.now();
            long elapsedTimeAdd = timeMarker.GetDateTimeMillisMinus(dtStart, dtEnd);
            ui.modelTaskInfo.setHBaseTbBingoElapsedTime(elapsedTimeOld + elapsedTimeAdd);
            long sCount = tbBingoSqlList.size() - errorCount;
            long succeededCount = ui.modelTaskInfo.getHBaseTbBingoSucceededCount();
            ui.modelTaskInfo.setHBaseTbBingoSucceededCount(succeededCount + sCount);
            long failedCount = ui.modelTaskInfo.getHBaseTbBingoFailedCount() + errorCount;
            ui.modelTaskInfo.setHBaseTbBingoFailedCount(failedCount);
        } catch (Exception e) {
            long failedCount = ui.modelTaskInfo.getHBaseTbBingoFailedCount() + tbBingoSqlList.size();
            ui.modelTaskInfo.setHBaseTbBingoFailedCount(failedCount);
            ui.lblStatusVal.setText("Writing hbase table " + ui.modelConfigInfo.getTbBingo() + " failed...");
            logManager.LogRecord(
                    ui.modelConfigInfo.getLogFilePath(),
                    ui.modelConfigInfo.getInputFileName(),
                    e.getMessage() + " Writing hbase table " + ui.modelConfigInfo.getTbBingo() + " failed...",
                    "HB"
            );
        }
    }

    public void WriteIntoESTbBingo(BingoWriterUI ui, List<String> tbBingoJsonList) {
        try {
            ui.lblStatusVal.setText("Writing elasticsearch index " + ui.modelConfigInfo.getTbBingo() + "...");
            DateTime dtStart = DateTime.now();
            long elapsedTimeOld = ui.modelTaskInfo.getESTbBingoElapsedTime();
            int errorCount = esHelper.UpsertData(ui, tbBingoJsonList, ui.modelConfigInfo.getTbBingo(), "t", ui.modelConfigInfo.getTbBingoIndexNumber());
            DateTime dtEnd = DateTime.now();
            long elapsedTimeAdd = timeMarker.GetDateTimeMillisMinus(dtStart, dtEnd);
            ui.modelTaskInfo.setESTbBingoElapsedTime(elapsedTimeOld + elapsedTimeAdd);
            long sCount = tbBingoJsonList.size() - errorCount;
            long succeededCount = ui.modelTaskInfo.getESTbBingoSucceededCount();
            ui.modelTaskInfo.setESTbBingoSucceededCount(succeededCount + sCount);
            long failedCount = ui.modelTaskInfo.getESTbBingoFailedCount() + errorCount;
            ui.modelTaskInfo.setESTbBingoFailedCount(failedCount);
        } catch (Exception e) {
            long failedCount = ui.modelTaskInfo.getESTbBingoFailedCount() + tbBingoJsonList.size();
            ui.modelTaskInfo.setESTbBingoFailedCount(failedCount);
            ui.lblStatusVal.setText("Writing elasticsearch index " + ui.modelConfigInfo.getTbBingo() + " failed...");
            logManager.LogRecord(
                    ui.modelConfigInfo.getLogFilePath(),
                    ui.modelConfigInfo.getInputFileName(),
                    e.getMessage() + " Writing elasticsearch index " + ui.modelConfigInfo.getTbBingo() + " failed...",
                    "ES"
            );
        }
    }

    public void SetArgs(BingoWriterUI ui, JFrame frame, String tbName) {
        this.bbui = ui;
        this.frame = frame;
        this.tbName = tbName;
    }

    public void run() {
        AddColumn();
    }
}
