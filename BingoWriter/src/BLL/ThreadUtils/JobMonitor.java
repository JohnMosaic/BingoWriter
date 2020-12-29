package BLL.ThreadUtils;

import BLL.Common.LogManager;
import Model.ModelConfigInfo;
import Model.ModelTaskInfo;
import UI.BingoWriterUI;

import java.util.ArrayList;

public class JobMonitor {
    private LogManager logManager = new LogManager();

    private void FinishJobs(BingoWriterUI ui) {
        if (ui.timer != null) {
            ui.timer.cancel();
        }
        ui.lblStatusVal.setText("Complete write all tables...");
        logManager.LogRecord(
                ui.modelConfigInfo.getLogFilePath(),
                ui.modelConfigInfo.getInputFileName(),
                "Complete write all tables...",
                "JM"
        );
        ui.uiManager.UpdateTaskInfo(ui);
        ui.uiManager.RemoveProgressPanel();
        ui.modelConfigInfo = new ModelConfigInfo();
        ui.modelTaskInfo = new ModelTaskInfo();
        ui.tbHashSqlList2.clear();
        ui.tbHashSqlList2 = new ArrayList<>();
        ui.tbBingoSqlList2.clear();
        ui.tbBingoSqlList2 = new ArrayList<>();
        ui.tbHashJsonList2.clear();
        ui.tbHashJsonList2 = new ArrayList<>();
        ui.tbBingoJsonList2.clear();
        ui.tbBingoJsonList2 = new ArrayList<>();
    }

    public void FinishJobsActoJM(BingoWriterUI ui) {
        if (!ui.isJobsFinished) {
            ui.isJobsFinished = true;
            int tryTimes = 0;
            while (tryTimes < 120) {
                if (ui.modelConfigInfo.getHBaseTbHashChecked() &&
                        !ui.modelConfigInfo.getESTbHashChecked() &&
                        !ui.modelConfigInfo.getHBaseTbBingoChecked() &&
                        !ui.modelConfigInfo.getESTbBingoChecked() &&
                        ui.modelConfigInfo.getWriteHBaseTbHashFinished()) {
                    FinishJobs(ui);
                    break;
                } else if (!ui.modelConfigInfo.getHBaseTbHashChecked() &&
                        ui.modelConfigInfo.getESTbHashChecked() &&
                        !ui.modelConfigInfo.getHBaseTbBingoChecked() &&
                        !ui.modelConfigInfo.getESTbBingoChecked() &&
                        ui.modelConfigInfo.getWriteESTbHashFinished()) {
                    FinishJobs(ui);
                    break;
                } else if (!ui.modelConfigInfo.getHBaseTbHashChecked() &&
                        !ui.modelConfigInfo.getESTbHashChecked() &&
                        ui.modelConfigInfo.getHBaseTbBingoChecked() &&
                        !ui.modelConfigInfo.getESTbBingoChecked() &&
                        ui.modelConfigInfo.getWriteHBaseTbBingoFinished() &&
                        ui.modelTaskInfo.getValidCount() == ui.modelTaskInfo.getHBaseTbBingoSucceededCount() + ui.modelTaskInfo.getHBaseTbBingoFailedCount()) {
                    FinishJobs(ui);
                    break;
                } else if (!ui.modelConfigInfo.getHBaseTbHashChecked() &&
                        !ui.modelConfigInfo.getESTbHashChecked() &&
                        !ui.modelConfigInfo.getHBaseTbBingoChecked() &&
                        ui.modelConfigInfo.getESTbBingoChecked() &&
                        ui.modelConfigInfo.getWriteESTbBingoFinished() &&
                        ui.modelTaskInfo.getValidCount() == ui.modelTaskInfo.getESTbBingoSucceededCount() + ui.modelTaskInfo.getESTbBingoFailedCount()) {
                    FinishJobs(ui);
                    break;
                } else if (ui.modelConfigInfo.getHBaseTbHashChecked() &&
                        ui.modelConfigInfo.getESTbHashChecked() &&
                        !ui.modelConfigInfo.getHBaseTbBingoChecked() &&
                        !ui.modelConfigInfo.getESTbBingoChecked() &&
                        ui.modelConfigInfo.getWriteHBaseTbHashFinished() &&
                        ui.modelConfigInfo.getWriteESTbHashFinished()) {
                    FinishJobs(ui);
                    break;
                } else if (ui.modelConfigInfo.getHBaseTbHashChecked() &&
                        !ui.modelConfigInfo.getESTbHashChecked() &&
                        ui.modelConfigInfo.getHBaseTbBingoChecked() &&
                        !ui.modelConfigInfo.getESTbBingoChecked() &&
                        ui.modelConfigInfo.getWriteHBaseTbHashFinished() &&
                        ui.modelConfigInfo.getWriteHBaseTbBingoFinished() &&
                        ui.modelTaskInfo.getValidCount() == ui.modelTaskInfo.getHBaseTbBingoSucceededCount() + ui.modelTaskInfo.getHBaseTbBingoFailedCount()) {
                    FinishJobs(ui);
                    break;
                } else if (ui.modelConfigInfo.getHBaseTbHashChecked() &&
                        !ui.modelConfigInfo.getESTbHashChecked() &&
                        !ui.modelConfigInfo.getHBaseTbBingoChecked() &&
                        ui.modelConfigInfo.getESTbBingoChecked() &&
                        ui.modelConfigInfo.getWriteHBaseTbHashFinished() &&
                        ui.modelConfigInfo.getWriteESTbBingoFinished() &&
                        ui.modelTaskInfo.getValidCount() == ui.modelTaskInfo.getESTbBingoSucceededCount() + ui.modelTaskInfo.getESTbBingoFailedCount()) {
                    FinishJobs(ui);
                    break;
                } else if (!ui.modelConfigInfo.getHBaseTbHashChecked() &&
                        ui.modelConfigInfo.getESTbHashChecked() &&
                        ui.modelConfigInfo.getHBaseTbBingoChecked() &&
                        !ui.modelConfigInfo.getESTbBingoChecked() &&
                        ui.modelConfigInfo.getWriteESTbHashFinished() &&
                        ui.modelConfigInfo.getWriteHBaseTbBingoFinished() &&
                        ui.modelTaskInfo.getValidCount() == ui.modelTaskInfo.getHBaseTbBingoSucceededCount() + ui.modelTaskInfo.getHBaseTbBingoFailedCount()) {
                    FinishJobs(ui);
                    break;
                } else if (!ui.modelConfigInfo.getHBaseTbHashChecked() &&
                        ui.modelConfigInfo.getESTbHashChecked() &&
                        !ui.modelConfigInfo.getHBaseTbBingoChecked() &&
                        ui.modelConfigInfo.getESTbBingoChecked() &&
                        ui.modelConfigInfo.getWriteESTbHashFinished() &&
                        ui.modelConfigInfo.getWriteESTbBingoFinished() &&
                        ui.modelTaskInfo.getValidCount() == ui.modelTaskInfo.getESTbBingoSucceededCount() + ui.modelTaskInfo.getESTbBingoFailedCount()) {
                    FinishJobs(ui);
                    break;
                } else if (!ui.modelConfigInfo.getHBaseTbHashChecked() &&
                        !ui.modelConfigInfo.getESTbHashChecked() &&
                        ui.modelConfigInfo.getHBaseTbBingoChecked() &&
                        ui.modelConfigInfo.getESTbBingoChecked() &&
                        ui.modelConfigInfo.getWriteHBaseTbBingoFinished() &&
                        ui.modelConfigInfo.getWriteESTbBingoFinished() &&
                        ui.modelTaskInfo.getValidCount() == ui.modelTaskInfo.getHBaseTbBingoSucceededCount() + ui.modelTaskInfo.getHBaseTbBingoFailedCount() &&
                        ui.modelTaskInfo.getValidCount() == ui.modelTaskInfo.getESTbBingoSucceededCount() + ui.modelTaskInfo.getESTbBingoFailedCount()) {
                    FinishJobs(ui);
                    break;
                } else if (ui.modelConfigInfo.getHBaseTbHashChecked() &&
                        ui.modelConfigInfo.getESTbHashChecked() &&
                        ui.modelConfigInfo.getHBaseTbBingoChecked() &&
                        !ui.modelConfigInfo.getESTbBingoChecked() &&
                        ui.modelConfigInfo.getWriteHBaseTbHashFinished() &&
                        ui.modelConfigInfo.getWriteESTbHashFinished() &&
                        ui.modelConfigInfo.getWriteHBaseTbBingoFinished() &&
                        ui.modelTaskInfo.getValidCount() == ui.modelTaskInfo.getHBaseTbBingoSucceededCount() + ui.modelTaskInfo.getHBaseTbBingoFailedCount()) {
                    FinishJobs(ui);
                    break;
                } else if (ui.modelConfigInfo.getHBaseTbHashChecked() &&
                        ui.modelConfigInfo.getESTbHashChecked() &&
                        !ui.modelConfigInfo.getHBaseTbBingoChecked() &&
                        ui.modelConfigInfo.getESTbBingoChecked() &&
                        ui.modelConfigInfo.getWriteHBaseTbHashFinished() &&
                        ui.modelConfigInfo.getWriteESTbHashFinished() &&
                        ui.modelConfigInfo.getWriteESTbBingoFinished() &&
                        ui.modelTaskInfo.getValidCount() == ui.modelTaskInfo.getESTbBingoSucceededCount() + ui.modelTaskInfo.getESTbBingoFailedCount()) {
                    FinishJobs(ui);
                    break;
                } else if (ui.modelConfigInfo.getHBaseTbHashChecked() &&
                        !ui.modelConfigInfo.getESTbHashChecked() &&
                        ui.modelConfigInfo.getHBaseTbBingoChecked() &&
                        ui.modelConfigInfo.getESTbBingoChecked() &&
                        ui.modelConfigInfo.getWriteHBaseTbHashFinished() &&
                        ui.modelConfigInfo.getWriteHBaseTbBingoFinished() &&
                        ui.modelConfigInfo.getWriteESTbBingoFinished() &&
                        ui.modelTaskInfo.getValidCount() == ui.modelTaskInfo.getHBaseTbBingoSucceededCount() + ui.modelTaskInfo.getHBaseTbBingoFailedCount() &&
                        ui.modelTaskInfo.getValidCount() == ui.modelTaskInfo.getESTbBingoSucceededCount() + ui.modelTaskInfo.getESTbBingoFailedCount()) {
                    FinishJobs(ui);
                    break;
                } else if (!ui.modelConfigInfo.getHBaseTbHashChecked() &&
                        ui.modelConfigInfo.getESTbHashChecked() &&
                        ui.modelConfigInfo.getHBaseTbBingoChecked() &&
                        ui.modelConfigInfo.getESTbBingoChecked() &&
                        ui.modelConfigInfo.getWriteESTbHashFinished() &&
                        ui.modelConfigInfo.getWriteHBaseTbBingoFinished() &&
                        ui.modelConfigInfo.getWriteESTbBingoFinished() &&
                        ui.modelTaskInfo.getValidCount() == ui.modelTaskInfo.getHBaseTbBingoSucceededCount() + ui.modelTaskInfo.getHBaseTbBingoFailedCount() &&
                        ui.modelTaskInfo.getValidCount() == ui.modelTaskInfo.getESTbBingoSucceededCount() + ui.modelTaskInfo.getESTbBingoFailedCount()) {
                    FinishJobs(ui);
                    break;
                } else if (ui.modelConfigInfo.getHBaseTbHashChecked() &&
                        ui.modelConfigInfo.getESTbHashChecked() &&
                        ui.modelConfigInfo.getHBaseTbBingoChecked() &&
                        ui.modelConfigInfo.getESTbBingoChecked() &&
                        ui.modelConfigInfo.getWriteHBaseTbHashFinished() &&
                        ui.modelConfigInfo.getWriteESTbHashFinished() &&
                        ui.modelConfigInfo.getWriteHBaseTbBingoFinished() &&
                        ui.modelConfigInfo.getWriteESTbBingoFinished() &&
                        ui.modelTaskInfo.getValidCount() == ui.modelTaskInfo.getHBaseTbBingoSucceededCount() + ui.modelTaskInfo.getHBaseTbBingoFailedCount() &&
                        ui.modelTaskInfo.getValidCount() == ui.modelTaskInfo.getESTbBingoSucceededCount() + ui.modelTaskInfo.getESTbBingoFailedCount()) {
                    FinishJobs(ui);
                    break;
                } else {
                    try {
                        Thread.sleep(1000);
                        tryTimes++;
                        if (tryTimes == 120) {
                            FinishJobs(ui);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
