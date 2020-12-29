package BLL.ThreadUtils.ConnTester;

import BLL.Common.TypeChecker;
import DAL.ESHelper;
import UI.BingoWriterUI;

import javax.swing.*;

public class ESConnTester implements Runnable {
    private BingoWriterUI ui;
    private JFrame frame;

    private void TestESConn() {
        try {
            TypeChecker typeChecker = new TypeChecker();
            String esCluster = ui.tfdESCluster.getText().trim();
            String hostName = ui.tfdESHost.getText().trim();
            String esPort = ui.tfdESPort.getText().trim();
            if (esCluster.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input the elasticsearch cluster's name.\neg: (bingo).",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (hostName.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input the elasticsearch host's name or ip address.\neg: (bingo4 | 192.168.222.4).",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (!typeChecker.IsAPort(esPort)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input the elasticsearch's port.\nPort range: [0, 65535].",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                ui.uiManager.AddProgressPanel(frame, "Connecting elasticsearch...");
                boolean b = new ESHelper().ConnectToES(esCluster, hostName, Integer.parseInt(esPort));
                ui.uiManager.RemoveProgressPanel();
                if (b) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Connect elasticsearch succeeded.",
                            "Tips",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Connect elasticsearch failed.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE
                    );
                    ui.uiManager.AddProgressPanel(frame, "Shutting down...");
                    System.exit(0);
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

    public void SetArgs(BingoWriterUI ui, JFrame frame) {
        this.ui = ui;
        this.frame = frame;
    }

    public void run() {
        TestESConn();
    }
}
