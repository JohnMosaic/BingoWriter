package BLL.ThreadUtils.Timer.Writer;

import DAL.DbProcessor;
import UI.BingoWriterUI;

import java.util.List;

public class ESTbBingoWriter implements Runnable{
    private BingoWriterUI ui;
    private List<String> tbBingoJsonList;

    public void SetArgs(BingoWriterUI ui, List<String> tbBingoJsonList) {
        this.ui = ui;
        this.tbBingoJsonList = tbBingoJsonList;
    }

    public void run() {
        int curThreadNumberBefore = ui.modelConfigInfo.getCurESThreadNumber() + 1;
        ui.modelConfigInfo.setCurESThreadNumber(curThreadNumberBefore);
        new DbProcessor().WriteIntoESTbBingo(ui, tbBingoJsonList);
        int curThreadNumberAfter = ui.modelConfigInfo.getCurESThreadNumber() - 1;
        ui.modelConfigInfo.setCurESThreadNumber(curThreadNumberAfter);
    }
}
