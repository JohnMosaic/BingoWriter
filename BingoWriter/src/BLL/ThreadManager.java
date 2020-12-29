package BLL;

import BLL.ThreadUtils.JobWorker;
import BLL.ThreadUtils.ConnTester.ESConnTester;
import BLL.ThreadUtils.ConnTester.ZKConnTester;
import DAL.DbProcessor;
import UI.BingoWriterUI;

import javax.swing.*;

public class ThreadManager {
    public void AddTbHashColumn(BingoWriterUI ui, JFrame frame) {
        DbProcessor dbProcessor = new DbProcessor();
        dbProcessor.SetArgs(ui, frame, "tb1");
        new Thread(dbProcessor).start();
    }

    public void AddTbBingoColumn(BingoWriterUI ui, JFrame frame) {
        DbProcessor dbProcessor = new DbProcessor();
        dbProcessor.SetArgs(ui, frame, "tb2");
        new Thread(dbProcessor).start();
    }

    public void TestZKConnThread(BingoWriterUI ui, JFrame frame) {
        ZKConnTester zkConnTester = new ZKConnTester();
        zkConnTester.SetArgs(ui, frame);
        new Thread(zkConnTester).start();
    }

    public void TestESConnThread(BingoWriterUI ui, JFrame frame) {
        ESConnTester esConnTester = new ESConnTester();
        esConnTester.SetArgs(ui, frame);
        new Thread(esConnTester).start();
    }

    public void StartAllTaskThreads(BingoWriterUI ui, JFrame frame) {
        JobWorker jobWorker = new JobWorker();
        jobWorker.SetArgs(ui, frame);
        new Thread(jobWorker).start();
    }
}
