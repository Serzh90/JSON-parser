package util;

public class RecordResult {
    private int recordId;
    private String flag;

    public RecordResult(int recordId, String flag) {
        this.recordId = recordId;
        this.flag = flag;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
