package BLL.ThreadUtils.Timer;

import BLL.Common.LogManager;
import BLL.ThreadUtils.JobMonitor;
import BLL.ThreadUtils.Timer.Writer.ESTbBingoWriter;
import UI.BingoWriterUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ESTbBingoTimer {
    private BingoWriterUI ui;
    private LogManager logManager = new LogManager();
    private JobMonitor jobMonitor = new JobMonitor();
    private Timer timer;

    public void StartWriterTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                WriteIntoES();
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

    private void WriteIntoES() {
        try {
            if (ui.modelConfigInfo.getWriteMemFinished() && ui.tbBingoJsonList2.size() == 0 && !ui.modelConfigInfo.getWriteESTbBingoFinished()) {
                StopWriterTimer();
                ui.modelConfigInfo.setWriteESTbBingoFinished(true);
                ui.lblStatusVal.setText("Complete write elasticsearch index " + ui.modelConfigInfo.getTbBingo() + "...");
                logManager.LogRecord(
                        ui.modelConfigInfo.getLogFilePath(),
                        ui.modelConfigInfo.getInputFileName(),
                        "Complete write elasticsearch index " + ui.modelConfigInfo.getTbBingo() + "...",
                        "ES"
                );
                jobMonitor.FinishJobsActoJM(ui);
            } else if (ui.tbBingoJsonList2.size() > 0 && !ui.modelConfigInfo.getWriteESTbBingoFinished() && !ui.modelConfigInfo.getTbBingoJsonListUsed()) {
                int curThreadNumber = ui.modelConfigInfo.getCurESThreadNumber();
                if (curThreadNumber < ui.modelConfigInfo.getESMaxThreads()) {
                    List<String> tbBingoJsonListCopy = new ArrayList<>();
                    ui.modelConfigInfo.setTbBingoJsonListUsed(true);
                    for (String jsonStr : ui.tbBingoJsonList2.get(0)) {
                        tbBingoJsonListCopy.add(jsonStr);
                    }
                    ui.tbBingoJsonList2.remove(0);
                    ui.modelConfigInfo.setTbBingoJsonListUsed(false);
                    ESTbBingoWriter esTbBingoWriter = new ESTbBingoWriter();
                    esTbBingoWriter.SetArgs(ui, tbBingoJsonListCopy);
                    new Thread(esTbBingoWriter).start();
                }
            }
        } catch (Exception e) {
            logManager.LogRecord(
                    ui.modelConfigInfo.getLogFilePath(),
                    ui.modelConfigInfo.getInputFileName(),
                    e.getMessage(),
                    "ES"
            );
        }
    }
}
