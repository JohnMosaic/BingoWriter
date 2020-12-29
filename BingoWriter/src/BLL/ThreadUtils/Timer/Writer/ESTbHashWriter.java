package BLL.ThreadUtils.Timer.Writer;

import DAL.DbProcessor;
import UI.BingoWriterUI;

import java.util.List;

public class ESTbHashWriter implements Runnable{
    private BingoWriterUI ui;
    private List<String> tbHashJsonList;

    public void SetArgs(BingoWriterUI ui, List<String> tbHashJsonList) {
        this.ui = ui;
        this.tbHashJsonList = tbHashJsonList;
    }

    public void run() {
        int curThreadNumberBefore = ui.modelConfigInfo.getCurESThreadNumber() + 1;
        ui.modelConfigInfo.setCurESThreadNumber(curThreadNumberBefore);
        new DbProcessor().WriteIntoESTbHash(ui, tbHashJsonList);
        int curThreadNumberAfter = ui.modelConfigInfo.getCurESThreadNumber() - 1;
        ui.modelConfigInfo.setCurESThreadNumber(curThreadNumberAfter);
    }
}
