package model;

public class VehicleTypes {
    private final String vtName;
    private final String features;
    private final int wRate;
    private final int dRate;
    private final int hRate;
    private final int wIRate;
    private final int dIRate;
    private final int hIRate;
    private final int kRate;

    public VehicleTypes(String vtName, String features, int wRate, int dRate, int hRate, int wIRate, int dIRate, int hIRate, int kRate) {
        this.vtName = vtName;
        this.features = features;
        this.wRate = wRate;
        this.dRate = dRate;
        this.hRate = hRate;
        this.wIRate = wIRate;
        this.dIRate = dIRate;
        this.hIRate = hIRate;
        this.kRate = kRate;
    }

    public String getVtName() {
        return vtName;
    }

    public String getFeatures() {
        return features;
    }

    public int getwRate() {
        return wRate;
    }

    public int getdRate() {
        return dRate;
    }

    public int gethRate() {
        return hRate;
    }

    public int getwIRate() {
        return wIRate;
    }

    public int getdIRate() {
        return dIRate;
    }

    public int gethIRate() {
        return hIRate;
    }

    public int getkRate() {
        return kRate;
    }
}
