package BLL.ThreadUtils.Timer;

import BLL.Common.LogManager;
import BLL.ThreadUtils.JobMonitor;
import BLL.ThreadUtils.Timer.Writer.HBTbHashWriter;
import UI.BingoWriterUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HBTbHashTimer {
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
            if (ui.modelConfigInfo.getWriteMemFinished() && ui.tbHashSqlList2.size() == 0 && !ui.modelConfigInfo.getWriteHBaseTbHashFinished()) {
                StopWriterTimer();
                ui.modelConfigInfo.setWriteHBaseTbHashFinished(true);
                ui.lblStatusVal.setText("Complete write hbase table " + ui.modelConfigInfo.getTbHash() + "...");
                logManager.LogRecord(
                        ui.modelConfigInfo.getLogFilePath(),
                        ui.modelConfigInfo.getInputFileName(),
                        "Complete write hbase table " + ui.modelConfigInfo.getTbHash() + "...",
                        "HB"
                );
                jobMonitor.FinishJobsActoJM(ui);
            } else if (ui.tbHashSqlList2.size() > 0 && !ui.modelConfigInfo.getWriteHBaseTbHashFinished() && !ui.modelConfigInfo.getTbHashSqlListUsed()) {
                int curThreadNumber = ui.modelConfigInfo.getCurHBaseThreadNumber();
                if (curThreadNumber < ui.modelConfigInfo.getHBaseMaxThreads()) {
                    List<String> tbHashSqlListCopy = new ArrayList<>();
                    ui.modelConfigInfo.setTbHashSqlListUsed(true);
                    for (String strTbHash : ui.tbHashSqlList2.get(0)) {
                        tbHashSqlListCopy.add(strTbHash);
                    }
                    ui.tbHashSqlList2.remove(0);
                    ui.modelConfigInfo.setTbHashSqlListUsed(false);
                    HBTbHashWriter hbTbHashWriter = new HBTbHashWriter();
                    hbTbHashWriter.SetArgs(ui, tbHashSqlListCopy);
                    new Thread(hbTbHashWriter).start();
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