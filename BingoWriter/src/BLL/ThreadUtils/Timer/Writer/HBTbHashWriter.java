package BLL.ThreadUtils.Timer.Writer;

import DAL.DbProcessor;
import UI.BingoWriterUI;

import java.util.List;

public class HBTbHashWriter implements Runnable {
    private BingoWriterUI ui;
    private List<String> tbHashSqlList;

    public void SetArgs(BingoWriterUI ui, List<String> tbHashSqlList) {
        this.ui = ui;
        this.tbHashSqlList = tbHashSqlList;
    }

    public void run() {
        int curThreadNumberBefore = ui.modelConfigInfo.getCurHBaseThreadNumber() + 1;
        ui.modelConfigInfo.setCurHBaseThreadNumber(curThreadNumberBefore);
        new DbProcessor().WriteIntoHBaseTbHash(ui, tbHashSqlList);
        int curThreadNumberAfter = ui.modelConfigInfo.getCurHBaseThreadNumber() - 1;
        ui.modelConfigInfo.setCurHBaseThreadNumber(curThreadNumberAfter);
    }
}
