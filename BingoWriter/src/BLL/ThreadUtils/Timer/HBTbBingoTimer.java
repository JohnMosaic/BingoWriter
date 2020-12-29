package BLL.ThreadUtils.Timer;

import BLL.Common.LogManager;
import BLL.ThreadUtils.JobMonitor;
import BLL.ThreadUtils.Timer.Writer.HBTbBingoWriter;
import UI.BingoWriterUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HBTbBingoTimer {
    private BingoWriterUI ui;
    private LogManager logManager = new LogManager();
    private JobMonitor jobMonitor = new JobMonitor();
    private Timer timer;

    public void StartWriterTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                WriteIntoHBase();
            }
        }, 0, 1000);
    }

    private void StopWriterTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public void SetArgs(BingoWriterUI ui) {
        this.ui = ui;
    }

    private void WriteIntoHBase() {
        try {
            if (ui.modelConfigInfo.getWriteMemFinished() && ui.tbBingoSqlList2.size() == 0 && !ui.modelConfigInfo.getWriteHBaseTbBingoFinished()) {
                StopWriterTimer();
                ui.modelConfigInfo.setWriteHBaseTbBingoFinished(true);
                ui.lblStatusVal.setText("Complete write hbase table " + ui.modelConfigInfo.getTbBingo() + "...");
                logManager.LogRecord(
                        ui.modelConfigInfo.getLogFilePath(),
                        ui.modelConfigInfo.getInputFileName(),
                        "Complete write hbase table " + ui.modelConfigInfo.getTbBingo() + "...",
                        "HB"
                );
                jobMonitor.FinishJobsActoJM(ui);
            } else if (ui.tbBingoSqlList2.size() > 0 && !ui.modelConfigInfo.getWriteHBaseTbBingoFinished() && !ui.modelConfigInfo.getTbBingoSqlListUsed()) {
                int curThreadNumber = ui.modelConfigInfo.getCurHBaseThreadNumber();
                if (curThreadNumber < ui.modelConfigInfo.getHBaseMaxThreads()) {
                    List<String> tbBingoSqlListCopy = new ArrayList<>();
                    ui.modelConfigInfo.setTbBingoSqlListUsed(true);
                    for (String strTbBingo : ui.tbBingoSqlList2.get(0)) {
                        tbBingoSqlListCopy.add(strTbBingo);
                    }
                    ui.tbBingoSqlList2.remove(0);
                    ui.modelConfigInfo.setTbBingoSqlListUsed(false);
                    HBTbBingoWriter hbTbBingoWriter = new HBTbBingoWriter();
                    hbTbBingoWriter.SetArgs(ui, tbBingoSqlListCopy);
                    new Thread(hbTbBingoWriter).start();
                }
            }
        } catch (Exception e) {
            logManager.LogRecord(
                    ui.modelConfigInfo.getLogFilePath(),
                    ui.modelConfigInfo.getInputFileName(),
                    e.getMessage(),
                    "HB"
            );
        }
    }
}
