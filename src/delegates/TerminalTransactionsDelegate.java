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

	public boolean checkCustomer(String dlicense);
	public void insertCustomer(CustomerModel model);


	public void deleteReservation(int confNo);
	public void insertReservation(ReservationModel model);
	public ReservationModel[] showReservation(int confNo, boolean showDetails);
	public int lastConfNumber();

	public void deleteBranch(String location, String city);
	public void insertBranch(BranchModel model);
	public void showBranch();

	public void showAvailableVehicles(String vtname, String location, String city, Timestamp fromDate, Timestamp toDate);
	public void showNumberVehicles(String vtname, String location, String city, Timestamp fromDate, Timestamp toDate);
	public VehiclesModel[] showVehicles(String vLicense, boolean show);
	public boolean checkVLicense(String vLicense);

	public int lastRid();
	public void insertRental(RentalModel model);

	public void terminalTransactionsFinished();
}
