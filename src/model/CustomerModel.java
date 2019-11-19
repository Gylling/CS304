package model;

public class CustomerModel {
    private final String dLicense;
    private final String name;
    private final String address;

    public CustomerModel(String dLicense, String name, String address){
        this.dLicense=dLicense;
        this.name=name;
        this.address=address;
    }

    public String getdLicense() {
        return dLicense;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
