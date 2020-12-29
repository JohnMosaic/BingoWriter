package DAL;

import BLL.Common.*;
import Model.ModelConfigInfo;
import UI.BingoWriterUI;
import org.joda.time.DateTime;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataProcessor implements Runnable {
    private TypeChecker typeChecker = new TypeChecker();
    private HashWorker hashWorker = new HashWorker();
    private TimeMarker timeMarker = new TimeMarker();
    private StringFormatter stringFormatter = new StringFormatter();
    private LogManager logManager = new LogManager();
    private BingoWriterUI ui;

    private String GetValue(Map<String, String> kvMap, String valTip, String keyTip, String buid, List<SimpleDateFormat> sdfList) {
        String val = "";
        if (kvMap.containsKey(valTip)) {
            val = kvMap.get(valTip);
            if (val.equals("")) {
                if (keyTip.contains("*")) {
                    val = "[false]";
                }
            } else {
                if (keyTip.toLowerCase().contains("ip") && !typeChecker.IsAIP(val)) {
                    val = "";
                } else if ((keyTip.toLowerCase().contains("time") ||
                        keyTip.toLowerCase().contains("day") ||
                        keyTip.toLowerCase().contains("date")) &&
                        !typeChecker.IsADateTime(val, sdfList)) {
                    val = "";
                }
            }
        } else if (valTip.toLowerCase().contains("md5")) {
            if (kvMap.containsKey("password")) {
                val = hashWorker.StringToHash(kvMap.get("password"), "md5");
            } else {
                val = "md5";
            }
        } else if (valTip.toLowerCase().contains("sha1")) {
            if (kvMap.containsKey("password")) {
                val = hashWorker.StringToHash(kvMap.get("password"), "sha1");
            } else {
                val = "sha1";
            }
        } else if (valTip.toLowerCase().contains("sha256")) {
            if (kvMap.containsKey("password")) {
                val = hashWorker.StringToHash(kvMap.get("password"), "sha256");
            } else {
                val = "sha256";
            }
        } else if (valTip.toLowerCase().contains("sha512")) {
            if (kvMap.containsKey("password")) {
                val = hashWorker.StringToHash(kvMap.get("password"), "sha512");
            } else {
                val = "sha512";
            }
        } else if (valTip.toLowerCase().contains("datetime")) {
            val = timeMarker.GetCurrentDateTime();
        } else if (valTip.toLowerCase().contains("buid")) {
            val = buid;
        } else {
            val = valTip;
        }
        return val;
    }

    private void AddTbHashSqlList(ModelConfigInfo modelConfigInfo, List<String> tbHashSqlList, Map<String, String> kvMap, String buid, List<SimpleDateFormat> sdfList) {
        String tbHashColumns = "";
        List<String> tbHashColumnsList = modelConfigInfo.getTbHashColumnsList();
        for (String s : tbHashColumnsList) {
            tbHashColumns += (s + ",");
        }
        if (tbHashColumns.contains(",")) {
            tbHashColumns = (tbHashColumns.substring(0, tbHashColumns.lastIndexOf(","))).replace("*", "");
        }
        String tbHashValues = "";
        List<String> tbHashValuesList = modelConfigInfo.getTbHashValuesList();
        int tbHashValTipCount = tbHashValuesList.size();
        boolean isInTbHash = true;
        for (int i = 0; i < tbHashValTipCount; i++) {
            String keyTip = tbHashColumnsList.get(i);
            String valTip = tbHashValuesList.get(i);
            String val = GetValue(kvMap, valTip, keyTip, buid, sdfList);
            if (val.equals("[false]")) {
                isInTbHash = false;
                break;
            }
            tbHashValues += ("'" + val + "',");
        }
        if (isInTbHash) {
            if (tbHashValues.contains(",")) {
                tbHashValues = tbHashValues.substring(0, tbHashValues.lastIndexOf(","));
            }
            String tbHashSql = "upsert into " + modelConfigInfo.getTbHash() + " (" + tbHashColumns + ") values(" + tbHashValues + ")";
            tbHashSqlList.add(tbHashSql);
        }
    }

    private void AddTbBingoSqlList(ModelConfigInfo modelConfigInfo, List<String> tbBingoSqlList, Map<String, String> kvMap, String buid, List<SimpleDateFormat> sdfList) {
        String tbBingoColumns = "";
        List<String> tbBingoColumnsList = modelConfigInfo.getTbBingoColumnsList();
        for (String s : tbBingoColumnsList) {
            tbBingoColumns += (s + ",");
        }
        if (tbBingoColumns.contains(",")) {
            tbBingoColumns = (tbBingoColumns.substring(0, tbBingoColumns.lastIndexOf(","))).replace("*", "");
        }
        String tbBingoValues = "";
        List<String> tbBingoValuesList = modelConfigInfo.getTbBingoValuesList();
        int tbBingoValTipCount = tbBingoValuesList.size();
        boolean isInTbBingo = true;
        for (int i = 0; i < tbBingoValTipCount; i++) {
            String keyTip = tbBingoColumnsList.get(i);
            String valTip = tbBingoValuesList.get(i);
            String val = GetValue(kvMap, valTip, keyTip, buid, sdfList);
            if (val.equals("[false]")) {
                isInTbBingo = false;
                break;
            }
            tbBingoValues += ("'" + val + "',");
        }
        if (isInTbBingo) {
            if (tbBingoValues.contains(",")) {
                tbBingoValues = tbBingoValues.substring(0, tbBingoValues.lastIndexOf(","));
            }
            String tbBingoSql = "upsert into " + modelConfigInfo.getTbBingo() + " (" + tbBingoColumns + ") values(" + tbBingoValues + ")";
            tbBingoSqlList.add(tbBingoSql);
        }
    }

    private void AddTbHashJsonList(ModelConfigInfo modelConfigInfo, List<String> tbHashJsonList, Map<String, String> kvMap, String buid, List<SimpleDateFormat> sdfList) {
        List<String> tbHashColumnsList = modelConfigInfo.getTbHashColumnsList();
        String jsonStr = "";
        List<String> tbHashValuesList = modelConfigInfo.getTbHashValuesList();
        int tbHashValTipCount = tbHashValuesList.size();
        boolean isInTbHash = true;
        String pkVal = "";
        for (int i = 0; i < tbHashValTipCount; i++) {
            String keyTip = tbHashColumnsList.get(i);
            String valTip = tbHashValuesList.get(i);
            String val = GetValue(kvMap, valTip, keyTip, buid, sdfList);
            if (val.equals("[false]")) {
                isInTbHash = false;
                break;
            } else {
                if (keyTip.contains("*")) {
                    pkVal = val;
                } else {
                    jsonStr += ("\"" + keyTip + "\":\"" + val + "\",");
                }
            }
        }
        if (isInTbHash) {
            String tbHashJson = "{" + jsonStr + "\"id\":\"" + pkVal + "\"}";
            tbHashJsonList.add(tbHashJson);
        }
    }

    private void AddTbBingoJsonList(ModelConfigInfo modelConfigInfo, List<String> tbBingoJsonList, Map<String, String> kvMap, String buid, List<SimpleDateFormat> sdfList) {
        List<String> tbBingoColumnsList = modelConfigInfo.getTbBingoColumnsList();
        String jsonStr = "";
        List<String> tbBingoValuesList = modelConfigInfo.getTbBingoValuesList();
        int tbBingoValTipCount = tbBingoValuesList.size();
        boolean isInTbBingo = true;
        String pkVal = "";
        for (int i = 0; i < tbBingoValTipCount; i++) {
            String keyTip = tbBingoColumnsList.get(i);
            String valTip = tbBingoValuesList.get(i);
            String val = GetValue(kvMap, valTip, keyTip, buid, sdfList);
            if (val.equals("[false]")) {
                isInTbBingo = false;
                break;
            } else {
                if (keyTip.contains("*")) {
                    pkVal = val;
                } else {
                    jsonStr += ("\"" + keyTip + "\":\"" + val + "\",");
                }
            }
        }
        if (isInTbBingo) {
            String tbBingoJson = "{" + jsonStr + "\"id\":\"" + pkVal + "\"}";
            tbBingoJsonList.add(tbBingoJson);
        }
    }

    private void SqlListCopy(List<String> tbHashSqlList, List<String> tbBingoSqlList) {
        while (true) {
            if (ui.tbHashSqlList2.size() > ui.modelConfigInfo.getCacheNumber() ||
                    ui.tbBingoSqlList2.size() > ui.modelConfigInfo.getCacheNumber()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                if (ui.modelConfigInfo.getHBaseTbHashChecked() && tbHashSqlList.size() > 0) {
                    List<String> tbHashSqlListCopy = new ArrayList<>();
                    while (true) {
                        if (!ui.modelConfigInfo.getTbHashSqlListUsed()) {
                            ui.modelConfigInfo.setTbHashSqlListUsed(true);
                            for (String strTbHash : tbHashSqlList) {
                                tbHashSqlListCopy.add(strTbHash);
                            }
                            ui.tbHashSqlList2.add(tbHashSqlListCopy);
                            ui.modelConfigInfo.setTbHashSqlListUsed(false);
                            break;
                        } else {
                            try {
                                Thread.sleep(20);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    tbHashSqlList.clear();
                }
                if (ui.modelConfigInfo.getHBaseTbBingoChecked() && tbBingoSqlList.size() > 0) {
                    List<String> tbBingoSqlListCopy = new ArrayList<>();
                    while (true) {
                        if (!ui.modelConfigInfo.getTbBingoSqlListUsed()) {
                            ui.modelConfigInfo.setTbBingoSqlListUsed(true);
                            for (String strTbBingo : tbBingoSqlList) {
                                tbBingoSqlListCopy.add(strTbBingo);
                            }
                            ui.tbBingoSqlList2.add(tbBingoSqlListCopy);
                            ui.modelConfigInfo.setTbBingoSqlListUsed(false);
                            break;
                        } else {
                            try {
                                Thread.sleep(20);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    tbBingoSqlList.clear();
                }
                break;
            }
        }
    }

    private void JsonListCopy(List<String> tbHashJsonList, List<String> tbBingoJsonList) {
        while (true) {
            if (ui.tbHashJsonList2.size() > ui.modelConfigInfo.getCacheNumber() ||
                    ui.tbBingoJsonList2.size() > ui.modelConfigInfo.getCacheNumber()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                if (ui.modelConfigInfo.getESTbHashChecked() && tbHashJsonList.size() > 0) {
                    List<String> tbHashJsonListCopy = new ArrayList<>();
                    while (true) {
                        if (!ui.modelConfigInfo.getTbHashJsonListUsed()) {
                            ui.modelConfigInfo.setTbHashJsonListUsed(true);
                            for (String tbHashJsonStr : tbHashJsonList) {
                                tbHashJsonListCopy.add(tbHashJsonStr);
                            }
                            ui.tbHashJsonList2.add(tbHashJsonListCopy);
                            ui.modelConfigInfo.setTbHashJsonListUsed(false);
                            break;
                        } else {
                            try {
                                Thread.sleep(20);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    tbHashJsonList.clear();
                }
                if (ui.modelConfigInfo.getESTbBingoChecked() && tbBingoJsonList.size() > 0) {
                    List<String> tbBingoJsonListCopy = new ArrayList<>();
                    while (true) {
                        if (!ui.modelConfigInfo.getTbBingoJsonListUsed()) {
                            ui.modelConfigInfo.setTbBingoJsonListUsed(true);
                            for (String tbBingoJsonStr : tbBingoJsonList) {
                                tbBingoJsonListCopy.add(tbBingoJsonStr);
                            }
                            ui.tbBingoJsonList2.add(tbBingoJsonListCopy);
                            ui.modelConfigInfo.setTbBingoJsonListUsed(false);
                            break;
                        } else {
                            try {
                                Thread.sleep(20);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    tbBingoJsonList.clear();
                }
                break;
            }
        }
    }

    private Map<String, String> GetKeyValueMap(List<String> strList, ModelConfigInfo modelConfigInfo, String mode) {
        Map<String, String> kvMap = new HashMap<>();
        int strArrCount = strList.size();
        for (int i = 0; i < strArrCount; i++) {
            String s = strList.get(i);
            s = stringFormatter.RemoveRedundancy(s, mode);
            if (modelConfigInfo.getMessyCodeChecked() && !s.equals("") && modelConfigInfo.getIndexTagsList().contains(i)) {
                s = stringFormatter.ReplaceMessyCode(s);
            }
            if (modelConfigInfo.getInvalidDataChecked() && !s.equals("") && modelConfigInfo.getIndexTagsList().contains(i)) {
                s = stringFormatter.ReplaceInvalidData(s);
            }
            if (modelConfigInfo.getMixedDataChecked() && !s.equals("") && modelConfigInfo.getIndexTagsList().contains(i)) {
                s = stringFormatter.RecoverMixedData(s);
            }
            kvMap.put(modelConfigInfo.getColumnsList().get(i), s.trim());
        }
        return kvMap;
    }

    private boolean WriteIntoMemoryWorker(String strLine, ModelConfigInfo mci, List<String> strList, List<String> tbHashSqlList,
                                          List<String> tbBingoSqlList, List<String> tbHashJsonList, List<String> tbBingoJsonList, List<SimpleDateFormat> sdfList) {
        Map<String, String> kvMapHB = new HashMap<>();
        Map<String, String> kvMapES = new HashMap<>();
        if (mci.getHBaseTbHashChecked() || mci.getHBaseTbBingoChecked()) {
            kvMapHB = GetKeyValueMap(strList, mci, "HB");
            if (kvMapHB == null || kvMapHB.size() <= 0) {
                return false;
            }
        }
        if (mci.getESTbHashChecked() || mci.getESTbBingoChecked()) {
            kvMapES = GetKeyValueMap(strList, mci, "ES");
            if (kvMapES == null || kvMapES.size() <= 0) {
                return false;
            }
        }
        String buid = hashWorker.StringToHash(strLine, "md5");//String.valueOf(UUID.randomUUID());
        if (mci.getHBaseTbHashChecked() && !mci.getESTbHashChecked() && !mci.getHBaseTbBingoChecked() && !mci.getESTbBingoChecked()) {
            AddTbHashSqlList(mci, tbHashSqlList, kvMapHB, buid, sdfList);//A
        } else if (!mci.getHBaseTbHashChecked() && mci.getESTbHashChecked() && !mci.getHBaseTbBingoChecked() && !mci.getESTbBingoChecked()) {
            AddTbHashJsonList(mci, tbHashJsonList, kvMapES, buid, sdfList);//B
        } else if (!mci.getHBaseTbHashChecked() && !mci.getESTbHashChecked() && mci.getHBaseTbBingoChecked() && !mci.getESTbBingoChecked()) {
            AddTbBingoSqlList(mci, tbBingoSqlList, kvMapHB, buid, sdfList);//C
        } else if (!mci.getHBaseTbHashChecked() && !mci.getESTbHashChecked() && !mci.getHBaseTbBingoChecked() && mci.getESTbBingoChecked()) {
            AddTbBingoJsonList(mci, tbBingoJsonList, kvMapES, buid, sdfList);//D
        } else if (mci.getHBaseTbHashChecked() && mci.getESTbHashChecked() && !mci.getHBaseTbBingoChecked() && !mci.getESTbBingoChecked()) {
            AddTbHashSqlList(mci, tbHashSqlList, kvMapHB, buid, sdfList);//AB
            AddTbHashJsonList(mci, tbHashJsonList, kvMapES, buid, sdfList);
        } else if (mci.getHBaseTbHashChecked() && !mci.getESTbHashChecked() && mci.getHBaseTbBingoChecked() && !mci.getESTbBingoChecked()) {
            AddTbHashSqlList(mci, tbHashSqlList, kvMapHB, buid, sdfList);//AC
            AddTbBingoSqlList(mci, tbBingoSqlList, kvMapHB, buid, sdfList);
        } else if (mci.getHBaseTbHashChecked() && !mci.getESTbHashChecked() && !mci.getHBaseTbBingoChecked() && mci.getESTbBingoChecked()) {
            AddTbHashSqlList(mci, tbHashSqlList, kvMapHB, buid, sdfList);//AD
            AddTbBingoJsonList(mci, tbBingoJsonList, kvMapES, buid, sdfList);
        } else if (!mci.getHBaseTbHashChecked() && mci.getESTbHashChecked() && mci.getHBaseTbBingoChecked() && !mci.getESTbBingoChecked()) {
            AddTbHashJsonList(mci, tbHashJsonList, kvMapES, buid, sdfList);//BC
            AddTbBingoSqlList(mci, tbBingoSqlList, kvMapHB, buid, sdfList);
        } else if (!mci.getHBaseTbHashChecked() && mci.getESTbHashChecked() && !mci.getHBaseTbBingoChecked() && mci.getESTbBingoChecked()) {
            AddTbHashJsonList(mci, tbHashJsonList, kvMapES, buid, sdfList);//BD
            AddTbBingoJsonList(mci, tbBingoJsonList, kvMapES, buid, sdfList);
        } else if (!mci.getHBaseTbHashChecked() && !mci.getESTbHashChecked() && mci.getHBaseTbBingoChecked() && mci.getESTbBingoChecked()) {
            AddTbBingoSqlList(mci, tbBingoSqlList, kvMapHB, buid, sdfList);//CD
            AddTbBingoJsonList(mci, tbBingoJsonList, kvMapES, buid, sdfList);
        } else if (mci.getHBaseTbHashChecked() && mci.getESTbHashChecked() && mci.getHBaseTbBingoChecked() && !mci.getESTbBingoChecked()) {
            AddTbHashSqlList(mci, tbHashSqlList, kvMapHB, buid, sdfList);//ABC
            AddTbHashJsonList(mci, tbHashJsonList, kvMapES, buid, sdfList);
            AddTbBingoSqlList(mci, tbBingoSqlList, kvMapHB, buid, sdfList);
        } else if (mci.getHBaseTbHashChecked() && mci.getESTbHashChecked() && !mci.getHBaseTbBingoChecked() && mci.getESTbBingoChecked()) {
            AddTbHashSqlList(mci, tbHashSqlList, kvMapHB, buid, sdfList);//ABD
            AddTbHashJsonList(mci, tbHashJsonList, kvMapES, buid, sdfList);
            AddTbBingoJsonList(mci, tbBingoJsonList, kvMapES, buid, sdfList);
        } else if (mci.getHBaseTbHashChecked() && !mci.getESTbHashChecked() && mci.getHBaseTbBingoChecked() && mci.getESTbBingoChecked()) {
            AddTbHashSqlList(mci, tbHashSqlList, kvMapHB, buid, sdfList);//ACD
            AddTbBingoSqlList(mci, tbBingoSqlList, kvMapHB, buid, sdfList);
            AddTbBingoJsonList(mci, tbBingoJsonList, kvMapES, buid, sdfList);
        } else if (!mci.getHBaseTbHashChecked() && mci.getESTbHashChecked() && mci.getHBaseTbBingoChecked() && mci.getESTbBingoChecked()) {
            AddTbHashJsonList(mci, tbHashJsonList, kvMapES, buid, sdfList);//BCD
            AddTbBingoSqlList(mci, tbBingoSqlList, kvMapHB, buid, sdfList);
            AddTbBingoJsonList(mci, tbBingoJsonList, kvMapES, buid, sdfList);
        } else if (mci.getHBaseTbHashChecked() && mci.getESTbHashChecked() && mci.getHBaseTbBingoChecked() && mci.getESTbBingoChecked()) {
            AddTbHashSqlList(mci, tbHashSqlList, kvMapHB, buid, sdfList);//ABCD
            AddTbHashJsonList(mci, tbHashJsonList, kvMapES, buid, sdfList);
            AddTbBingoSqlList(mci, tbBingoSqlList, kvMapHB, buid, sdfList);
            AddTbBingoJsonList(mci, tbBingoJsonList, kvMapES, buid, sdfList);
        }
        return true;
    }

    private void WriteIntoMemory() {
        BufferedReader reader = null;
        String strLine;
        long sqlCount = 0;
        long jsonCount = 0;
        try {
            DateTime dtStart = DateTime.now();
            ui.modelTaskInfo.setDtStart(dtStart);
            ui.lblStatusVal.setText("Writing memory...");
            logManager.LogRecord(ui.modelConfigInfo.getLogFilePath(), ui.modelConfigInfo.getInputFileName(), "Start writing memory and tables...", "DP");
            //File file = new File(ui.modelConfigInfo.getInputFile());
            //reader = new BufferedReader(new FileReader(file));
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(ui.modelConfigInfo.getInputFile()), "UTF-8"));
            List<String> tbHashSqlList = new ArrayList<>();
            List<String> tbBingoSqlList = new ArrayList<>();
            List<String> tbHashJsonList = new ArrayList<>();
            List<String> tbBingoJsonList = new ArrayList<>();
            List<SimpleDateFormat> sdfList = timeMarker.GetDateTimeFormat();
            while ((strLine = reader.readLine()) != null) {
                try {
                    if (!strLine.trim().equals("")) {
                        if (ui.modelConfigInfo.getMixedDataChecked()) {
                            strLine = stringFormatter.ReplaceMixedData(strLine);
                        }
                        if (stringFormatter.IsSeparatorCountValid(strLine, ui.modelConfigInfo.getSeparator(), ui.modelConfigInfo.getColumnNumber())) {
                            List<String> strList = stringFormatter.SplitString(strLine, ui.modelConfigInfo.getSeparator());
                            if (WriteIntoMemoryWorker(strLine, ui.modelConfigInfo, strList, tbHashSqlList, tbBingoSqlList, tbHashJsonList, tbBingoJsonList, sdfList)) {
                                sqlCount++;
                                jsonCount++;
                                long validCount = ui.modelTaskInfo.getValidCount() + 1;
                                ui.modelTaskInfo.setValidCount(validCount);
                                if (sqlCount == ui.modelConfigInfo.getHBaseBatchNumber()) {
                                    SqlListCopy(tbHashSqlList, tbBingoSqlList);
                                    sqlCount = 0;
                                }
                                if (jsonCount == ui.modelConfigInfo.getESBatchNumber()) {
                                    JsonListCopy(tbHashJsonList, tbBingoJsonList);
                                    jsonCount = 0;
                                }
                            }
                        } else {
                            if (ui.modelConfigInfo.getMixedDataChecked()) {
                                strLine = stringFormatter.RecoverMixedData(strLine);
                            }
                            long invalidCount = ui.modelTaskInfo.getInvalidCount() + 1;
                            ui.modelTaskInfo.setInvalidCount(invalidCount);
                            logManager.InvalidDataRecord(ui.modelConfigInfo.getLogFilePath(), ui.modelConfigInfo.getInputFileName(), strLine);
                        }
                    }
                } catch (Exception e) {
                    if (ui.modelConfigInfo.getMixedDataChecked()) {
                        strLine = stringFormatter.RecoverMixedData(strLine);
                    }
                    long invalidCount = ui.modelTaskInfo.getInvalidCount() + 1;
                    ui.modelTaskInfo.setInvalidCount(invalidCount);
                    logManager.LogRecord(
                            ui.modelConfigInfo.getLogFilePath(),
                            ui.modelConfigInfo.getInputFileName(),
                            e.getMessage() + " Writing memory failed...",
                            "DP"
                    );
                    logManager.InvalidDataRecord(ui.modelConfigInfo.getLogFilePath(), ui.modelConfigInfo.getInputFileName(), strLine);
                }
            }
            if (sqlCount > 0 && sqlCount < ui.modelConfigInfo.getHBaseBatchNumber()) {
                SqlListCopy(tbHashSqlList, tbBingoSqlList);
            }
            if (jsonCount > 0 && jsonCount < ui.modelConfigInfo.getESBatchNumber()) {
                JsonListCopy(tbHashJsonList, tbBingoJsonList);
            }
        } catch (Exception e) {
            logManager.LogRecord(
                    ui.modelConfigInfo.getLogFilePath(),
                    ui.modelConfigInfo.getInputFileName(),
                    e.getMessage() + " Writing memory failed...",
                    "DP"
            );
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ui.modelConfigInfo.setWriteMemFinished(true);
            ui.lblStatusVal.setText("Complete write memory...");
            logManager.LogRecord(
                    ui.modelConfigInfo.getLogFilePath(),
                    ui.modelConfigInfo.getInputFileName(),
                    "Complete write memory...",
                    "DP"
            );
        }
    }

    public void SetArgs(BingoWriterUI ui) {
        this.ui = ui;
    }

    public void run() {
        WriteIntoMemory();
    }
}
