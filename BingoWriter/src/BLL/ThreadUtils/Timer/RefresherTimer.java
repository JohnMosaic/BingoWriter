package BLL.ThreadUtils.Timer;

import UI.BingoWriterUI;

import java.util.Timer;
import java.util.TimerTask;

public class RefresherTimer {
    private BingoWriterUI ui;

    public void StartRefresherTimer() {
        ui.timer = new Timer();
        ui.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ui.uiManager.UpdateTaskInfo(ui);
            }
        }, 0, 5000);
    }

    public void StopRefresherTimer() {
        if (ui.timer != null) {
            ui.timer.cancel();
        }
    }

    public void SetArgs(BingoWriterUI ui) {
        this.ui = ui;
    }
}
