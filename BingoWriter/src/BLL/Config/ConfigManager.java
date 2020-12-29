package BLL.Config;

import BLL.Common.Base64Worker;
import BLL.Common.TypeChecker;
import Model.ModelConfigInfo;
import UI.BingoWriterUI;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigManager {
    private Base64Worker base64Worker = new Base64Worker();

    private String getConfigFilePath() {
        String configFilePath = "";
        try {
            String realPath = ConfigManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            File file = new File(realPath);
            realPath = file.getAbsolutePath();
            if (realPath.contains("\\")) {
                configFilePath = realPath.substring(0, realPath.lastIndexOf("\\")) + "\\BingoWriter.conf";
            } else if (realPath.contains("/")) {
                configFilePath = realPath.substring(0, realPath.lastIndexOf("/")) + "/BingoWriter.conf";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configFilePath;
    }

    public boolean IsConfigFileExist() {
        boolean ret;
        try {
            ret = new File(getConfigFilePath()).exists();
        } catch (Exception e) {
            ret = false;
        }
        return ret;
    }

    public boolean ConfigWrite(ModelConfigInfo modelConfigInfo) {
        boolean ret = true;
        FileOutputStream fos = null;
        try {
            String zkHost = modelConfigInfo.getZKHost();
            String zkPort = String.valueOf(modelConfigInfo.getZKPort());
            String isZKChecked = String.valueOf(modelConfigInfo.getZKChecked());
            String saltBuckets = String.valueOf(modelConfigInfo.getSaltBuckets());
            String esHost = modelConfigInfo.getESHost();
            String esPort = String.valueOf(modelConfigInfo.getESPort());
            String isESChecked = String.valueOf(modelConfigInfo.getESChecked());
            String esCluster = modelConfigInfo.getESCluster();
            String separator = modelConfigInfo.getSeparator();
            String cacheNumber = String.valueOf(modelConfigInfo.getCacheNumber());
            String columns = "";
            for (String s : modelConfigInfo.getColumnsList()) {
                columns += (s + ",");
            }
            if (columns.contains(",")) {
                columns = columns.substring(0, columns.lastIndexOf(","));
            }
            String columnNumber = String.valueOf(modelConfigInfo.getColumnNumber());
            String isMessyCodeChecked = String.valueOf(modelConfigInfo.getMessyCodeChecked());
            String isInvalidDataChecked = String.valueOf(modelConfigInfo.getInvalidDataChecked());
            String isMixedDataChecked = String.valueOf(modelConfigInfo.getMixedDataChecked());
            String indexTags = "";
            for (int i : modelConfigInfo.getIndexTagsList()) {
                indexTags += (String.valueOf(i) + ",");
            }
            if (indexTags.contains(",")) {
                indexTags = indexTags.substring(0, indexTags.lastIndexOf(","));
            }
            String inputFile = modelConfigInfo.getInputFile();
            String tbHash = modelConfigInfo.getTbHash();
            String isHBaseTbHashChecked = String.valueOf(modelConfigInfo.getHBaseTbHashChecked());
            String isESTbHashChecked = String.valueOf(modelConfigInfo.getESTbHashChecked());
            String tbHashIndexNumber = String.valueOf(modelConfigInfo.getTbHashIndexNumber());
            String tbHashColumns = "";
            for (String s : modelConfigInfo.getTbHashColumnsList()) {
                tbHashColumns += (s + ",");
            }
            if (tbHashColumns.contains(",")) {
                tbHashColumns = tbHashColumns.substring(0, tbHashColumns.lastIndexOf(","));
            }
            String tbHashValues = "";
            for (String s : modelConfigInfo.getTbHashValuesList()) {
                tbHashValues += (s + ",");
            }
            if (tbHashValues.contains(",")) {
                tbHashValues = tbHashValues.substring(0, tbHashValues.lastIndexOf(","));
            }
            String tbBingo = modelConfigInfo.getTbBingo();
            String isHBaseTbBingoChecked = String.valueOf(modelConfigInfo.getHBaseTbBingoChecked());
            String isESTbBingoChecked = String.valueOf(modelConfigInfo.getESTbBingoChecked());
            String tbBingoIndexNumber = String.valueOf(modelConfigInfo.getTbBingoIndexNumber());
            String tbBingoColumns = "";
            for (String s : modelConfigInfo.getTbBingoColumnsList()) {
                tbBingoColumns += (s + ",");
            }
            if (tbBingoColumns.contains(",")) {
                tbBingoColumns = tbBingoColumns.substring(0, tbBingoColumns.lastIndexOf(","));
            }
            String tbBingoValues = "";
            for (String s : modelConfigInfo.getTbBingoValuesList()) {
                tbBingoValues += (s + ",");
            }
            if (tbBingoValues.contains(",")) {
                tbBingoValues = tbBingoValues.substring(0, tbBingoValues.lastIndexOf(","));
            }
            String hBaseMaxThreadNumber = String.valueOf(modelConfigInfo.getHBaseMaxThreads());
            String hBaseBatchNumber = String.valueOf(modelConfigInfo.getHBaseBatchNumber());
            String esMaxThreadNumber = String.valueOf(modelConfigInfo.getESMaxThreads());
            String esBatchNumber = String.valueOf(modelConfigInfo.getESBatchNumber());
            fos = new FileOutputStream(getConfigFilePath());
            Properties properties = new Properties();
            properties.setProperty("1", base64Worker.Base64Encode(zkHost));
            properties.setProperty("2", base64Worker.Base64Encode(zkPort));
            properties.setProperty("3", base64Worker.Base64Encode(isZKChecked));
            properties.setProperty("4", base64Worker.Base64Encode(saltBuckets));
            properties.setProperty("5", base64Worker.Base64Encode(esHost));
            properties.setProperty("6", base64Worker.Base64Encode(esPort));
            properties.setProperty("7", base64Worker.Base64Encode(isESChecked));
            properties.setProperty("8", base64Worker.Base64Encode(esCluster));
            properties.setProperty("9", base64Worker.Base64Encode(separator));
            properties.setProperty("10", base64Worker.Base64Encode(cacheNumber));
            properties.setProperty("11", base64Worker.Base64Encode(columns));
            properties.setProperty("12", base64Worker.Base64Encode(columnNumber));
            properties.setProperty("13", base64Worker.Base64Encode(isMessyCodeChecked));
            properties.setProperty("14", base64Worker.Base64Encode(isInvalidDataChecked));
            properties.setProperty("15", base64Worker.Base64Encode(isMixedDataChecked));
            properties.setProperty("16", base64Worker.Base64Encode(indexTags));
            properties.setProperty("17", base64Worker.Base64Encode(inputFile));
            properties.setProperty("18", base64Worker.Base64Encode(tbHash));
            properties.setProperty("19", base64Worker.Base64Encode(isHBaseTbHashChecked));
            properties.setProperty("20", base64Worker.Base64Encode(isESTbHashChecked));
            properties.setProperty("21", base64Worker.Base64Encode(tbHashIndexNumber));
            properties.setProperty("22", base64Worker.Base64Encode(tbHashColumns));
            properties.setProperty("23", base64Worker.Base64Encode(tbHashValues));
            properties.setProperty("24", base64Worker.Base64Encode(tbBingo));
            properties.setProperty("25", base64Worker.Base64Encode(isHBaseTbBingoChecked));
            properties.setProperty("26", base64Worker.Base64Encode(isESTbBingoChecked));
            properties.setProperty("27", base64Worker.Base64Encode(tbBingoIndexNumber));
            properties.setProperty("28", base64Worker.Base64Encode(tbBingoColumns));
            properties.setProperty("29", base64Worker.Base64Encode(tbBingoValues));
            properties.setProperty("30", base64Worker.Base64Encode(hBaseMaxThreadNumber));
            properties.setProperty("31", base64Worker.Base64Encode(hBaseBatchNumber));
            properties.setProperty("32", base64Worker.Base64Encode(esMaxThreadNumber));
            properties.setProperty("33", base64Worker.Base64Encode(esBatchNumber));
            properties.store(fos, null);
        } catch (Exception e) {
            ret = false;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public ModelConfigInfo ConfigRead() {
        ModelConfigInfo modelConfigInfo = new ModelConfigInfo();
        FileInputStream fis;
        try {
            fis = new FileInputStream(getConfigFilePath());
            Properties properties = new Properties();
            properties.load(fis);
            String zkHost = base64Worker.Base64Decode(String.valueOf(properties.getProperty("1")));
            int zkPort = Integer.parseInt(base64Worker.Base64Decode(String.valueOf(properties.getProperty("2"))));
            boolean isZKChecked = Boolean.valueOf(base64Worker.Base64Decode(String.valueOf(properties.getProperty("3"))));
            int saltBuckets = Integer.parseInt(base64Worker.Base64Decode(String.valueOf(properties.getProperty("4"))));
            String esHost = base64Worker.Base64Decode(String.valueOf(properties.getProperty("5")));
            int esPort = Integer.parseInt(base64Worker.Base64Decode(String.valueOf(properties.getProperty("6"))));
            boolean isESChecked = Boolean.valueOf(base64Worker.Base64Decode(String.valueOf(properties.getProperty("7"))));
            String esCluster = base64Worker.Base64Decode(String.valueOf(properties.getProperty("8")));
            String separator = base64Worker.Base64Decode(String.valueOf(properties.getProperty("9")));
            int cacheNumber = Integer.parseInt(base64Worker.Base64Decode(String.valueOf(properties.getProperty("10"))));
            String columns = base64Worker.Base64Decode(String.valueOf(properties.getProperty("11")));
            List<String> columnsList = new ArrayList<>();
            if (columns.contains(",")) {
                String[] sArr = columns.split(",");
                for (String s : sArr) {
                    columnsList.add(s);
                }
            } else {
                columnsList.add(columns);
            }
            int columnNumber = Integer.parseInt(base64Worker.Base64Decode(String.valueOf(properties.getProperty("12"))));
            boolean isMessyCodeChecked = Boolean.valueOf(base64Worker.Base64Decode(String.valueOf(properties.getProperty("13"))));
            boolean isInvalidDataChecked = Boolean.valueOf(base64Worker.Base64Decode(String.valueOf(properties.getProperty("14"))));
            boolean isMixedDataChecked = Boolean.valueOf(base64Worker.Base64Decode(String.valueOf(properties.getProperty("15"))));
            String indexTags = base64Worker.Base64Decode(String.valueOf(properties.getProperty("16")));
            List<Integer> indexTagsList = new ArrayList<>();
            if (indexTags.contains(",")) {
                String[] sArr = indexTags.split(",");
                for (String s : sArr) {
                    indexTagsList.add(Integer.parseInt(s));
                }
            } else {
                indexTagsList.add(Integer.parseInt(indexTags));
            }
            String inputFile = base64Worker.Base64Decode(String.valueOf(properties.getProperty("17")));
            String tbHash = base64Worker.Base64Decode(String.valueOf(properties.getProperty("18")));
            boolean isHBaseTbHashChecked = Boolean.valueOf(base64Worker.Base64Decode(String.valueOf(properties.getProperty("19"))));
            boolean isESTbHashChecked = Boolean.valueOf(base64Worker.Base64Decode(String.valueOf(properties.getProperty("20"))));
            int tbHashIndexNumber = Integer.parseInt(base64Worker.Base64Decode(String.valueOf(properties.getProperty("21"))));
            String tbHashColumns = base64Worker.Base64Decode(String.valueOf(properties.getProperty("22")));
            List<String> tbHashColumnsList = new ArrayList<>();
            if (tbHashColumns.contains(",")) {
                String[] sArr = tbHashColumns.split(",");
                for (String s : sArr) {
                    tbHashColumnsList.add(s);
                }
            } else {
                tbHashColumnsList.add(tbHashColumns);
            }
            String tbHashValues = base64Worker.Base64Decode(String.valueOf(properties.getProperty("23")));
            List<String> tbHashValuesList = new ArrayList<>();
            if (tbHashValues.contains(",")) {
                String[] sArr = tbHashValues.split(",");
                for (String s : sArr) {
                    tbHashValuesList.add(s);
                }
            } else {
                tbHashValuesList.add(tbHashValues);
            }
            String tbBingo = base64Worker.Base64Decode(String.valueOf(properties.getProperty("24")));
            boolean isHBaseTbBingoChecked = Boolean.valueOf(base64Worker.Base64Decode(String.valueOf(properties.getProperty("25"))));
            boolean isESTbBingoChecked = Boolean.valueOf(base64Worker.Base64Decode(String.valueOf(properties.getProperty("26"))));
            int tbBingoIndexNumber = Integer.parseInt(base64Worker.Base64Decode(String.valueOf(properties.getProperty("27"))));
            String tbBingoColumns = base64Worker.Base64Decode(String.valueOf(properties.getProperty("28")));
            List<String> tbBingoColumnsList = new ArrayList<>();
            if (tbBingoColumns.contains(",")) {
                String[] sArr = tbBingoColumns.split(",");
                for (String s : sArr) {
                    tbBingoColumnsList.add(s);
                }
            } else {
                tbBingoColumnsList.add(tbBingoColumns);
            }
            String tbBingoValues = base64Worker.Base64Decode(String.valueOf(properties.getProperty("29")));
            List<String> tbBingoValuesList = new ArrayList<>();
            if (tbBingoValues.contains(",")) {
                String[] sArr = tbBingoValues.split(",");
                for (String s : sArr) {
                    tbBingoValuesList.add(s);
                }
            } else {
                tbBingoValuesList.add(tbBingoValues);
            }
            int hBaseMaxThreadNumber = Integer.parseInt(base64Worker.Base64Decode(String.valueOf(properties.getProperty("30"))));
            int hBaseBatchNumber = Integer.parseInt(base64Worker.Base64Decode(String.valueOf(properties.getProperty("31"))));
            int esMaxThreadNumber = Integer.parseInt(base64Worker.Base64Decode(String.valueOf(properties.getProperty("32"))));
            int esBatchNumber = Integer.parseInt(base64Worker.Base64Decode(String.valueOf(properties.getProperty("33"))));
            modelConfigInfo.setZKHost(zkHost);
            modelConfigInfo.setZKPort(zkPort);
            modelConfigInfo.setZKChecked(isZKChecked);
            modelConfigInfo.setSaltBuckets(saltBuckets);
            modelConfigInfo.setESHost(esHost);
            modelConfigInfo.setESPort(esPort);
            modelConfigInfo.setESChecked(isESChecked);
            modelConfigInfo.setESCluster(esCluster);
            modelConfigInfo.setSeparator(separator);
            modelConfigInfo.setCacheNumber(cacheNumber);
            modelConfigInfo.setColumnsList(columnsList);
            modelConfigInfo.setColumnNumber(columnNumber);
            modelConfigInfo.setMessyCodeChecked(isMessyCodeChecked);
            modelConfigInfo.setInvalidDataChecked(isInvalidDataChecked);
            modelConfigInfo.setMixedDataChecked(isMixedDataChecked);
            modelConfigInfo.setIndexTagsList(indexTagsList);
            modelConfigInfo.setInputFile(inputFile);
            modelConfigInfo.setTbHash(tbHash);
            modelConfigInfo.setHBaseTbHashChecked(isHBaseTbHashChecked);
            modelConfigInfo.setESTbHashChecked(isESTbHashChecked);
            modelConfigInfo.setTbHashIndexNumber(tbHashIndexNumber);
            modelConfigInfo.setTbHashColumnsList(tbHashColumnsList);
            modelConfigInfo.setTbHashValuesList(tbHashValuesList);
            modelConfigInfo.setTbBingo(tbBingo);
            modelConfigInfo.setHBaseTbBingoChecked(isHBaseTbBingoChecked);
            modelConfigInfo.setESTbBingoChecked(isESTbBingoChecked);
            modelConfigInfo.setTbBingoIndexNumber(tbBingoIndexNumber);
            modelConfigInfo.setTbBingoColumnsList(tbBingoColumnsList);
            modelConfigInfo.setTbBingoValuesList(tbBingoValuesList);
            modelConfigInfo.setHBaseMaxThreads(hBaseMaxThreadNumber);
            modelConfigInfo.setHBaseBatchNumber(hBaseBatchNumber);
            modelConfigInfo.setESMaxThreads(esMaxThreadNumber);
            modelConfigInfo.setESBatchNumber(esBatchNumber);
        } catch (Exception e) {
            modelConfigInfo = null;
        }
        return modelConfigInfo;
    }

    public boolean ConfigCheck(BingoWriterUI ui) {
        try {
            TypeChecker typeChecker = new TypeChecker();
            String zkHost = ui.tfdZKHost.getText().trim();
            String zkPort = ui.tfdZKPort.getText().trim();
            boolean isZKChecked = ui.ckbZK.isSelected();
            String saltBuckets = ui.tfdSaltBuckets.getText().trim();
            String esHost = ui.tfdESHost.getText().trim();
            String esPort = ui.tfdESPort.getText().trim();
            boolean isESChecked = ui.ckbES.isSelected();
            String esCluster = ui.tfdESCluster.getText().trim();
            String separator = ui.tfdSeparator.getText();
            String cacheNumber = ui.tfdCacheNum.getText();
            String columns = ui.tfdColumns.getText().trim();
            String columnNumber = ui.tfdColumnNum.getText().trim();
            boolean isMessyCodeChecked = ui.ckbMessyCode.isSelected();
            boolean isInvalidDataChecked = ui.ckbInvalidData.isSelected();
            boolean isMixedDataChecked = ui.ckbMixedData.isSelected();
            String indexTags = ui.tfdIndexTag.getText().trim();
            String inputFile = ui.tfdInputFile.getText().trim();
            String tbHash = ui.tfdTbHash.getText().trim();
            boolean isHBaseTbHashChecked = ui.ckbHBaseTbHash.isSelected();
            boolean isESTbHashChecked = ui.ckbESTbHash.isSelected();
            int tbHashIndexNumber = GetIndexNumberFromIndex(ui.cbbTbHashIndexNum.getSelectedIndex());
            String tbHashColumns = ui.tfdTbHashColumns.getText().trim();
            String tbHashValues = ui.tfdTbHashValues.getText().trim();
            String tbBingo = ui.tfdTbBingo.getText().trim();
            boolean isHBaseTbBingoChecked = ui.ckbHBaseTbBingo.isSelected();
            boolean isESTbBingoChecked = ui.ckbESTbBingo.isSelected();
            int tbBingoIndexNumber = GetIndexNumberFromIndex(ui.cbbTbBingoIndexNum.getSelectedIndex());
            String tbBingoColumns = ui.tfdTbBingoColumns.getText().trim();
            String tbBingoValues = ui.tfdTbBingoValues.getText().trim();
            int hBaseMaxThreadNumber = GetThreadNumberFromIndex(ui.cbbHBaseMaxThreads.getSelectedIndex());
            int hBaseBatchNumber = GetBatchNumberFromIndex(ui.cbbHBaseBatchNum.getSelectedIndex());
            int esMaxThreadNumber = GetThreadNumberFromIndex(ui.cbbESMaxThreads.getSelectedIndex());
            int esBatchNumber = GetBatchNumberFromIndex(ui.cbbESBatchNum.getSelectedIndex());
            if (zkHost.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input the zookeeper host's name or ip address.\neg: (cdh1 | 192.168.222.1).",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (!typeChecker.IsAPort(zkPort)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input the zookeeper's port.\nPort range: [0, 65535].",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }else if (esHost.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input the elasticsearch host's name or ip address..\neg: (bingo4 | 192.168.222.4).",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (!typeChecker.IsAPort(esPort)) {
                JOptionPane.showMessageDialog(
                        null, "Please input the elasticsearch's port.\nPort range: [0, 65535].",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }  else if (!isESChecked && !isZKChecked) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please select one host at least.\nElasticsearch or zookeeper or both.",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (!typeChecker.IsAPositiveNumber(saltBuckets)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input the phoenix salt buckets.\nRange: (positive integer | ~= 0.5 * total cpu core number).",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (esCluster.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input the elasticsearch cluster's name.\neg: (bingo).",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (separator.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input the separator according to the content.\neg: (:).",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (!typeChecker.IsAPositiveNumber(cacheNumber)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input the cache number.\nRange: (positive integer).",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (columns.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input the column names according to the content.\neg: (user,account,ip,password).",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (!typeChecker.IsAPositiveNumber(columnNumber)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input the column number.\nRange: (positive integer).",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (indexTags.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input the data filter's index tag(s).\neg: (0 | 0,1,2,3).",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (inputFile.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input or select the file which will be cleaned.\nPath: (Absolute full path).",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (!typeChecker.IsAFile(inputFile)) {
                JOptionPane.showMessageDialog(
                        null,
                        "The file \"" + inputFile + "\" dos not exist.\nPath: (Absolute full path).",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (typeChecker.IsFileEmpty(inputFile)) {
                JOptionPane.showMessageDialog(
                        null,
                        "The file \"" + inputFile + "\" is empty.\nPath: (Absolute full path).",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (tbHash.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input the table1's name.\neg: (tb_hash).",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (tbHashColumns.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input the column names according to table1.\neg: (*PASSWORD,MD5,SHA1,SHA256,SHA512).\n\"*\" means the column is the primary key.",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (tbHashValues.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input the values according to the columns of table1.\neg: (password,md5,sha1,sha256,sha512).",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (tbBingo.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input the table2's name.\neg: (tb_bingo).",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (tbBingoColumns.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input the column names according to table2.\neg: (*GUID,USER,ACCOUNT,ACCOUNTTYPE,PASSWORD,IP,CREATETIME,DATASOURCE,DATATYPE,LEAKEDTIME,PROVIDER).\n\"*\" means the column is the primary key.",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (tbBingoValues.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please input the values according to the columns of table2.\neg: (guid,user,account,accounttype,password,ip,datetime,value,value,value,value).",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (!isHBaseTbHashChecked && !isESTbHashChecked && !isHBaseTbBingoChecked && !isESTbBingoChecked) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please select one table from the 4 tables at least .\nHbase table1 or table2, Elasticsearch table1 or table2, or all of them.",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if ((isHBaseTbHashChecked || isHBaseTbBingoChecked) && !isZKChecked) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please check the zookeeper host if hbase table1 or hbase table2 was selected.",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if ((isESTbHashChecked || isESTbBingoChecked) && !isESChecked) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please check the elasticsearch host if es table1 or es table2 was selected.",
                        "Tips",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                List<String> strColumnsList = new ArrayList<>();
                if (columns.contains(",")) {
                    String[] sArr = columns.split(",");
                    for (String s : sArr) {
                        strColumnsList.add(s);
                    }
                } else {
                    strColumnsList.add(columns);
                }
                if (strColumnsList.size() != Integer.parseInt(columnNumber)) {
                    JOptionPane.showMessageDialog(
                            null,
                            "The number of columns is not equal to the columnNumber.",
                            "Tips",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return false;
                }
                List<Integer> intIndexTagsList = new ArrayList<>();
                if (indexTags.contains(",")) {
                    String[] sArr = indexTags.split(",");
                    for (String s : sArr) {
                        if (!typeChecker.IsANumber(s)) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "The index tag(s) must be a number or a\ngroup of numbers which split with comma.\neg: (1)(0,1,2,3).",
                                    "Tips",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                            return false;
                        } else {
                            intIndexTagsList.add(Integer.parseInt(s));
                        }
                    }
                } else {
                    if (!typeChecker.IsANumber(indexTags)) {
                        JOptionPane.showMessageDialog(
                                null,
                                "The index tag(s) must be a number or a\ngroup of numbers which split with comma.\neg: (1)(0,1,2,3).",
                                "Tips",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        return false;
                    } else {
                        intIndexTagsList.add(Integer.parseInt(indexTags));
                    }
                }
                if (intIndexTagsList.size() > strColumnsList.size()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "The number of index tag(s) must be less than the number of columns.",
                            "Tips",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return false;
                }
                List<String> strTbHashColumnsList = new ArrayList<>();
                if (tbHashColumns.contains(",")) {
                    String[] sArr = tbHashColumns.split(",");
                    for (String s : sArr) {
                        strTbHashColumnsList.add(s);
                    }
                } else {
                    strTbHashColumnsList.add(tbHashColumns);
                }
                List<String> strTbHashValuesList = new ArrayList<>();
                if (tbHashValues.contains(",")) {
                    String[] sArr = tbHashValues.split(",");
                    for (String s : sArr) {
                        strTbHashValuesList.add(s);
                    }
                } else {
                    strTbHashValuesList.add(tbHashValues);
                }
                if (strTbHashColumnsList.size() != strTbHashValuesList.size()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "The number of columns is not equal to the number of values in table1.",
                            "Tips",
                            JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
                List<String> strTbBingoColumnsList = new ArrayList<>();
                if (tbBingoColumns.contains(",")) {
                    String[] sArr = tbBingoColumns.split(",");
                    for (String s : sArr) {
                        strTbBingoColumnsList.add(s);
                    }
                } else {
                    strTbBingoColumnsList.add(tbBingoColumns);
                }
                List<String> strTbBingoValuesList = new ArrayList<>();
                if (tbBingoValues.contains(",")) {
                    String[] sArr = tbBingoValues.split(",");
                    for (String s : sArr) {
                        strTbBingoValuesList.add(s);
                    }
                } else {
                    strTbBingoValuesList.add(tbBingoValues);
                }
                if (strTbBingoColumnsList.size() != strTbBingoValuesList.size()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "The number of columns is not equal to the number of values in table2.",
                            "Tips",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return false;
                }
                String logFilePath = "";
                String inputFileName = "";
                if (inputFile.contains("\\")) {
                    logFilePath = inputFile.substring(0, inputFile.lastIndexOf("\\"));
                    inputFileName = inputFile.substring(inputFile.lastIndexOf("\\") + 1);
                } else if (inputFile.contains("/")) {
                    logFilePath = inputFile.substring(0, inputFile.lastIndexOf("/"));
                    inputFileName = inputFile.substring(inputFile.lastIndexOf("/") + 1);
                }
                ui.modelConfigInfo.setZKHost(zkHost);
                ui.modelConfigInfo.setZKPort(Integer.parseInt(zkPort));
                ui.modelConfigInfo.setZKChecked(isZKChecked);
                ui.modelConfigInfo.setSaltBuckets(Integer.parseInt(saltBuckets));
                ui.modelConfigInfo.setESHost(esHost);
                ui.modelConfigInfo.setESPort(Integer.parseInt(esPort));
                ui.modelConfigInfo.setESChecked(isESChecked);
                ui.modelConfigInfo.setESCluster(esCluster);
                ui.modelConfigInfo.setSeparator(separator);
                ui.modelConfigInfo.setCacheNumber(Integer.parseInt(cacheNumber));
                ui.modelConfigInfo.setColumnsList(strColumnsList);
                ui.modelConfigInfo.setColumnNumber(Integer.parseInt(columnNumber));
                ui.modelConfigInfo.setMessyCodeChecked(isMessyCodeChecked);
                ui.modelConfigInfo.setInvalidDataChecked(isInvalidDataChecked);
                ui.modelConfigInfo.setMixedDataChecked(isMixedDataChecked);
                ui.modelConfigInfo.setIndexTagsList(intIndexTagsList);
                ui.modelConfigInfo.setInputFile(inputFile);
                ui.modelConfigInfo.setTbHash(tbHash);
                ui.modelConfigInfo.setHBaseTbHashChecked(isHBaseTbHashChecked);
                ui.modelConfigInfo.setESTbHashChecked(isESTbHashChecked);
                ui.modelConfigInfo.setTbHashIndexNumber(tbHashIndexNumber);
                ui.modelConfigInfo.setTbHashColumnsList(strTbHashColumnsList);
                ui.modelConfigInfo.setTbHashValuesList(strTbHashValuesList);
                ui.modelConfigInfo.setTbBingo(tbBingo);
                ui.modelConfigInfo.setHBaseTbBingoChecked(isHBaseTbBingoChecked);
                ui.modelConfigInfo.setESTbBingoChecked(isESTbBingoChecked);
                ui.modelConfigInfo.setTbBingoIndexNumber(tbBingoIndexNumber);
                ui.modelConfigInfo.setTbBingoColumnsList(strTbBingoColumnsList);
                ui.modelConfigInfo.setTbBingoValuesList(strTbBingoValuesList);
                ui.modelConfigInfo.setHBaseMaxThreads(hBaseMaxThreadNumber);
                ui.modelConfigInfo.setHBaseBatchNumber(hBaseBatchNumber);
                ui.modelConfigInfo.setESMaxThreads(esMaxThreadNumber);
                ui.modelConfigInfo.setESBatchNumber(esBatchNumber);
                ui.modelConfigInfo.setLogFilePath(logFilePath);
                ui.modelConfigInfo.setInputFileName(inputFileName);
                ui.modelConfigInfo.setTbHashSqlListUsed(false);
                ui.modelConfigInfo.setTbBingoSqlListUsed(false);
                ui.modelConfigInfo.setTbHashJsonListUsed(false);
                ui.modelConfigInfo.setTbBingoJsonListUsed(false);
                ui.modelConfigInfo.setWriteMemFinished(false);
                ui.modelConfigInfo.setWriteHBaseTbHashFinished(false);
                ui.modelConfigInfo.setWriteHBaseTbBingoFinished(false);
                ui.modelConfigInfo.setWriteESTbHashFinished(false);
                ui.modelConfigInfo.setWriteESTbBingoFinished(false);
                ui.modelConfigInfo.setCurHBaseThreadNumber(0);
                ui.modelConfigInfo.setCurESThreadNumber(0);
                ui.isJobsFinished = false;
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private int GetIndexNumberFromIndex(int index) {
        return index + 1;
    }

    public int GetIndexFromIndexNumber(int indexNumber) {
        return indexNumber - 1;
    }

    private int GetThreadNumberFromIndex(int index) {
        return index + 1;
    }

    public int GetIndexFromThreadNumber(int threadNumber) {
        return threadNumber - 1;
    }

    private int GetBatchNumberFromIndex(int index) {
        return (index + 1) * 10000;
    }

    public int GetIndexFromBatchNumber(int batchNumber) {
        return batchNumber / 10000 - 1;
    }
}
