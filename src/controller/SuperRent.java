package controller;


import  database.DatabaseConnectionHandler;
import  delegates.LoginWindowDelegate;
import  delegates.TerminalTransactionsDelegate;
import model.*;
import  ui.LoginWindow;
import  ui.TerminalTransactions;

import java.sql.Timestamp;

import static java.sql.Types.NULL;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class SuperRent implements LoginWindowDelegate, TerminalTransactionsDelegate {
    private DatabaseConnectionHandler dbHandler;
    private LoginWindow loginWindow = null;
    private static final String INFO_TAG = "[INFO] ";

    private SuperRent() {
        dbHandler = new DatabaseConnectionHandler();
    }

    private void start() {
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this);

    }

    /**
     * LoginWindowDelegate Implementation
     *
     * connects to Oracle database with supplied username and password
     */


    public void login(String username, String password) {
        boolean didConnect = dbHandler.login(username, password);

        if (didConnect) {
            // Once connected, remove login window and start text transaction flow
            loginWindow.dispose();
            TerminalTransactions transaction = new TerminalTransactions();
            transaction.showMainMenu(this);
        } else {
            loginWindow.handleLoginFailed();

            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.out.println("You have exceeded your number of allowed attempts");
                System.exit(-1);
            }
        }
    }

    //Check if customer is a new customer
    public boolean checkCustomer(String dLicense){
        boolean newCustomer = true;
        CustomerModel[] models = dbHandler.getCustomerInfo();
        for (CustomerModel a: models) {
            if(a.getdLicense().equals(dLicense)){
                newCustomer = false;
                break;
            }
        }
        return newCustomer;
    }

    //Insert new customer
    public void insertCustomer(CustomerModel model){
        dbHandler.insertCustomer(model);
    }

    public void insertReservation(ReservationModel model, boolean show) {
        VehiclesModel[] vehiclesModel = dbHandler.getAvailableVehicles("",model.getVtName(),"", "", model.getFromDate(), model.getToDate());
        if(vehiclesModel.length<1){
            System.out.println(INFO_TAG + "There is no vehicles available with the given vehicle type on the given dates.");
        } else {
            dbHandler.insertReservation(model);
            if(show) {
                System.out.println("Your confirmation number is: \t" + model.getConfNo());
                System.out.println("Your driver´s license is: \t" + model.getdLicense());
                System.out.println("The vehicle type is: \t" + model.getVtName());
                System.out.println("Start date is: \t" + model.getFromDate());
                System.out.println("End date is: \t" + model.getToDate());
            }
        }
    }

    public int lastConfNumber() {
        return dbHandler.last("CONFNO","RESERVATIONS");
    }


    public void deleteReservation(int confNo) {
        if(dbHandler.deleteReservation(confNo)){
            System.out.println("Delete complete");
        } else {
            System.out.println(INFO_TAG + "There is no reservation with that confirmation number.");
        }
    }



    public ReservationModel[] showReservation(int confNo, boolean showDetails) {
        ReservationModel[] models = dbHandler.getReservationInfo(confNo);
        if(models.length<1){
            System.out.println(INFO_TAG + "There is no reservation with that confirmation number.");
        }
        else {
            if (showDetails) {
                System.out.printf("%-20.20s", "Confirmation number");
                System.out.print("|");
                System.out.printf("%-20.20s", "Vehicle Type");
                System.out.print("|");
                System.out.printf("%-20.20s", "Driver's License");
                System.out.print("|");
                System.out.printf("%-30.30s", "Start Date");
                System.out.print("|");
                System.out.printf("%-30.30s", "End Date");
                System.out.println();
                for (ReservationModel model : models) {
                    printReservation(model);
                }
            }
        }
        return models;
    }

    private void printReservation (ReservationModel model){
        System.out.printf("%-20.20s", model.getConfNo());
        System.out.print("|");
        System.out.printf("%-20.20s", model.getVtName());
        System.out.print("|");
        System.out.printf("%-20.20s", model.getdLicense());
        System.out.print("|");
        System.out.printf("%-30.30s", model.getFromDate());
        System.out.print("|");
        System.out.printf("%-30.30s", model.getToDate());
        System.out.println();
    }
    public void updateReservation (int confNo, int col, String name, Timestamp date){
        ReservationModel[] model = showReservation(confNo,false);
        if(model.length>0) {
            VehiclesModel[] vehiclesModel;
            ReservationModel resModel;
            switch (col) {
                case 1:
                        /*
                        Get reservation
                        Get the new vehicle type
                        Check if the new vehicle type is available in this time interval
                        Update vehicle type
                         */
                    vehiclesModel = dbHandler.getAvailableVehicles("", name, "", "", model[0].getFromDate(), model[0].getToDate());
                    if (vehiclesModel.length < 1) {
                        System.out.println(INFO_TAG + "There is no vehicles available with the given vehicle type on the given dates.");
                    } else {
                        resModel = new ReservationModel(model[0].getConfNo(), name, model[0].getdLicense(), model[0].getFromDate(), model[0].getToDate());
                        dbHandler.updateReservation(resModel, 1);
                        System.out.println(INFO_TAG + "Update complete");
                        printReservation(resModel);
                    }
                    break;
                case 2:
                        /*
                        Get reservation
                        Check if customer is new
                        Update Driver´s License
                         */
                    resModel = new ReservationModel(model[0].getConfNo(), model[0].getVtName(), name, model[0].getFromDate(), model[0].getToDate());
                    dbHandler.updateReservation(resModel, 2);
                    System.out.println(INFO_TAG + "Update complete");
                    System.out.println();
                    printReservation(resModel);
                    break;

                case 3:
                        /*
                        Get reservation
                        Get the new start date
                        Check if the vehicle type is available in this time interval
                        Update start date
                         */
                    if (date.before(new Timestamp(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) || model[0].getToDate().before(new Timestamp(date.getTime() + 24 * 60 * 60 * 1000))) {
                        System.out.println(INFO_TAG + "The start day has to be at least 24 hour from now and 24 hour before the end date.");
                        System.out.println(INFO_TAG + "Update not complete");
                    } else {

                        deleteReservation(model[0].getConfNo());
                        vehiclesModel = dbHandler.getAvailableVehicles("", model[0].getVtName(), "", "", date, model[0].getToDate());
                        if (vehiclesModel.length < 1) {
                            System.out.println(INFO_TAG + "There is no vehicles available with the given vehicle type on the given dates.");
                            System.out.println(INFO_TAG + "Update not complete");
                            insertReservation(model[0], true);
                        } else {
                            insertReservation(model[0], false);
                            resModel = new ReservationModel(model[0].getConfNo(), model[0].getVtName(), model[0].getdLicense(), date, model[0].getToDate());
                            dbHandler.updateReservation(resModel, 3);
                            System.out.println(INFO_TAG + "Update complete");
                            printReservation(resModel);
                        }
                    }
                    break;
                case 4:
                        /*
                        Get reservation
                        Get the new end date
                        Check if the vehicle type is available in this time interval
                        Update end date
                         */
                    if (date.before(new Timestamp(model[0].getFromDate().getTime() + 24 * 60 * 60 * 1000))) {
                        System.out.println(INFO_TAG + "The end day has to be at least 24 after the start date.");
                        System.out.println(INFO_TAG + "Update not complete");
                    } else {

                        deleteReservation(model[0].getConfNo());
                        vehiclesModel = dbHandler.getAvailableVehicles("", model[0].getVtName(), "", "", model[0].getFromDate(), date);
                        if (vehiclesModel.length < 1) {
                            System.out.println(INFO_TAG + "There is no vehicles available with the given vehicle type on the given dates.");
                            System.out.println(INFO_TAG + "Update not complete");
                            insertReservation(model[0], true);
                        } else {
                            insertReservation(model[0], false);
                            resModel = new ReservationModel(model[0].getConfNo(), model[0].getVtName(), model[0].getdLicense(), model[0].getFromDate(), date);
                            dbHandler.updateReservation(resModel, 4);
                            System.out.println(INFO_TAG + "Update complete");
                            printReservation(resModel);
                        }
                    }
                    break;
            }
        }

    }

    public boolean checkConfNo(int confNo){
        ReservationModel[] models = showReservation(confNo,false);
        return models.length>0;
    }


    public void insertBranch(BranchModel model) {
        dbHandler.insertBranch(model);
        System.out.println(INFO_TAG + "Insert complete.");
    }


    public void deleteBranch(String location, String city) {
        if(dbHandler.deleteBranch(location, city)){
            System.out.println(INFO_TAG + "Delete complete.");
        } else {
            System.out.println(INFO_TAG + "The branch is not found");
        }
    }



    public void showBranch() {
        BranchModel[] models = dbHandler.getBranchInfo();
        if(models.length<1){
            System.out.println(INFO_TAG + "There is no branch to show.");
        } else {
            System.out.printf("%-10.10s", "Location");
            System.out.print("|");
            System.out.printf("%-15.15s", "City");
            System.out.println();

            for (BranchModel model : models) {
                System.out.printf("%-10.10s", model.getLocation());
                System.out.print("|");
                System.out.printf("%-15.15s", model.getCity());
                System.out.println();
            }
        }
    }
    public void showNumberVehicles(String vtname, String location, String city, Timestamp fromDate, Timestamp toDate) {
        VehiclesModel[] models = dbHandler.getAvailableVehicles("",vtname, location, city, fromDate, toDate);
        System.out.println("Number of cars avaiable:\t"+models.length);

    }
    public void showAvailableVehicles(String vtname, String location, String city, Timestamp fromDate, Timestamp toDate) {
        VehiclesModel[] models = dbHandler.getAvailableVehicles("",vtname, location, city, fromDate, toDate);
        printVehicles(models);
    }

    public VehiclesModel[] showVehicles(String vLicense, boolean show) {
        VehiclesModel[] models = dbHandler.getVehicles(vLicense);
        if(show) {
            printVehicles(models);
            System.out.println("Number of cars shown:\t"+models.length);
        }
        return models;
    }

    private void printVehicles(VehiclesModel[] models){
        if(models.length<1){
            System.out.println(INFO_TAG + "There is no vehicles to show.");
        } else {
            System.out.printf("%-10.10s", "Vehicle ID");
            System.out.print("|");
            System.out.printf("%-15.15s", "Vehicle License");
            System.out.print("|");
            System.out.printf("%-10.10s", "Make");
            System.out.print("|");
            System.out.printf("%-10.10s", "Model");
            System.out.print("|");
            System.out.printf("%-10.10s", "Year");
            System.out.print("|");
            System.out.printf("%-10.10s", "Color");
            System.out.print("|");
            System.out.printf("%-10.10s", "Odometer");
            System.out.print("|");
            System.out.printf("%-10.10s", "Status");
            System.out.print("|");
            System.out.printf("%-15.15s", "Vehicle Type");
            System.out.print("|");
            System.out.printf("%-10.10s", "Location");
            System.out.print("|");
            System.out.printf("%-15.15s", "City");
            System.out.println();

            for (VehiclesModel model : models) {
                System.out.printf("%-10.10s", model.getVid());
                System.out.print("|");
                System.out.printf("%-15.15s", model.getvLicense());
                System.out.print("|");
                System.out.printf("%-10.10s", model.getMake());
                System.out.print("|");
                System.out.printf("%-10.10s", model.getModel());
                System.out.print("|");
                System.out.printf("%-10.10s", model.getYear());
                System.out.print("|");
                System.out.printf("%-10.10s", model.getColor());
                System.out.print("|");
                System.out.printf("%-10.10s", model.getOdometer());
                System.out.print("|");
                System.out.printf("%-10.10s", model.getStatus());
                System.out.print("|");
                System.out.printf("%-15.15s", model.getVtname());
                System.out.print("|");
                System.out.printf("%-10.10s", model.getLocation());
                System.out.print("|");
                System.out.printf("%-15.15s", model.getCity());
                System.out.println();
            }
        }
    }
    //Check if there is a vehicle with the vehicle license
    public boolean checkVLicense(String vLicense){
        VehiclesModel[] models = dbHandler.getVehicles(vLicense);
        boolean check = false;
        for (VehiclesModel a: models) {
            if(a.getvLicense().equals(vLicense)){
                check = true;
                break;
            }
        }
        return check;
    }

    //Show customer
    public void showCustomer(String dLicense) {
        CustomerModel[] models = dbHandler.getCustomerInfo(dLicense);
        if(models.length<1){
            System.out.println(INFO_TAG + "There is no customer to show.");
        } else {
            System.out.printf("%-18.18s", "Driver´s License");
            System.out.print("|");
            System.out.printf("%-30.30s", "Name");
            System.out.print("|");
            System.out.printf("%-30.30s", "Address");
            System.out.println();

            for (CustomerModel model : models) {
                System.out.printf("%-18.18s", model.getdLicense());
                System.out.print("|");
                System.out.printf("%-30.30s", model.getName());
                System.out.print("|");
                System.out.printf("%-30.30s", model.getAddress());
                System.out.println();
            }
        }
    }

    //Show vehicletypes
    public VehicleTypesModel[] showVehicleTypes(String vtname, boolean showDetails) {
        VehicleTypesModel[] models = dbHandler.getTypes(vtname);
        if(models.length<1){
            System.out.println(INFO_TAG + "There is no vehicles types to show.");
        } else if (showDetails) {
            System.out.printf("%-15.15s", "Vehicle Type");
            System.out.print("|");
            System.out.printf("%-20.20s", "Features");
            System.out.print("|");
            System.out.printf("%-15.15s", "Weekly rate");
            System.out.print("|");
            System.out.printf("%-15.15s", "Daily rate");
            System.out.print("|");
            System.out.printf("%-15.15s", "Hourly rate");
            System.out.print("|");
            System.out.printf("%-20.20s", "Weekly Ins. rate");
            System.out.print("|");
            System.out.printf("%-20.20s", "Daily Ins. rate");
            System.out.print("|");
            System.out.printf("%-20.20s", "Hourly Ins. rate");
            System.out.print("|");
            System.out.printf("%-10.10s", "K. rate");
            System.out.println();

            for (VehicleTypesModel model : models) {
                System.out.printf("%-15.15s", model.getVtName());
                System.out.print("|");
                System.out.printf("%-20.20s", model.getFeatures());
                System.out.print("|");
                System.out.printf("%-15.15s", model.getwRate());
                System.out.print("|");
                System.out.printf("%-15.15s", model.getdRate());
                System.out.print("|");
                System.out.printf("%-15.15s", model.gethRate());
                System.out.print("|");
                System.out.printf("%-20.20s", model.getwIRate());
                System.out.print("|");
                System.out.printf("%-20.20s", model.getdIRate());
                System.out.print("|");
                System.out.printf("%-20.20s", model.gethIRate());
                System.out.print("|");
                System.out.printf("%-20.20s", model.getkRate());
                System.out.println();
            }
        }
        return models;
    }

    //Get´s the highest rentalID
    public int lastRid() {
        return dbHandler.last("RID","RENTALS");
    }

    //Inserts the new rental
    public void insertRental(RentalModel model){
        VehiclesModel[] vehiclesModel = dbHandler.getAvailableVehicles(model.getvLicense(),"", "", "", model.getFromDate(), model.getToDate());
        if(vehiclesModel.length<1){
            System.out.println(INFO_TAG + "There is no vehicles available with the given vehicle license on the given dates.");
        } else {
            dbHandler.insertRental(model);
            System.out.println("Your rental number is: \t" + model.getRid());
            System.out.println("The vehicle license is: \t" + model.getvLicense());
            System.out.println("The vehicle make is: \t" + vehiclesModel[0].getMake());
            System.out.println("The vehicle model is: \t" + vehiclesModel[0].getModel());
            System.out.println("The year of production is: \t" + vehiclesModel[0].getYear());
            System.out.println("The vehicle type is: \t" + vehiclesModel[0].getVtname());
            System.out.println("Your driver´s license is: \t" + model.getdLicense());
            System.out.println("Start date is: \t" + model.getFromDate());
            System.out.println("End date is: \t" + model.getToDate());
            System.out.println("Odometer is: \t" + model.getOdometer());
            System.out.println("Card name is: \t" + model.getCardName());
            System.out.println("Card number is: \t" + model.getCardNumber());
            if(model.getConfNo()!=NULL) {
                System.out.println("Confirmation number is: \t" + model.getConfNo());
            }
        }
    }

    public boolean checkRid(int rid){
        RentalModel[] models = showRentedVehicles(rid,false);
        return models.length>0;
    }

    //Show rented vehicles
    public RentalModel[] showRentedVehicles(int rid, boolean showDetails) {
        RentalModel[] models = dbHandler.getRentalInfo(rid);
        if(models.length<1){
            System.out.println(INFO_TAG + "There is no rental with that rental number.");
        }
        else {
            if (showDetails) {
                System.out.printf("%-10.10s", "Rental No.");
                System.out.print("|");
                System.out.printf("%-18.18s", "Vehicle's License");
                System.out.print("|");
                System.out.printf("%-18.18s", "Driver's License");
                System.out.print("|");
                System.out.printf("%-20.20s", "Start Date");
                System.out.print("|");
                System.out.printf("%-20.20s", "End Date");
                System.out.print("|");
                System.out.printf("%-10.10s", "Odometer");
                System.out.print("|");
                System.out.printf("%-10.10s", "Cardname");
                System.out.print("|");
                System.out.printf("%-10.10s", "Card No.");
                System.out.print("|");
                System.out.printf("%-10.10s", "Exp. date");
                System.out.print("|");
                System.out.printf("%-10.10s", "Conf. No.");
                System.out.print("|");
                System.out.printf("%-10.10s", "R.Odometer");
                System.out.print("|");
                System.out.printf("%-10.10s", "R.FullTank");
                System.out.print("|");
                System.out.printf("%-10.10s", "Value");
                System.out.print("|");
                System.out.printf("%-20.20s", "Rdate");
                System.out.println();
                for (RentalModel model : models) {
                    printRental(model);
                }
            }
        }
        return models;
    }

    private void printRental (RentalModel model){
        System.out.printf("%-10.10s", model.getRid());
        System.out.print("|");
        System.out.printf("%-18.18s", model.getvLicense());
        System.out.print("|");
        System.out.printf("%-18.18s", model.getdLicense());
        System.out.print("|");
        System.out.printf("%-20.20s", model.getFromDate());
        System.out.print("|");
        System.out.printf("%-20.20s", model.getToDate());
        System.out.print("|");
        System.out.printf("%-10.10s", model.getOdometer());
        System.out.print("|");
        System.out.printf("%-10.10s", model.getCardName());
        System.out.print("|");
        System.out.printf("%-10.10s", model.getCardNumber());
        System.out.print("|");
        System.out.printf("%-10.10s", model.getExpDate());
        System.out.print("|");
        if(model.getConfNo()<1){
            System.out.printf("%-10.10s", "null");
        } else {
            System.out.printf("%-10.10s", model.getConfNo());
        }
        System.out.print("|");
        if(model.getrOdometer()<1 ){
            System.out.printf("%-10.10s", "null");
        } else {
            System.out.printf("%-10.10s", model.getrOdometer());

        }
        System.out.print("|");
        System.out.printf("%-10.10s", model.getrFulltank());
        System.out.print("|");
        if(model.getValue()<1){
            System.out.printf("%-10.10s", "null");
        } else {
            System.out.printf("%-10.10s", model.getValue());
        }
        System.out.print("|");
        System.out.printf("%-20.20s", model.getrDate());
        System.out.println();
    }

    public void returnRental (RentalModel model){
        dbHandler.deleteRental(model.getRid());
        dbHandler.insertRental(model);
        printRental(model);
        System.out.println("Value is calculated by weeks then remaining days plus the weekly and daily insurance rates");
    }

    public void showReportLocation(String Location, String City){
       ReportModel[] models = dbHandler.getDailyRentalsBranch(Location, City);
       VehiclesModel[] vModels = dbHandler.getDailyRentedVehicles(Location, City);
        if(vModels.length<1){
            System.out.println(INFO_TAG + "There are no rentals for today at: \t" + Location);
        }
        else {
            System.out.println("Location is: \t" + models[0].getLocation());
            System.out.println("City is: \t" + models[0].getCity());
            System.out.println("Total rentals for location: \t" + models[0].getTotalNumber());
            System.out.println("Total rentals for vehicle types:");
            System.out.println();
            for (ReportModel model : models) {
                System.out.printf("%-15.15s", model.getVtType());
                System.out.print("|");
                System.out.printf("%-10.10s", model.getVtCount());
                System.out.println();
            }
            printVehicles(vModels);
        }
    }

    public void showReportsAll(){
        int total = dbHandler.getTotalRentals();

        System.out.println("Total Rentals: \t" + total);
        showReportLocation("A","ABBOTSFORD");
        System.out.println();
        showReportLocation("C","CHILLIWACK");
        System.out.println();
        showReportLocation("V","VANCOUVER");

    }

    /**
     * TerminalTransactionsDelegate Implementation
     *
     * The TerminalTransaction instance tells us that it is done with what it's
     * doing so we are cleaning up the connection since it's no longer needed.
     */
    public void terminalTransactionsFinished() {
        dbHandler.close();
        dbHandler = null;

        System.exit(0);
    }

    /**
     * Main method called at launch time
     */
    public static void main(String[] args) {
        SuperRent superRent = new SuperRent();
        superRent.start();
    }
}
