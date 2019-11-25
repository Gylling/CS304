package model;

public class ReportModel {
    private final String location;
    private final String city;
    private final String vtType;
    private final int vtCount;
    private final int tRevenue;
    private final int cRevenue;

    private int totalNumber;

    public ReportModel(String location, String city, int totalNumber,
                       int vtCount, String vtType, int cRevenue, int tRevenue) {
        this.location = location;
        this.city = city;
        this.totalNumber = totalNumber;
       this.vtCount = vtCount;
       this.vtType = vtType;
       this.cRevenue = cRevenue;
       this.tRevenue = tRevenue;
    }

    public String getLocation() {
        return location;
    }

    public String getCity() {
        return city;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public int getVtCount() {
        return vtCount;
    }

    public String getVtType() {
        return vtType;
    }

    public int getTRevenue(){ return tRevenue;}

    public int getCRevenue(){ return cRevenue;}


}
