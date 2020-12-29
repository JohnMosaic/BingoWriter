package BLL.ThreadUtils.ConnTester;

import BLL.Common.TypeChecker;
import DAL.HBaseHelper;
import UI.BingoWriterUI;

import javax.swing.*;

public class ZKConnTester implements Runnable {
    private BingoWriterUI ui;
    private JFrame frame;

    private void TestZKConn() {
        try {
            TypeChecker typeChecker = new TypeChecker();
            String hostName = ui.tfdZKHost.getText().trim();
            String zkPort = ui.tfdZKPort.getText().trim();
            if (hostName.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input the host's name.\neg: (cdh1).",
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
                ui.uiManager.AddProgressPanel(frame, "Connecting zookeeper...");
                boolean b = new HBaseHelper().ConnectToHbase(hostName, zkPort);
                ui.uiManager.RemoveProgressPanel();
                if (b) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Connect zookeeper succeeded.",
                            "Tips",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Connect zookeeper failed.",
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
        TestZKConn();
    }
}
