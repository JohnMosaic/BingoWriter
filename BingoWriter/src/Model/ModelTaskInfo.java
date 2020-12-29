package Model;

import org.joda.time.DateTime;

public class ModelTaskInfo {
    private long validCount = 0;
    private long invalidCount = 0;
    private DateTime dtStart;
    private long hBaseTbHashSucceededCount = 0;
    private long hBaseTbHashFailedCount = 0;
    private long hBaseTbHashElapsedTime = 0;
    private int hBaseTbHashMaxRate = 0;
    private long hBaseTbBingoSucceededCount = 0;
    private long hBaseTbBingoFailedCount = 0;
    private long hBaseTbBingoElapsedTime = 0;
    private int hBaseTbBingoMaxRate = 0;
    private long esTbHashSucceededCount = 0;
    private long esTbHashFailedCount = 0;
    private long esTbHashElapsedTime = 0;
    private int esTbHashMaxRate = 0;
    private long esTbBingoSucceededCount = 0;
    private long esTbBingoFailedCount = 0;
    private long esTbBingoElapsedTime = 0;
    private int esTbBingoMaxRate = 0;

    public long getValidCount() {
        return validCount;
    }

    public void setValidCount(long validCount) {
        this.validCount = validCount;
    }

    public long getInvalidCount() {
        return invalidCount;
    }

    public void setInvalidCount(long invalidCount) {
        this.invalidCount = invalidCount;
    }

    public DateTime getDtStart() {
        return dtStart;
    }

    public void setDtStart(DateTime dtStart) {
        this.dtStart = dtStart;
    }

    public long getHBaseTbHashSucceededCount() {
        return hBaseTbHashSucceededCount;
    }

    public void setHBaseTbHashSucceededCount(long hBaseTbHashSucceededCount) {
        this.hBaseTbHashSucceededCount = hBaseTbHashSucceededCount;
    }

    public long getHBaseTbHashFailedCount() {
        return hBaseTbHashFailedCount;
    }

    public void setHBaseTbHashFailedCount(long hBaseTbHashFailedCount) {
        this.hBaseTbHashFailedCount = hBaseTbHashFailedCount;
    }

    public long getHBaseTbHashElapsedTime() {
        return hBaseTbHashElapsedTime;
    }

    public void setHBaseTbHashElapsedTime(long hBaseTbHashElapsedTime) {
        this.hBaseTbHashElapsedTime = hBaseTbHashElapsedTime;
    }

    public int getHBaseTbHashMaxRate() {
        return hBaseTbHashMaxRate;
    }

    public void setHBaseTbHashMaxRate(int hBaseTbHashMaxRate) {
        this.hBaseTbHashMaxRate = hBaseTbHashMaxRate;
    }

    public long getHBaseTbBingoSucceededCount() {
        return hBaseTbBingoSucceededCount;
    }

    public void setHBaseTbBingoSucceededCount(long hBaseTbBingoSucceededCount) {
        this.hBaseTbBingoSucceededCount = hBaseTbBingoSucceededCount;
    }

    public long getHBaseTbBingoFailedCount() {
        return hBaseTbBingoFailedCount;
    }

    public void setHBaseTbBingoFailedCount(long hBaseTbBingoFailedCount) {
        this.hBaseTbBingoFailedCount = hBaseTbBingoFailedCount;
    }

    public long getHBaseTbBingoElapsedTime() {
        return hBaseTbBingoElapsedTime;
    }

    public void setHBaseTbBingoElapsedTime(long hBaseTbBingoElapsedTime) {
        this.hBaseTbBingoElapsedTime = hBaseTbBingoElapsedTime;
    }

    public int getHBaseTbBingoMaxRate() {
        return hBaseTbBingoMaxRate;
    }

    public void setHBaseTbBingoMaxRate(int hBaseTbBingoMaxRate) {
        this.hBaseTbBingoMaxRate = hBaseTbBingoMaxRate;
    }

    public long getESTbHashSucceededCount() {
        return esTbHashSucceededCount;
    }

    public void setESTbHashSucceededCount(long esTbHashSucceededCount) {
        this.esTbHashSucceededCount = esTbHashSucceededCount;
    }

    public long getESTbHashFailedCount() {
        return esTbHashFailedCount;
    }

    public void setESTbHashFailedCount(long esTbHashFailedCount) {
        this.esTbHashFailedCount = esTbHashFailedCount;
    }

    public long getESTbHashElapsedTime() {
        return esTbHashElapsedTime;
    }

    public void setESTbHashElapsedTime(long esTbHashElapsedTime) {
        this.esTbHashElapsedTime = esTbHashElapsedTime;
    }

    public int getESTbHashMaxRate() {
        return esTbHashMaxRate;
    }

    public void setESTbHashMaxRate(int esTbHashMaxRate) {
        this.esTbHashMaxRate = esTbHashMaxRate;
    }

    public long getESTbBingoSucceededCount() {
        return esTbBingoSucceededCount;
    }

    public void setESTbBingoSucceededCount(long esTbBingoSucceededCount) {
        this.esTbBingoSucceededCount = esTbBingoSucceededCount;
    }

    public long getESTbBingoFailedCount() {
        return esTbBingoFailedCount;
    }

    public void setESTbBingoFailedCount(long esTbBingoFailedCount) {
        this.esTbBingoFailedCount = esTbBingoFailedCount;
    }

    public long getESTbBingoElapsedTime() {
        return esTbBingoElapsedTime;
    }

    public void setESTbBingoElapsedTime(long esTbBingoElapsedTime) {
        this.esTbBingoElapsedTime = esTbBingoElapsedTime;
    }

    public int getESTbBingoMaxRate() {
        return esTbBingoMaxRate;
    }

    public void setESTbBingoMaxRate(int esTbBingoMaxRate) {
        this.esTbBingoMaxRate = esTbBingoMaxRate;
    }
}
