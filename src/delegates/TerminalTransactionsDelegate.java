package delegates;

import model.*;

import java.sql.Timestamp;

/**
 * This interface uses the delegation design pattern where instead of having
 * the TerminalTransactions class try to do everything, it will only
 * focus on handling the UI. The actual logic/operation will be delegated to the 
 * controller class (in this case SuperRent).
 * 
 * TerminalTransactions calls the methods that we have listed below but 
 * SuperRent is the actual class that will implement the methods.
 */
public interface TerminalTransactionsDelegate {

	 boolean checkCustomer(String dlicense);
	 void insertCustomer(CustomerModel model);


	 void deleteReservation(int confNo);
	 void insertReservation(ReservationModel model, boolean show);
	 ReservationModel[] showReservation(int confNo, boolean showDetails);
	 void updateReservation (int confNo, int col, String name, Timestamp date);
	 int lastConfNumber();

	 void deleteBranch(String location, String city);
	 void insertBranch(BranchModel model);
	 void showBranch();

	 void showAvailableVehicles(String vtname, String location, String city, Timestamp fromDate, Timestamp toDate);
	 void showNumberVehicles(String vtname, String location, String city, Timestamp fromDate, Timestamp toDate);
	 VehiclesModel[] showVehicles(String vLicense, boolean show);
	 boolean checkVLicense(String vLicense);

	 int lastRid();
	 void insertRental(RentalModel model);

	 void terminalTransactionsFinished();
}
