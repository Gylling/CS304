package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;

import delegates.TerminalTransactionsDelegate;
import model.BranchModel;
import model.CustomerModel;
import model.ReservationModel;

/**
 * The class is only responsible for handling terminal text inputs. 
 */
public class TerminalTransactions {
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";
	private static final int INVALID_INPUT = Integer.MIN_VALUE;
	private static final int EMPTY_INPUT = 0;
	
	private BufferedReader bufferedReader = null;
	private TerminalTransactionsDelegate delegate = null;

	public TerminalTransactions() {
	}

	/**
	 * Displays simple text interface
	 */ 
	public void showMainMenu(TerminalTransactionsDelegate delegate) {
		this.delegate = delegate;
		
	    bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;

		while (choice !=5){
		    System.out.println("What is your query about?");
			System.out.println();
			System.out.println("1. Branch");
			System.out.println("2. Reservation");
			System.out.println("3. Vehicles");
			System.out.println("4. Quit");
			System.out.println("Please choose one of the above 5 options: ");

			choice = readInteger(false);

			System.out.println(" ");

			if (choice != INVALID_INPUT) {
				switch (choice) {
					case 1:
						while (choice != 4) {
							System.out.println();
							System.out.println("1. Insert branch");
							System.out.println("2. Delete branch");
							System.out.println("3. Show branch");
							System.out.println("4. Quit");
							System.out.println("Please choose one of the above 4 options: ");

							choice = readInteger(false);

							System.out.println(" ");

							if (choice != INVALID_INPUT) {
								switch (choice) {
									case 1:
										branchInsertOption();
										break;
									case 2:
										branchDeleteOption();
										break;
									case 3:
										delegate.showBranch();
										break;
									case 4:
										handleQuitOption();
										break;
									default:
										System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
										break;
								}
							}
						}
						break;
					case 2:
						while (choice != 4) {
							System.out.println();
							System.out.println("1. Insert reservation");
							System.out.println("2. Delete reservation");
							System.out.println("3. Show reservation");
							System.out.println("4. Quit");
							System.out.println("Please choose one of the above 4 options: ");

							choice = readInteger(false);

							System.out.println(" ");

							if (choice != INVALID_INPUT) {
								switch (choice) {
									case 1:
										reservationInsertOption();
										break;
									case 2:
										reservationDeleteOption();
										break;
									case 3:
										delegate.showReservation();
										break;
									case 4:
										handleQuitOption();
										break;
									default:
										System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
										break;
								}
							}
						}
						break;
                    case 3:
                        while (choice != 4) {
                            System.out.println();
                            System.out.println("1. Search for car");
                            System.out.println("4. Quit");
                            System.out.println("Please choose one of the above 2 options: ");

                            choice = readInteger(false);

                            System.out.println(" ");

                            if (choice != INVALID_INPUT) {
                                switch (choice) {
                                    case 1:
                                        handleVehicleSearch();
                                        break;
                                    case 4:
                                        handleQuitOption();
                                        break;
                                    default:
                                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                                        break;
                                }
                            }
                        }
                        break;
					case 4:
						handleQuitOption();
						break;
					default:
						System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
						break;
				}
			}

		}

	}
	
	private void branchDeleteOption() {
		String city = null;
		String location = null;

		while (location == null  || city == null || location.length() <= 0 || city.length() <= 0) {
			System.out.println("Please enter the location of the branch you wish to delete: ");
			location = readLine().trim();
            System.out.println("Please enter the city of the branch you wish to delete: ");
            city = readLine().trim();
			if (city != null && location != null && location.length() > 0 && city.length() > 0) {
				delegate.deleteBranch(location, city);
			}
		}
	}

	private void newCustomer(String dLicense){
		String name=null;
		while (name == null || name.length() <= 0) {
			System.out.println("You´re a new customer please write your name:");
			name = readLine().trim().toUpperCase();
		}

		String address=null;
		while (address == null || address.length() <= 0) {
			System.out.println("Please write your address:");
			address = readLine().trim().toUpperCase();
		}

		CustomerModel model = new CustomerModel(dLicense,name,address);
		delegate.insertCustomer(model);
	}

	private void reservationDeleteOption() {
		int confNo = INVALID_INPUT;
		while (confNo == INVALID_INPUT) {
			System.out.println("Please enter the confirmation number you wish to delete: ");
			confNo = readInteger(false);
			if (confNo != INVALID_INPUT) {
				delegate.deleteReservation(confNo);
			}
		}
	}

	private void reservationInsertOption() {
		String dLicense = null;
		while (dLicense == null || dLicense.length() <= 0) {
			System.out.println("Please enter your driver´s license: ");
			dLicense = readLine().trim();
		}
		if(delegate.checkCustomer(dLicense)){
			newCustomer(dLicense);
		}

		int confNo = delegate.lastConfNumber();

		String vtName = null;
		while (vtName == null || vtName.length() <= 0) {
			System.out.println("Please enter the vehicletype you wish to insert: ");
			vtName = readLine().trim();
		}


		Timestamp fromDate =null;
		while (fromDate == null || fromDate.before(new Timestamp(System.currentTimeMillis()+24*60*60*1000))){
			System.out.println("Please enter the start date you wish to insert (The start day has to be at least 24 from now):");
			fromDate = createFromDate();
		}


		Timestamp toDate =null;
		while (toDate == null || toDate.before(new Timestamp(fromDate.getTime()+24*60*60*1000))){
			System.out.println("Please enter the end date (A reservation has to be at least 24 hours):");
			toDate = createToDate();
		}


		ReservationModel model = new ReservationModel(confNo,
				vtName,
				dLicense,
				fromDate,
				toDate);
		delegate.insertReservation(model);
	}
	
	private void branchInsertOption() {
        String location = null;
		while (location == null || location.length() <= 0) {
			System.out.print("Please enter the location of the branch you wish to insert: ");
			location = readLine().trim();
		}
		

		
		String city = null;
		while (city == null || city.length() <= 0) {
			System.out.print("Please enter the city of the branch you wish to insert: ");
			city = readLine().trim();
		}

		BranchModel model = new BranchModel(location, city);
		delegate.insertBranch(model);
	}

	private void handleVehicleSearch(){

        System.out.println("Please enter the vehicletype you wish to search for (Press enter for all vehicletypes): ");
        String vtName = readLine().trim();

        System.out.println("Please enter the loctaion you wish to search for (Press enter for all locations): ");
        String location = readLine().trim();

        System.out.println("Please enter the city you wish to search for (Press enter for all cities): ");
        String city = readLine().trim();

        System.out.println("Please enter the start date you wish to search for (Press enter for all dates): ");
		Timestamp fromDate = createFromDate();

        System.out.println("Please enter the end date you wish to search for (Press enter for all dates): ");
		Timestamp toDate = createToDate();

        delegate.showNumberVehicles(vtName, location, city, fromDate, toDate);
        String command = "";
		while(!command.equals("SHOW") && !command.equals("EXIT")) {
			System.out.println("Enter \"Show\" to show details of the cars.");
			System.out.println("Enter \"Exit\" to exit search.");
			command=readLine().trim().toUpperCase();
			if (command.equals("SHOW")) {
				delegate.showVehicles(vtName, location, city, fromDate, toDate);
			}
		}
    }

	private void handleQuitOption() {
		System.out.println("Good Bye!");
		
		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				System.out.println("IOException!");
			}
		}
		
		delegate.terminalTransactionsFinished();
	}

	
	private int readInteger(boolean allowEmpty) {
		String line = null;
		int input = INVALID_INPUT;
		try {
			line = bufferedReader.readLine();
			input = Integer.parseInt(line);
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		} catch (NumberFormatException e) {
			if (allowEmpty && line.length() == 0) {
				input = EMPTY_INPUT;
			} else {
				System.out.println(WARNING_TAG + " Your input was not an integer");
			}
		}
		return input;
	}
	
	private String readLine() {
		String result = null;
		try {
			result = bufferedReader.readLine();
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return result;
	}

	private Timestamp createFromDate(){
		System.out.println("Year: YYYY");
		String year = readLine().trim().toUpperCase();
		if(year.equals("")){
			year="1970";
		}
		System.out.println("Month: MM");
		String month = readLine().trim().toUpperCase();
		if(month.equals("")){
			month="01";
		}
		System.out.println("Day: DD");
		String day = readLine().trim().toUpperCase();
		if(day.equals("")){
			day="01";
		}
		System.out.println("Hour: HH");
		String hour = readLine().trim().toUpperCase();
		if(hour.equals("")){
			hour="00";
		}
		System.out.println("Minute: MM");
		String minute = readLine().trim().toUpperCase();
		if(minute.equals("")){
			minute="01";
		}

		Timestamp fromDate = Timestamp.valueOf(year+"-"+month+"-"+day+" "+hour+":"+minute+":00");

		if(fromDate.before(new Timestamp(System.currentTimeMillis()))){
			fromDate= new Timestamp(System.currentTimeMillis());
		}
		return fromDate;
	}

	private Timestamp createToDate(){
		System.out.println("Year: YYYY");
		String year = readLine().trim().toUpperCase();
		if(year.equals("")){
			year="2200";
		}
		System.out.println("Month: MM");
		String month = readLine().trim().toUpperCase();
		if(month.equals("")){
			month="12";
		}
		System.out.println("Day: DD");
		String day = readLine().trim().toUpperCase();
		if(day.equals("")){
			day="31";
		}
		System.out.println("Hour: HH");
		String hour = readLine().trim().toUpperCase();
		if(hour.equals("")){
			hour="23";
		}
		System.out.println("Minute: MM");
		String minute = readLine().trim().toUpperCase();
		if(minute.equals("")){
			minute="59";
		}
		Timestamp toDate = Timestamp.valueOf(year+"-"+month+"-"+day+" "+hour+":"+minute+":00");

		if(toDate.before(new Timestamp(System.currentTimeMillis()))){
			toDate= new Timestamp(System.currentTimeMillis());
		}
		return toDate;
	}
}
