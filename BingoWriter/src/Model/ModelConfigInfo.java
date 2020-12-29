package Model;

import java.util.List;

public class ModelConfigInfo {
    private String zkHost;
    private int zkPort;
    private boolean isZKChecked = true;
    private int saltBuckets;
    private String esHost;
    private int esPort;
    private boolean isESChecked = true;
    private String esCluster;
    private String separator;
    private int cacheNumber;
    private List<String> columnsList;
    private int columnNumber;
    private boolean isMessyCodeChecked = true;
    private boolean isInvalidDataChecked = true;
    private boolean isMixedDataChecked = false;
    private List<Integer> indexTagsList;
    private String inputFile;
    private String tbHash;
    private boolean isHBaseTbHashChecked = false;
    private boolean isESTbHashChecked = true;
    private int tbHashIndexNumber;
    private List<String> tbHashColumnsList;
    private List<String> tbHashValuesList;
    private String tbBingo;
    private boolean isHBaseTbBingoChecked = true;
    private boolean isESTbBingoChecked = true;
    private int tbBingoIndexNumber;
    private List<String> tbBingoColumnsList;
    private List<String> tbBingoValuesList;
    private int hBaseMaxThreads;
    private int hBaseBatchNumber;
    private int esMaxThreads;
    private int esBatchNumber;
    private String logFilePath;
    private String inputFileName;
    private boolean isTbHashSqlListUsed = false;
    private boolean isTbBingoSqlListUsed = false;
    private boolean isTbHashJsonListUsed = false;
    private boolean isTbBingoJsonListUsed = false;
    private boolean isWriteMemFinished = false;
    private boolean isWriteHBaseTbHashFinished = false;
    private boolean isWriteHBaseTbBingoFinished = false;
    private boolean isWriteESTbHashFinished = false;
    private boolean isWriteESTbBingoFinished = false;
    private int curHBaseThreadNumber;
    private int curESThreadNumber;

    public String getZKHost() {
        return zkHost;
    }

    public void setZKHost(String zkHost) {
        this.zkHost = zkHost;
    }

    public int getZKPort() {
        return zkPort;
    }

    public void setZKPort(int zkPort) {
        this.zkPort = zkPort;
    }

    public boolean getZKChecked() {
        return isZKChecked;
    }

    public void setZKChecked(boolean isZKChecked) {
        this.isZKChecked = isZKChecked;
    }

    public int getSaltBuckets() {
        return saltBuckets;
    }

    public void setSaltBuckets(int saltBuckets) {
        this.saltBuckets = saltBuckets;
    }

    public String getESHost() {
        return esHost;
    }

    public void setESHost(String esHost) {
        this.esHost = esHost;
    }

    public int getESPort() {
        return esPort;
    }

    public void setESPort(int esPort) {
        this.esPort = esPort;
    }

    public boolean getESChecked() {
        return isESChecked;
    }

    public void setESChecked(boolean isESChecked) {
        this.isESChecked = isESChecked;
    }

    public String getESCluster() {
        return esCluster;
    }

    public void setESCluster(String esCluster) {
        this.esCluster = esCluster;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public int getCacheNumber() {
        return cacheNumber;
    }

    public void setCacheNumber(int cacheNumber) {
        this.cacheNumber = cacheNumber;
    }

    public List<String> getColumnsList() {
        return columnsList;
    }

    public void setColumnsList(List<String> columnsList) {
        this.columnsList = columnsList;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public boolean getMessyCodeChecked() {
        return isMessyCodeChecked;
    }

    public void setMessyCodeChecked(boolean isMessyCodeChecked) {
        this.isMessyCodeChecked = isMessyCodeChecked;
    }

    public boolean getInvalidDataChecked() {
        return isInvalidDataChecked;
    }

    public void setInvalidDataChecked(boolean isInvalidDataChecked) {
        this.isInvalidDataChecked = isInvalidDataChecked;
    }

    public boolean getMixedDataChecked() {
        return isMixedDataChecked;
    }

    public void setMixedDataChecked(boolean isMixedDataChecked) {
        this.isMixedDataChecked = isMixedDataChecked;
    }

    public List<Integer> getIndexTagsList() {
        return indexTagsList;
    }

    public void setIndexTagsList(List<Integer> indexTagsList) {
        this.indexTagsList = indexTagsList;
    }

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getTbHash() {
        return tbHash;
    }

    public void setTbHash(String tbHash) {
        this.tbHash = tbHash;
    }

    public boolean getHBaseTbHashChecked() {
        return isHBaseTbHashChecked;
    }

    public void setHBaseTbHashChecked(boolean isHBaseTbHashChecked) {
        this.isHBaseTbHashChecked = isHBaseTbHashChecked;
    }

    public boolean getESTbHashChecked() {
        return isESTbHashChecked;
    }

    public void setESTbHashChecked(boolean isESTbHashChecked) {
        this.isESTbHashChecked = isESTbHashChecked;
    }

    public int getTbHashIndexNumber() {
        return tbHashIndexNumber;
    }

    public void setTbHashIndexNumber(int tbHashIndexNumber) {
        this.tbHashIndexNumber = tbHashIndexNumber;
    }

    public List<String> getTbHashColumnsList() {
        return tbHashColumnsList;
    }

    public void setTbHashColumnsList(List<String> tbHashColumnsList) {
        this.tbHashColumnsList = tbHashColumnsList;
    }

    public String getTbBingo() {
        return tbBingo;
    }

    public void setTbBingo(String tbBingo) {
        this.tbBingo = tbBingo;
    }

    public boolean getHBaseTbBingoChecked() {
        return isHBaseTbBingoChecked;
    }

    public void setHBaseTbBingoChecked(boolean isHBaseTbBingoChecked) {
        this.isHBaseTbBingoChecked = isHBaseTbBingoChecked;
    }

    public boolean getESTbBingoChecked() {
        return isESTbBingoChecked;
    }

    public void setESTbBingoChecked(boolean isESTbBingoChecked) {
        this.isESTbBingoChecked = isESTbBingoChecked;
    }

    public int getTbBingoIndexNumber() {
        return tbBingoIndexNumber;
    }

    public void setTbBingoIndexNumber(int tbBingoIndexNumber) {
        this.tbBingoIndexNumber = tbBingoIndexNumber;
    }

    public List<String> getTbHashValuesList() {
        return tbHashValuesList;
    }

    public void setTbHashValuesList(List<String> tbHashValuesList) {
        this.tbHashValuesList = tbHashValuesList;
    }

    public List<String> getTbBingoColumnsList() {
        return tbBingoColumnsList;
    }

    public void setTbBingoColumnsList(List<String> tbBingoColumnsList) {
        this.tbBingoColumnsList = tbBingoColumnsList;
    }

    public List<String> getTbBingoValuesList() {
        return tbBingoValuesList;
    }

    public void setTbBingoValuesList(List<String> tbBingoValuesList) {
        this.tbBingoValuesList = tbBingoValuesList;
    }

    public int getHBaseMaxThreads() {
        return hBaseMaxThreads;
    }

    public void setHBaseMaxThreads(int hBaseMaxThreads) {
        this.hBaseMaxThreads = hBaseMaxThreads;
    }

    public int getHBaseBatchNumber() {
        return hBaseBatchNumber;
    }

    public void setHBaseBatchNumber(int hBaseBatchNumber) {
        this.hBaseBatchNumber = hBaseBatchNumber;
    }

    public int getESMaxThreads() {
        return esMaxThreads;
    }

    public void setESMaxThreads(int esMaxThreads) {
        this.esMaxThreads = esMaxThreads;
    }

    public int getESBatchNumber() {
        return esBatchNumber;
    }

    public void setESBatchNumber(int esBatchNumber) {
        this.esBatchNumber = esBatchNumber;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public boolean getTbHashSqlListUsed() {
        return isTbHashSqlListUsed;
    }

    public void setTbHashSqlListUsed(boolean isTbHashSqlListUsed) {
        this.isTbHashSqlListUsed = isTbHashSqlListUsed;
    }

    public boolean getTbBingoSqlListUsed() {
        return isTbBingoSqlListUsed;
    }

    public void setTbBingoSqlListUsed(boolean isTbBingoSqlListUsed) {
        this.isTbBingoSqlListUsed = isTbBingoSqlListUsed;
    }

    public boolean getTbHashJsonListUsed() {
        return isTbHashJsonListUsed;
    }

    public void setTbHashJsonListUsed(boolean isTbHashJsonListUsed) {
        this.isTbHashJsonListUsed = isTbHashJsonListUsed;
    }

    public boolean getTbBingoJsonListUsed() {
        return isTbBingoJsonListUsed;
    }

    public void setTbBingoJsonListUsed(boolean isTbBingoJsonListUsed) {
        this.isTbBingoJsonListUsed = isTbBingoJsonListUsed;
    }

    public boolean getWriteMemFinished() {
        return isWriteMemFinished;
    }

    public void setWriteMemFinished(boolean isWriteMemFinished) {
        this.isWriteMemFinished = isWriteMemFinished;
    }

    public boolean getWriteHBaseTbHashFinished() {
        return isWriteHBaseTbHashFinished;
    }

    public void setWriteHBaseTbHashFinished(boolean isWriteHBaseTbHashFinished) {
        this.isWriteHBaseTbHashFinished = isWriteHBaseTbHashFinished;
    }

    public boolean getWriteHBaseTbBingoFinished() {
        return isWriteHBaseTbBingoFinished;
    }

    public void setWriteHBaseTbBingoFinished(boolean isWriteHBaseTbBingoFinished) {
        this.isWriteHBaseTbBingoFinished = isWriteHBaseTbBingoFinished;
    }

    public boolean getWriteESTbHashFinished() {
        return isWriteESTbHashFinished;
    }

    public void setWriteESTbHashFinished(boolean isWriteESTbHashFinished) {
        this.isWriteESTbHashFinished = isWriteESTbHashFinished;
    }

    public boolean getWriteESTbBingoFinished() {
        return isWriteESTbBingoFinished;
    }

    public void setWriteESTbBingoFinished(boolean isWriteESTbBingoFinished) {
        this.isWriteESTbBingoFinished = isWriteESTbBingoFinished;
    }

    public int getCurHBaseThreadNumber() {
        return curHBaseThreadNumber;
    }

    public void setCurHBaseThreadNumber(int curHBaseThreadNumber) {
        this.curHBaseThreadNumber = curHBaseThreadNumber;
    }

    public int getCurESThreadNumber() {
        return curESThreadNumber;
    }

    public void setCurESThreadNumber(int curESThreadNumber) {
        this.curESThreadNumber = curESThreadNumber;
    }
}
