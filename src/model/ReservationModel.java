package model;

import java.sql.Time;
import java.sql.Timestamp;

public class ReservationModel {
    private final int confNo;
    private final String vtName;
    private final String dLicense;
    private final Timestamp fromDate;
    private final Timestamp toDate;


    public ReservationModel(int confNo, String vtName, String dLicense, Timestamp fromDate, Timestamp toDate) {
        this.vtName = vtName;
        this.confNo = confNo;
        this.dLicense = dLicense;
        this.fromDate = fromDate;
        this.toDate = toDate;
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

    public Timestamp getFromDate() {
        return fromDate;
    }

    public Timestamp getToDate() {
        return toDate;
    }

}
