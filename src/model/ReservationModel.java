package model;

public class ReservationModel {
    private final int confNo;
    private final String vtName;
    private final String dLicense;
    private final String fromDate;
    private final String fromTime;
    private final String toDate;
    private final String toTime;


    public ReservationModel(int confNo, String vtName, String dLicense, String fromDate, String fromTime, String toDate, String toTime) {
        this.vtName = vtName;
        this.confNo = confNo;
        this.dLicense = dLicense;
        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.toDate = toDate;
        this.toTime = toTime;
    }

    public int getConfNo() {
        return confNo;
    }

    public String getVtName() {
        return vtName;
    }

    public String getdLicense() {
        return dLicense;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getFromTime() {
        return fromTime;
    }

    public String getToDate() {
        return toDate;
    }

    public String getToTime() {
        return toTime;
    }
}
