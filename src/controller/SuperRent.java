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
            System.out.println("There is no vehicles available with the given vehicle type on the given dates.");
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
        dbHandler.deleteReservation(confNo);
    }



    public ReservationModel[] showReservation(int confNo, boolean showDetails) {
        ReservationModel[] models = dbHandler.getReservationInfo(confNo);

        if(showDetails) {
            for (ReservationModel model : models) {
                printReservation(model);
            }
        }

        return models;
    }

    private void printReservation (ReservationModel model){
        System.out.printf("%-20.20s", model.getConfNo());
        System.out.printf("%-20.20s", model.getVtName());
        System.out.printf("%-20.20s", model.getdLicense());
        System.out.printf("%-20.20s", model.getFromDate());
        System.out.printf("%-20.20s", model.getToDate());
        System.out.println();
    }
    public void updateReservation (int confNo, int col, String name, Timestamp date){
        ReservationModel[] model = showReservation(confNo,false);
        if(model.length<1){
            System.out.println("The confirmation number is not found");
        } else {
            VehiclesModel[] vehiclesModel;
            ReservationModel resModel;
            switch (col) {
                case 1:
                        /*
                        Get the new vehicle type
                        Check if the new vehicle type is available in this time interval
                        Update vehicle type
                         */
                    vehiclesModel = dbHandler.getAvailableVehicles("",name,"", "", model[0].getFromDate(), model[0].getToDate());
                    if(vehiclesModel.length<1){
                        System.out.println("There is no vehicles available with the given vehicle type on the given dates.");
                    } else {
                        resModel = new ReservationModel(model[0].getConfNo(),name,model[0].getdLicense(),model[0].getFromDate(), model[0].getToDate());
                        dbHandler.updateReservation(resModel,1);
                        System.out.println("Update complete");
                        printReservation(resModel);
                    }
                    break;
                case 2:
                        /*
                        Get reservation
                        Check if customer is new
                        Update Driver´s License
                         */
                    resModel = new ReservationModel(model[0].getConfNo(),model[0].getVtName(),name,model[0].getFromDate(), model[0].getToDate());
                    dbHandler.updateReservation(resModel,2);
                    System.out.println("Update complete");
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
                    if(date.before(new Timestamp(System.currentTimeMillis()+24*60*60*1000)) || model[0].getToDate().before(new Timestamp(date.getTime()+24*60*60*1000))){
                        System.out.println("The start day has to be at least 24 hour from now and 24 hour before the end date.");
                        System.out.println("Update not complete");
                    } else {

                        deleteReservation(model[0].getConfNo());
                        vehiclesModel = dbHandler.getAvailableVehicles("", model[0].getVtName(), "", "", date, model[0].getToDate());
                        if (vehiclesModel.length < 1) {
                            System.out.println("There is no vehicles available with the given vehicle type on the given dates.");
                            System.out.println("Update not complete");
                            insertReservation(model[0], true);
                        } else {
                            insertReservation(model[0], false);
                            resModel = new ReservationModel(model[0].getConfNo(),model[0].getVtName(),model[0].getdLicense(),date, model[0].getToDate());
                            dbHandler.updateReservation(resModel,3);
                            System.out.println("Update complete");
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
                    if(date.before(new Timestamp(model[0].getFromDate().getTime()+24*60*60*1000))){
                        System.out.println("The end day has to be at least 24 after the start date.");
                        System.out.println("Update not complete");
                    } else {

                        deleteReservation(model[0].getConfNo());
                        vehiclesModel = dbHandler.getAvailableVehicles("", model[0].getVtName(), "", "", model[0].getFromDate(), date);
                        if (vehiclesModel.length < 1) {
                            System.out.println("There is no vehicles available with the given vehicle type on the given dates.");
                            System.out.println("Update not complete");
                            insertReservation(model[0], true);
                        } else {
                            insertReservation(model[0], false);
                            resModel = new ReservationModel(model[0].getConfNo(),model[0].getVtName(),model[0].getdLicense(),model[0].getFromDate(), date);
                            dbHandler.updateReservation(resModel,4);
                            System.out.println("Update complete");
                            printReservation(resModel);
                        }
                    }
                    break;
            }
        }

    }

    /**
     * TermainalTransactionsDelegate Implementation
     *
     * Insert a branch with the given info
     */
    public void insertBranch(BranchModel model) {
        dbHandler.insertBranch(model);
    }

    /**
     * TermainalTransactionsDelegate Implementation
     *
     * Delete branch with given branch ID.
     */
    public void deleteBranch(String location, String city) {
        dbHandler.deleteBranch(location, city);
    }


    /**
     * TermainalTransactionsDelegate Implementation
     *
     * Displays information about varies SuperRent branches.
     */
    public void showBranch() {
        BranchModel[] models = dbHandler.getBranchInfo();

        for (BranchModel model : models) {
            System.out.printf("%-10.10s", model.getLocation());
            System.out.printf("%-15.15s", model.getCity());
            System.out.println();
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
        }
        return models;
    }

    private void printVehicles(VehiclesModel[] models){
        System.out.println("Details:");

        for (VehiclesModel model : models) {
            System.out.printf("%-10.10s", model.getVid());
            System.out.printf("%-15.15s", model.getvLicense());
            System.out.printf("%-10.10s", model.getMake());
            System.out.printf("%-15.15s", model.getModel());
            System.out.printf("%-10.10s", model.getYear());
            System.out.printf("%-15.15s", model.getColor());
            System.out.printf("%-10.10s", model.getOdometer());
            System.out.printf("%-15.15s", model.getStatus());
            System.out.printf("%-10.10s", model.getVtname());
            System.out.printf("%-10.10s", model.getLocation());
            System.out.printf("%-15.15s", model.getCity());
            System.out.println();
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


    //Get´s the highest rentalID
    public int lastRid() {
        return dbHandler.last("RID","RENTALS");
    }

    //Inserts the new rental
    public void insertRental(RentalModel model){
        VehiclesModel[] vehiclesModel = dbHandler.getAvailableVehicles(model.getvLicense(),"", "", "", model.getFromDate(), model.getToDate());
        if(vehiclesModel.length<1){
            System.out.println("There is no vehicles available with the given vehicle license on the given dates.");
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
