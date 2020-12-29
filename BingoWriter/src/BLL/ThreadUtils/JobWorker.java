package BLL.ThreadUtils;

import BLL.Config.ConfigManager;
import BLL.ThreadUtils.Timer.*;
import DAL.DataProcessor;
import DAL.DbProcessor;
import DAL.ESHelper;
import DAL.HBaseHelper;
import UI.BingoWriterUI;

import javax.swing.*;

public class JobWorker implements Runnable {
    private BingoWriterUI ui;
    private JFrame frame;

    private boolean IsHBaseConnected() {
        boolean ret;
        try {
            ui.uiManager.AddProgressPanel(frame, "Connecting zookeeper...");
            ret = new HBaseHelper().ConnectToHbase(
                    ui.modelConfigInfo.getZKHost(),
                    String.valueOf(ui.modelConfigInfo.getZKPort())
            );
            ui.uiManager.RemoveProgressPanel();
            if (!ret) {
                JOptionPane.showMessageDialog(
                        null,
                        "Connect zookeeper failed.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                ui.uiManager.AddProgressPanel(frame, "Shutting down...");
                System.exit(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            ret = false;
        }
        return ret;
    }

    private boolean IsESConnected() {
        boolean ret;
        try {
            ui.uiManager.AddProgressPanel(frame, "Connecting elasticsearch...");
            ret = new ESHelper().ConnectToES(
                    ui.modelConfigInfo.getESCluster(),
                    ui.modelConfigInfo.getESHost(),
                    ui.modelConfigInfo.getESPort()
            );
            ui.uiManager.RemoveProgressPanel();
            if (!ret) {
                JOptionPane.showMessageDialog(
                        null,
                        "Connect elasticsearch failed.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                ui.uiManager.AddProgressPanel(frame, "Shutting down...");
                System.exit(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            ret = false;
        }
        return ret;
    }

    private void Engine() {
        ConfigManager configManager = new ConfigManager();
        if (configManager.ConfigWrite(ui.modelConfigInfo)) {
            DataProcessor dataProcessor = new DataProcessor();
            dataProcessor.SetArgs(ui);
            new Thread(dataProcessor).start();
            if (ui.modelConfigInfo.getHBaseTbHashChecked()) {
                HBTbHashTimer hbTbHashTimer = new HBTbHashTimer();
                hbTbHashTimer.SetArgs(ui);
                hbTbHashTimer.StartWriterTimer();
            }
            if (ui.modelConfigInfo.getHBaseTbBingoChecked()) {
                HBTbBingoTimer hbTbBingoTimer = new HBTbBingoTimer();
                hbTbBingoTimer.SetArgs(ui);
                hbTbBingoTimer.StartWriterTimer();
            }
            if (ui.modelConfigInfo.getESTbHashChecked()) {
                ESTbHashTimer esTbHashTimer = new ESTbHashTimer();
                esTbHashTimer.SetArgs(ui);
                esTbHashTimer.StartWriterTimer();
            }
            if (ui.modelConfigInfo.getESTbBingoChecked()) {
                ESTbBingoTimer esTbBingoTimer = new ESTbBingoTimer();
                esTbBingoTimer.SetArgs(ui);
                esTbBingoTimer.StartWriterTimer();
            }
            RefresherTimer refresherTimer = new RefresherTimer();
            refresherTimer.SetArgs(ui);
            refresherTimer.StartRefresherTimer();
        } else {
            ui.uiManager.RemoveProgressPanel();
            JOptionPane.showMessageDialog(
                    null,
                    "Write config file failed.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void AdvEngine() {
        DbProcessor dbProcessor = new DbProcessor();
        String s1 = dbProcessor.CreateOrUpdateTbHash(ui.modelConfigInfo);
        String s2 = dbProcessor.CreateOrUpdateTbBingo(ui.modelConfigInfo);
        if (!s1.contains("[T]")) {
            ui.uiManager.RemoveProgressPanel();
            JOptionPane.showMessageDialog(
                    null,
                    "Create or update hbase table " + ui.modelConfigInfo.getTbHash() + " failed.\n" + s1,
                    "Warning", JOptionPane.WARNING_MESSAGE
            );
        } else if (!s2.contains("[T]")) {
            ui.uiManager.RemoveProgressPanel();
            JOptionPane.showMessageDialog(
                    null,
                    "Create or update hbase table " + ui.modelConfigInfo.getTbBingo() + " failed.\n" + s2,
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
        } else {
            Engine();
        }
    }

    private void StartEngine() {
        if (ui.modelConfigInfo.getZKChecked() && !ui.modelConfigInfo.getESChecked() && IsHBaseConnected()) {
            ui.uiManager.AddProgressPanel(frame, "Running...");
            AdvEngine();
        } else if (!ui.modelConfigInfo.getZKChecked() && ui.modelConfigInfo.getESChecked() && IsESConnected()) {
            ui.uiManager.AddProgressPanel(frame, "Running...");
            Engine();
        } else if (ui.modelConfigInfo.getZKChecked() && ui.modelConfigInfo.getESChecked() && IsHBaseConnected() && IsESConnected()) {
            ui.uiManager.AddProgressPanel(frame, "Running...");
            AdvEngine();
        }
    }

    public void SetArgs(BingoWriterUI ui, JFrame frame) {
        this.ui = ui;
        this.frame = frame;
    }

    public void run() {
        StartEngine();
    }
}
