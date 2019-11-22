package model;

import java.util.ArrayList;

public class ReportModel {
    private final String location;
    private final String city;
    private final int totalNumber;
    private ArrayList<Integer> typeNumbers= new ArrayList<Integer>();
    private ArrayList<String> typeNames= new ArrayList<String>();

    public ReportModel(String location, String city, int totalNumber, ArrayList<Integer> typeNumbers, ArrayList<String> typeNames) {
        this.location = location;
        this.city = city;
        this.totalNumber = totalNumber;
        this.typeNumbers = typeNumbers;
        this.typeNames = typeNames;
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

    public ArrayList<Integer> getTypeNumbers() {
        return typeNumbers;
    }

    public ArrayList<String> getTypeNames() {
        return typeNames;
    }

    @Override
    public String toString() {
        String res = city + ", " + location +" - Total: " + totalNumber +"\nType details: ";
        int i =0;
        while(i<typeNumbers.size() && i<typeNames.size()){
            res = res + typeNames.get(i) + ":\t" + typeNumbers.get(i);
        }
        return res;
    }
}
