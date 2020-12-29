package BLL.ThreadUtils.Timer.Writer;

import DAL.DbProcessor;
import UI.BingoWriterUI;

import java.util.List;

public class HBTbBingoWriter implements Runnable {
    private BingoWriterUI ui;
    private List<String> tbBingoSqlList;

    public void SetArgs(BingoWriterUI ui, List<String> tbBingoSqlList) {
        this.ui = ui;
        this.tbBingoSqlList = tbBingoSqlList;
    }

    public void run() {
        int curThreadNumberBefore = ui.modelConfigInfo.getCurHBaseThreadNumber() + 1;
        ui.modelConfigInfo.setCurHBaseThreadNumber(curThreadNumberBefore);
        new DbProcessor().WriteIntoHBaseTbBingo(ui, tbBingoSqlList);
        int curThreadNumberAfter = ui.modelConfigInfo.getCurHBaseThreadNumber() - 1;
        ui.modelConfigInfo.setCurHBaseThreadNumber(curThreadNumberAfter);
    }
}
