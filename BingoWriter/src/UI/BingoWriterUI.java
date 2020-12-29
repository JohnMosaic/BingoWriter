package UI;

import BLL.Config.ConfigManager;
import BLL.ThreadManager;
import Model.ModelConfigInfo;
import Model.ModelTaskInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BingoWriterUI {
    private JPanel panelBG;
    private JTabbedPane tabPane;
    public JTextField tfdZKHost;
    public JTextField tfdZKPort;
    public JCheckBox ckbZK;
    public JTextField tfdSaltBuckets;
    public JTextField tfdESHost;
    public JTextField tfdESPort;
    public JCheckBox ckbES;
    public JTextField tfdESCluster;
    public JTextField tfdSeparator;
    public JTextField tfdCacheNum;
    public JTextField tfdColumns;
    public JTextField tfdColumnNum;
    public JCheckBox ckbMessyCode;
    public JCheckBox ckbInvalidData;
    public JCheckBox ckbMixedData;
    public JTextField tfdIndexTag;
    public JTextField tfdInputFile;
    private JButton btnBrowse;
    public JTextField tfdTbHash;
    public JCheckBox ckbHBaseTbHash;
    public JCheckBox ckbESTbHash;
    public JComboBox cbbTbHashIndexNum;
    public JTextField tfdAddTbHashColumn;
    private JButton btnAddTbHashColumn;
    public JTextField tfdTbHashColumns;
    public JTextField tfdTbHashValues;
    public JTextField tfdTbBingo;
    public JCheckBox ckbHBaseTbBingo;
    public JCheckBox ckbESTbBingo;
    public JComboBox cbbTbBingoIndexNum;
    public JTextField tfdAddTbBingoColumn;
    private JButton btnAddTbBingoColumn;
    public JTextField tfdTbBingoColumns;
    public JTextField tfdTbBingoValues;
    public JComboBox cbbHBaseMaxThreads;
    public JComboBox cbbHBaseBatchNum;
    public JComboBox cbbESMaxThreads;
    public JComboBox cbbESBatchNum;
    private JButton btnTestZKConn;
    private JButton btnTestESConn;
    private JButton btnStart;
    public JLabel lblValidateVal;
    public JLabel lblElapsedTimeVal;
    public JLabel lblHBaseTbHashValidateVal;
    public JLabel lblHBaseTbHashElapsedTimeVal;
    public JLabel lblHBaseTbHashMaxRateVal;
    public JLabel lblHBaseTbHashAverageRateVal;
    public JLabel lblESTbHashValidateVal;
    public JLabel lblESTbHashElapsedTimeVal;
    public JLabel lblESTbHashMaxRateVal;
    public JLabel lblESTbHashAverageRateVal;
    public JLabel lblHBaseTbBingoValidateVal;
    public JLabel lblHBaseTbBingoElapsedTimeVal;
    public JLabel lblHBaseTbBingoMaxRateVal;
    public JLabel lblHBaseTbBingoAverageRateVal;
    public JLabel lblESTbBingoValidateVal;
    public JLabel lblESTbBingoElapsedTimeVal;
    public JLabel lblESTbBingoMaxRateVal;
    public JLabel lblESTbBingoAverageRateVal;
    public JLabel lblStatusVal;
    public ModelConfigInfo modelConfigInfo = new ModelConfigInfo();
    public ModelTaskInfo modelTaskInfo = new ModelTaskInfo();
    public List<List<String>> tbHashSqlList2 = new ArrayList<>();
    public List<List<String>> tbBingoSqlList2 = new ArrayList<>();
    public List<List<String>> tbHashJsonList2 = new ArrayList<>();
    public List<List<String>> tbBingoJsonList2 = new ArrayList<>();
    public UIManager uiManager = new UIManager();
    public java.util.Timer timer;
    public boolean isJobsFinished = false;

    public static void main(String[] args) {
        BingoWriterUI bingoWriterUI = new BingoWriterUI();
        UIManager uiManagerP = new UIManager();
        JFrame frame = new JFrame("BingoWriter Version 1.0.0");
        frame.setResizable(false);
        frame.setContentPane(bingoWriterUI.panelBG);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocation(uiManagerP.GetCenterLocationPoint(frame));
        frame.setVisible(true);
        bingoWriterUI.BtnBrowseClick(bingoWriterUI);
        bingoWriterUI.BtnAddTbHashColumn(bingoWriterUI, frame);
        bingoWriterUI.BtnAddTbBingoColumn(bingoWriterUI, frame);
        bingoWriterUI.BtnTestZKConnClick(bingoWriterUI, frame);
        bingoWriterUI.BtnTestESConnClick(bingoWriterUI, frame);
        bingoWriterUI.BtnStartClick(bingoWriterUI, frame);
        uiManagerP.ReloadConfigInfo(bingoWriterUI);
    }

    private void BtnBrowseClick(BingoWriterUI bingoWriterUI) {
        btnBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FileBrowser().SelectFile(bingoWriterUI);
            }
        });
    }

    private void BtnAddTbHashColumn(BingoWriterUI bingoWriterUI, JFrame frame) {
        btnAddTbHashColumn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ThreadManager().AddTbHashColumn(bingoWriterUI, frame);
            }
        });
    }

    private void BtnAddTbBingoColumn(BingoWriterUI bingoWriterUI, JFrame frame) {
        btnAddTbBingoColumn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ThreadManager().AddTbBingoColumn(bingoWriterUI, frame);
            }
        });
    }

    private void BtnTestZKConnClick(BingoWriterUI bingoWriterUI, JFrame frame) {
        btnTestZKConn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ThreadManager().TestZKConnThread(bingoWriterUI, frame);
            }
        });
    }

    private void BtnTestESConnClick(BingoWriterUI bingoWriterUI, JFrame frame) {
        btnTestESConn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ThreadManager().TestESConnThread(bingoWriterUI, frame);
            }
        });
    }

    private void BtnStartClick(BingoWriterUI bingoWriterUI, JFrame frame) {
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (new ConfigManager().ConfigCheck(bingoWriterUI)) {
                    bingoWriterUI.tabPane.setSelectedIndex(1);
                    new ThreadManager().StartAllTaskThreads(bingoWriterUI, frame);
                }
            }
        });
    }
}
