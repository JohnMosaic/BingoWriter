package BLL.ThreadUtils.Timer;

import BLL.Common.LogManager;
import BLL.ThreadUtils.JobMonitor;
import BLL.ThreadUtils.Timer.Writer.ESTbHashWriter;
import UI.BingoWriterUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ESTbHashTimer {
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
        if(timer != null) {
            timer.cancel();
        }
    }

    public void SetArgs(BingoWriterUI ui) {
        this.ui = ui;
    }

    private void WriteIntoES() {
        try {
            if (ui.modelConfigInfo.getWriteMemFinished() && ui.tbHashJsonList2.size() == 0 && !ui.modelConfigInfo.getWriteESTbHashFinished()) {
                StopWriterTimer();
                ui.modelConfigInfo.setWriteESTbHashFinished(true);
                ui.lblStatusVal.setText("Complete write elasticsearch index " + ui.modelConfigInfo.getTbHash() + "...");
                logManager.LogRecord(
                        ui.modelConfigInfo.getLogFilePath(),
                        ui.modelConfigInfo.getInputFileName(),
                        "Complete write elasticsearch index " + ui.modelConfigInfo.getTbHash() + "...",
                        "ES"
                );
                jobMonitor.FinishJobsActoJM(ui);
            } else if (ui.tbHashJsonList2.size() > 0 && !ui.modelConfigInfo.getWriteESTbHashFinished() && !ui.modelConfigInfo.getTbHashJsonListUsed()) {
                int curThreadNumber = ui.modelConfigInfo.getCurESThreadNumber();
                if (curThreadNumber < ui.modelConfigInfo.getESMaxThreads()) {
                    List<String> tbHashJsonListCopy = new ArrayList<>();
                    ui.modelConfigInfo.setTbHashJsonListUsed(true);
                    for (String jsonStr : ui.tbHashJsonList2.get(0)) {
                        tbHashJsonListCopy.add(jsonStr);
                    }
                    ui.tbHashJsonList2.remove(0);
                    ui.modelConfigInfo.setTbHashJsonListUsed(false);
                    ESTbHashWriter esTbHashWriter = new ESTbHashWriter();
                    esTbHashWriter.SetArgs(ui, tbHashJsonListCopy);
                    new Thread(esTbHashWriter).start();
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
