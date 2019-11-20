package model;

import java.sql.Time;
import java.sql.Timestamp;

public class RentalModel {
    private final int rid;
    private final String vLicense;
    private final String dLicense;
    private final Timestamp fromDate;
    private final Timestamp toDate;
    private final int odometer;
    private final String cardName;
    private final int cardNumber;
    private final String expDate;
    private final int confNo;
    private final int rOdometer;
    private final String rFulltank;
    private final int value;
    private final Timestamp rDate;

    public RentalModel(int rid, String vLicense, String dLicense, Timestamp fromDate, Timestamp toDate, int odometer, String cardName, int cardNumber, String expDate, int confNo, int rOdometer, String rFulltank, int value, Timestamp rDate) {
        this.rid = rid;
        this.vLicense = vLicense;
        this.dLicense = dLicense;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.odometer = odometer;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.expDate = expDate;
        this.confNo = confNo;
        this.rOdometer = rOdometer;
        this.rFulltank = rFulltank;
        this.value = value;
        this.rDate = rDate;
    }

    public int getRid() {
        return rid;
    }

    public String getvLicense() {
        return vLicense;
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

    public int getOdometer() {
        return odometer;
    }

    public String getCardName() {
        return cardName;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public String getExpDate() {
        return expDate;
    }

    public int getConfNo() {
        return confNo;
    }

    public int getrOdometer() {
        return rOdometer;
    }

    public String getrFulltank() {
        return rFulltank;
    }

    public int getValue() {
        return value;
    }

    public Timestamp getrDate() {
        return rDate;
    }
}
