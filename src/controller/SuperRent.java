package controller;


import  database.DatabaseConnectionHandler;
import  delegates.LoginWindowDelegate;
import  delegates.TerminalTransactionsDelegate;
import  model.BranchModel;
import model.CustomerModel;
import  model.ReservationModel;
import  model.VehiclesModel;
import  ui.LoginWindow;
import  ui.TerminalTransactions;

import java.sql.Timestamp;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class SuperRent implements LoginWindowDelegate, TerminalTransactionsDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private LoginWindow loginWindow = null;

    public SuperRent() {
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
        System.out.println(newCustomer);
        return newCustomer;
    }

    //Insert new customer
    public void insertCustomer(CustomerModel model){
        dbHandler.insertCustomer(model);
    }

    public void insertReservation(ReservationModel model) {
        dbHandler.insertReservation(model);
        System.out.println("Your confirmation number is: \t" + model.getConfNo());
        System.out.println("Your driver´s license is: \t" + model.getdLicense());
        System.out.println("The vehicle type is: \t" + model.getVtName());
        System.out.println("Start date is: \t" + model.getFromDate());
        System.out.println("End date is: \t" + model.getToDate());
    }

    public int lastConfNumber() {
        return dbHandler.lastConfNumber();
    }


    public void deleteReservation(int confNo) {
        dbHandler.deleteReservation(confNo);
    }



    public void showReservation() {
        ReservationModel[] models = dbHandler.getReservationInfo();

        for (int i = 0; i < models.length; i++) {
            ReservationModel model = models[i];

            // simplified output formatting; truncation may occur
            System.out.printf("%-20.20s", model.getConfNo());
            System.out.printf("%-20.20s", model.getVtName());
            System.out.printf("%-20.20s", model.getdLicense());
            System.out.printf("%-20.20s", model.getFromDate());
            System.out.printf("%-20.20s", model.getToDate());
            System.out.println();
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

        for (int i = 0; i < models.length; i++) {
            BranchModel model = models[i];

            // simplified output formatting; truncation may occur
            System.out.printf("%-10.10s", model.getLocation());
            System.out.printf("%-15.15s", model.getCity());
            System.out.println();
        }
    }
    public void showNumberVehicles(String vtname, String location, String city, Timestamp fromDate, Timestamp toDate) {
        VehiclesModel[] models = dbHandler.getVehiclesInfo(vtname, location, city, fromDate, toDate);
        System.out.println("Number of cars avaiable:\t"+models.length);

    }
    public void showVehicles(String vtname, String location, String city, Timestamp fromDate, Timestamp toDate) {
        VehiclesModel[] models = dbHandler.getVehiclesInfo(vtname, location, city, fromDate, toDate);
        System.out.println("Details:");

        for (int i = 0; i < models.length; i++) {
            VehiclesModel model = models[i];

            // simplified output formatting; truncation may occur
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
    public static void main(String args[]) {
        SuperRent superRent = new SuperRent();
        superRent.start();
    }
}
