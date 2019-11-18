package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Year;

import delegates.TerminalTransactionsDelegate;
import model.BranchModel;
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

		while (location == null || city == null || location.length() <= 0 || city.length() <= 0) {
			System.out.println("Please enter the location of the branch you wish to delete: ");
			location = readLine().trim();
            System.out.println("Please enter the city of the branch you wish to delete: ");
            city = readLine().trim();
			if (city != null && location != null && location.length() > 0 && city.length() > 0) {
				delegate.deleteBranch(location, city);
			}
		}
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
		int confNo = INVALID_INPUT;
		while (confNo == INVALID_INPUT) {
			System.out.println("Please enter the confirmation number you wish to insert: ");
			confNo = readInteger(false);
		}

		String vtName = null;
		while (vtName == null || vtName.length() <= 0) {
			System.out.println("Please enter the vehicletype you wish to insert: ");
			vtName = readLine().trim();
		}

		String dLicense = null;
		while (dLicense == null || dLicense.length() <= 0) {
			System.out.println("Please enter the driver´s license you wish to insert: ");
			dLicense = readLine().trim();
		}

		Timestamp fromDate = null;
		while (fromDate == null) {
			System.out.println("Please enter the start date you wish to insert: ");
			System.out.println("Year: YYYY");
			String year = readLine().trim();
			System.out.println("Month: MM");
			String month = readLine().trim();
			System.out.println("Day: DD");
			String day = readLine().trim();;
			System.out.println("Hour: HH");
			String hour = readLine().trim();
			System.out.println("Minute: MM");
			String minute = readLine().trim();

			fromDate = Timestamp.valueOf(year+"-"+month+"-"+day+" "+hour+":"+minute+":00");
		}


		Timestamp toDate = null;
		while (toDate == null) {
			System.out.println("Please enter the end date you wish to insert: ");
			System.out.println("Year: YYYY");
			String year = readLine().trim();
			System.out.println("Month: MM");
			String month = readLine().trim();
			System.out.println("Day: DD");
			String day = readLine().trim();;
			System.out.println("Hour: HH");
			String hour = readLine().trim();
			System.out.println("Minute: MM");
			String minute = readLine().trim();

			toDate = Timestamp.valueOf(year+"-"+month+"-"+day+" "+hour+":"+minute+":00");
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
		System.out.println("Year: YYYY");
		String year = readLine().trim();
		if(year.equals("")){
			year="1970";
		}
		System.out.println("Month: MM");
		String month = readLine().trim();
		if(month.equals("")){
			month="01";
		}
		System.out.println("Day: DD");
		String day = readLine().trim();
		if(day.equals("")){
			day="01";
		}
		System.out.println("Hour: HH");
		String hour = readLine().trim();
		if(hour.equals("")){
			hour="00";
		}
		System.out.println("Minute: MM");
		String minute = readLine().trim();
		if(minute.equals("")){
			minute="01";
		}

		Timestamp fromDate = Timestamp.valueOf(year+"-"+month+"-"+day+" "+hour+":"+minute+":00");

        System.out.println("Please enter the end date you wish to search for (Press enter for all dates): ");
		System.out.println("Year: YYYY");
		year = readLine().trim();
		if(year.equals("")){
			year="2200";
		}
		System.out.println("Month: MM");
		month = readLine().trim();
		if(month.equals("")){
			month="12";
		}
		System.out.println("Day: DD");
		day = readLine().trim();
		if(day.equals("")){
			day="31";
		}
		System.out.println("Hour: HH");
		hour = readLine().trim();
		if(hour.equals("")){
			hour="23";
		}
		System.out.println("Minute: MM");
		minute = readLine().trim();
		if(minute.equals("")){
			minute="59";
		}

		Timestamp toDate = Timestamp.valueOf(year+"-"+month+"-"+day+" "+hour+":"+minute+":00");

        delegate.showVehicles(vtName, location, city, fromDate, toDate);


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
}
