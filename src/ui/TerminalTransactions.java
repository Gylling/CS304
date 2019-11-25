package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import com.sun.deploy.util.Waiter;
import delegates.TerminalTransactionsDelegate;
import model.*;

import static java.sql.Types.NULL;

/**
 * The class is only responsible for handling terminal text inputs. 
 */
public class TerminalTransactions {
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";
    private static final String INFO_TAG = "[INFO] ";
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
        while (choice !=10) {
            System.out.println("Welcome to SuperRent");
            System.out.println("Are you a clerk or a customer?");
            System.out.println();
            System.out.println("1. Customer");
            System.out.println("2. Clerk");
            System.out.println("10. Quit");
            System.out.println("Please choose one of the above options: ");

            choice = readInteger(false);

            System.out.println(" ");

            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 1:
                        while (choice != 9) {
                            System.out.println("What would you like to do?");
                            System.out.println();

                            System.out.println("1. Make a reservation");
                            System.out.println("2. Look at available vehicles");
                            System.out.println("3. Vehcile types ");
                            System.out.println("9. Go Back");
                            System.out.println("Please choose one of the above options: ");

                            choice = readInteger(false);

                            System.out.println(" ");

                            if (choice != INVALID_INPUT) {
                                switch (choice) {
                                    case 1:
                                        while (choice != 8) {
                                            System.out.println();
                                            System.out.println("1. Create a reservation");
                                            System.out.println("2. Delete a reservation");
                                            System.out.println("3. Show a reservation");
                                            System.out.println("4. Show all reservations");
                                            System.out.println("5. Update your reservation");
                                            System.out.println("8. Go back");
                                            System.out.println("Please choose one of the above options: ");

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
                                                        showReservation();
                                                        break;
                                                    case 4:
                                                        delegate.showReservation(0,true);
                                                        break;
                                                    case 5:
                                                        reservationUpdateOption();
                                                        break;
                                                    case 8:
                                                        break;
                                                    default:
                                                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                                                        break;
                                                }
                                            }
                                        }
                                        break;
                                    case 2:
                                        while (choice != 8) {
                                            System.out.println();
                                            System.out.println("1. Search for available vehicles");
                                            System.out.println("2. Show all vehicles");
                                            System.out.println("8. Go back");
                                            System.out.println("Please choose one of the above options: ");

                                            choice = readInteger(false);

                                            System.out.println(" ");

                                            if (choice != INVALID_INPUT) {
                                                switch (choice) {
                                                    case 1:
                                                        handleVehicleSearch();
                                                        break;
                                                    case 2:
                                                        delegate.showVehicles("", true);
                                                        break;
                                                    case 8:
                                                        break;
                                                    default:
                                                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                                                        break;
                                                }
                                            }
                                        }
                                        break;
                                    case 3:
                                        while (choice != 8) {
                                            System.out.println();
                                            System.out.println("1. Show vehicle types");
                                            System.out.println("8. Go back");
                                            System.out.println("Please choose one of the above options: ");

                                            choice = readInteger(false);

                                            System.out.println(" ");

                                            if (choice != INVALID_INPUT) {
                                                switch (choice) {
                                                    case 1:
                                                        delegate.showVehicleTypes("",true);
                                                        break;
                                                    case 8:
                                                        break;
                                                    default:
                                                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                                                        break;
                                                }
                                            }
                                        }
                                        break;
                                    case 9:
                                        break;
                                    default:
                                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                                        break;
                                }
                            }

                        }
                        break;
                    case 2:
                        while (choice != 9) {
                            System.out.println("What would you like to do?");
                            System.out.println();
                            System.out.println("1. Look at branches");
                            System.out.println("2. Edit rental");
                            System.out.println("3. Customer Details ");
                            System.out.println("4. Print Daily Report");

                            System.out.println("9. Go Back");
                            System.out.println("Please choose one of the above options: ");

                            choice = readInteger(false);

                            System.out.println(" ");

                            if (choice != INVALID_INPUT) {
                                switch (choice) {
                                    case 1:
                                        while (choice != 8) {
                                            System.out.println();
                                            System.out.println("1. Insert branch");
                                            System.out.println("2. Delete branch");
                                            System.out.println("3. Show branch");
                                            System.out.println("8. Go back");
                                            System.out.println("Please choose one of the above options: ");

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
                                                    case 8:
                                                        break;
                                                    default:
                                                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                                                        break;
                                                }
                                            }
                                        }
                                        break;
                                    case 2:
                                        while (choice != 8) {
                                            System.out.println();
                                            System.out.println("1. Rent a vehicle");
                                            System.out.println("2. Return a vehicle");
                                            System.out.println("3. Show specific rented vehicle");
                                            System.out.println("4. Show rented vehicles");
                                            System.out.println("8. Go back");
                                            System.out.println("Please choose one of the above options: ");

                                            choice = readInteger(false);

                                            System.out.println(" ");

                                            if (choice != INVALID_INPUT) {
                                                switch (choice) {
                                                    case 1:
                                                        createRental();
                                                        break;
                                                    case 2:
                                                        returnRental();
                                                        break;
                                                    case 3:
                                                        showRentedVehicle();
                                                        break;
                                                    case 4:
                                                        delegate.showRentedVehicles(0,true);
                                                        break;
                                                    case 8:
                                                        break;
                                                    default:
                                                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                                                        break;
                                                }
                                            }
                                        }
                                        break;
                                    case 3:
                                        while (choice != 8) {
                                            System.out.println();
                                            System.out.println("1. Show customer");
                                            System.out.println("8. Go back");
                                            System.out.println("Please choose one of the above options: ");

                                            choice = readInteger(false);

                                            System.out.println(" ");

                                            if (choice != INVALID_INPUT) {
                                                switch (choice) {
                                                    case 1:
                                                        showCustomer();
                                                        break;
                                                    case 8:
                                                        break;
                                                    default:
                                                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                                                        break;
                                                }
                                            }
                                        }
                                        break;
                                    case 4:
                                        while (choice != 8) {
                                            System.out.println();
                                            System.out.println("1. Print Daily Rentals for Branch");
                                            System.out.println("2. Print Daily Rentals for All");
                                            System.out.println("8. Go back");
                                            System.out.println("Please choose one of the above options: ");

                                            choice = readInteger(false);

                                            System.out.println(" ");

                                            if (choice != INVALID_INPUT) {
                                                switch (choice) {
                                                    case 1:
                                                        while (choice != 7) {
                                                            System.out.println();
                                                            System.out.println("1. Print Daily Rentals for Abbotsford");
                                                            System.out.println("2. Print Daily Rentals for Chilliwack");
                                                            System.out.println("3. Print Daily Rentals for Vancouver");
                                                            System.out.println("7. Go back");
                                                            System.out.println("Please choose one of the above options: ");

                                                            choice = readInteger(false);

                                                            System.out.println(" ");
                                                            if (choice != INVALID_INPUT) {
                                                                switch (choice) {
                                                                    case 1:
                                                                        showReport("A","ABBOTSFORD");
                                                                        break;
                                                                    case 2:
                                                                        showReport("C","CHILLIWACK");
                                                                        break;
                                                                    case 3:
                                                                        showReport("V","VANCOUVER");
                                                                        break;
                                                                    case 7:
                                                                        break;
                                                                    default:
                                                                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                                                                        break;
                                                                }
                                                            }
                                                        }
                                                        break;
                                                    case 2:
                                                        showReportsAll();
                                                        break;
                                                    case 8:
                                                        break;
                                                    default:
                                                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                                                        break;
                                                }
                                            }
                                        }
                                        break;
                                    case 9:
                                        break;
                                    default:
                                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                                        break;
                                }
                            }

                        }
                        break;
                    case 10:
                        handleQuitOption();
                        break;
                }
            }
        }

	}

	private void branchDeleteOption() {
		String city = null;
		String location = null;

		while (location == null || location.length() <= 0 || city.length() <= 0) {
			System.out.println("Please enter the location of the branch you wish to delete: ");
			location = readLine().trim();
            System.out.println("Please enter the city of the branch you wish to delete: ");
            city = readLine().trim();
			if (location.length() > 0 && city.length() > 0) {
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

	private void showReservation(){
        int confNo = INVALID_INPUT;
        while (confNo == INVALID_INPUT) {
            System.out.println("Please enter the confirmation number for the reservation: ");
            confNo = readInteger(false);
            if (confNo != INVALID_INPUT) {
                delegate.showReservation(confNo,true);
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
		String dLicense = null;
		while (dLicense == null || dLicense.length() <= 0) {
			System.out.println("Please enter your driver´s license: ");
			dLicense = readLine().trim().toUpperCase();
		}
		if(delegate.checkCustomer(dLicense)){
			newCustomer(dLicense);
		}

		int confNo = delegate.lastConfNumber();

		String vtName = null;
		while (vtName == null || vtName.length() <= 0) {
			System.out.println("Please enter the vehicle type you wish to reserve: ");
			vtName = readLine().trim().toUpperCase();
		}


		Timestamp fromDate =null;
		boolean checkFromDate = false;
		while (fromDate == null || fromDate.before(new Timestamp(System.currentTimeMillis()+24*60*60*1000))){
		    if(checkFromDate){
                System.out.println(WARNING_TAG + " Start date has to be 24 hours from now.");

            }else{
                System.out.println("Please enter the start date of your reservation (The start day has to be at least 24 from now):");
            }
			checkFromDate=true;
			fromDate = createFromDate();
		}


		Timestamp toDate =null;
		boolean checkToDate = false;
		while (toDate == null || toDate.before(new Timestamp(fromDate.getTime()+24*60*60*1000))){
            if(checkToDate) {
                System.out.println(WARNING_TAG + " End date date has to be 24 hours after start date.");
            }else{
                System.out.println("Please enter the end date of your reservation (A reservation has to be at least 24 hours or 1 day):");
            }
            checkToDate=true;
			toDate = createToDate();
		}


		ReservationModel model = new ReservationModel(confNo,
				vtName,
				dLicense,
				fromDate,
				toDate);
		delegate.insertReservation(model, true);
	}

	private void reservationUpdateOption(){
        int confNo = INVALID_INPUT;
        while (confNo == INVALID_INPUT) {
            System.out.println("Please enter the confirmation number for the reservation you wish to update: ");
            confNo = readInteger(false);
        }

        int col = INVALID_INPUT;
        while (col == INVALID_INPUT) {
            System.out.println("Please enter the number that represents the column name you wish to update: ");
            System.out.println("1. Vehicle Type");
            System.out.println("2. Driver´s License");
            System.out.println("3. Start Date");
            System.out.println("4. End Date");
            System.out.println("5. Cancel update");
            col = readInteger(false);
        }
        if(1 == col ){
            System.out.println("Please enter the new vehicle type: ");
            String vtname = readLine().trim().toUpperCase();
            delegate.updateReservation(confNo, col, vtname, null);
        }  else if (col == 2){
            System.out.println("Please enter the new driver´s license: ");
            String dLicense = readLine().trim().toUpperCase();
            if(delegate.checkCustomer(dLicense)){
                newCustomer(dLicense);
            }
            delegate.updateReservation(confNo, col, dLicense, null);

        }   else if (col == 3){
            Timestamp fromDate = createFromDate();
            while(fromDate.before(new Timestamp(System.currentTimeMillis()+24*60*60*1000))){
                System.out.println(WARNING_TAG + " Your start date was not valid");
                System.out.println("Please enter the start date you wish to insert (The start day has to be at least 24 from now):");
                fromDate = createFromDate();
            }
            delegate.updateReservation(confNo, col, null, fromDate);

        }  else if (col == 4){
            Timestamp toDate = createToDate();
            while(toDate.before(new Timestamp(System.currentTimeMillis()+24*60*60*1000))){
                System.out.println(WARNING_TAG + " Your end date was not valid");
                System.out.println("Please enter the end date you wish to insert (The end day has to be at least 24 from now):");
                toDate = createFromDate();
            }
            delegate.updateReservation(confNo, col, null, toDate);

        }  else if (col != 5){
            System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
        }
    }

	private void branchInsertOption() {
        String location = null;
		while (location == null || location.length() <= 0) {
			System.out.print("Please enter the location of the branch you wish to insert: ");
			location = readLine().trim().toUpperCase();
		}



		String city = null;
		while (city == null || city.length() <= 0) {
			System.out.print("Please enter the city of the branch you wish to insert: ");
			city = readLine().trim().toUpperCase();
		}

		BranchModel model = new BranchModel(location, city);
		delegate.insertBranch(model);
	}

	private void handleVehicleSearch(){

        System.out.println("Please enter the vehicle type you wish to search for (Press enter for all vehicletypes): ");
        String vtName = readLine().trim().toUpperCase();

        System.out.println("Please enter the location you wish to search for (Press enter for all locations): ");
        String location = readLine().trim().toUpperCase();

        System.out.println("Please enter the city you wish to search for (Press enter for all cities): ");
        String city = readLine().trim().toUpperCase();

        System.out.println("Please enter the start date you wish to search for (Press enter for today): ");
		Timestamp fromDate = createFromDate();
        while(fromDate.before(new Timestamp(System.currentTimeMillis()))){
            System.out.println(WARNING_TAG + " Start date date can´t be in the past.");
            fromDate = createFromDate();
        }

        System.out.println("Please enter the end date you wish to search for (Press enter for all dates): ");
		Timestamp toDate = createToDate();
		while(toDate.before(new Timestamp(fromDate.getTime()+24*60*60*1000))) {
            System.out.println(WARNING_TAG + " End date date has to be 24 hours after start date.");
            toDate = createToDate();
        }
        delegate.showNumberVehicles(vtName, location, city, fromDate, toDate);
        String command = "";
        boolean checkCommand = false;
		while(!command.equals("SHOW") && !command.equals("EXIT")) {
		    if(checkCommand) {
                System.out.println(WARNING_TAG + " Your input was \"Show\" or \"Exit\"");
                System.out.println("Enter \"Show\" to show details of the cars.");
                System.out.println("Enter \"Exit\" to exit search.");
            }
		    else{
                System.out.println("Enter \"Show\" to show details of the cars.");
                System.out.println("Enter \"Exit\" to exit search.");
            }
		    checkCommand=true;
			command=readLine().trim().toUpperCase();
			if (command.equals("SHOW")) {
				delegate.showAvailableVehicles(vtName, location, city, fromDate, toDate);
			}
		}
    }

    private void createRental(){
	    int rid = delegate.lastRid();

        int confNo = INVALID_INPUT;
        boolean confNoCheck = false;
        while (!confNoCheck) {
            System.out.println("Please enter the confirmation number if you made a reservation prior: ");
            confNo = readInteger(true);
            confNoCheck=delegate.checkConfNo(confNo);
        }
        String dLicense = null;
        Timestamp fromDate =null;
        Timestamp toDate =null;

        //If a reservation has been made prior. Fetch information.
        if(confNo>0 ){
            ReservationModel resModel = delegate.showReservation(confNo, false)[0];
            dLicense = resModel.getdLicense();
            fromDate = resModel.getFromDate();
            toDate = resModel.getToDate();

        } else {
            confNo = NULL;

            while (dLicense == null || dLicense.length() <= 0) {
                System.out.println("Please enter your driver´s license: ");
                dLicense = readLine().trim();
            }
            if(delegate.checkCustomer(dLicense)){
                newCustomer(dLicense);
            }

            fromDate = createFromDate();
            while (fromDate.before(new Timestamp(System.currentTimeMillis()))){
                System.out.println(WARNING_TAG + " Start date can´t be in the past.");
                fromDate = createFromDate();
            }

            System.out.println("Please enter the end date (The time-interval has to be at least 24 hours):");
            toDate = createToDate();
            while (toDate.before(new Timestamp(fromDate.getTime()+24*60*60*1000))){
                System.out.println(WARNING_TAG + " End date date has to be 24 hours after start date.");
                toDate = createToDate();
            }
        }

        String vLicense=null;

        boolean check=false;
        while (!check) {
            System.out.println("Please enter the vehicle´s license: ");
            vLicense = readLine().trim().toUpperCase();
            check = delegate.checkVLicense(vLicense);
            if(!check){
                System.out.println("Vehicle license could not be found.");
            }
        }
        int odometer = delegate.showVehicles(vLicense,false)[0].getOdometer();

        String cdName = null;
        while (cdName == null || cdName.length() <= 0) {
            System.out.println("Please enter the card type of your credit card ");
            cdName = readLine().trim();
        }

        int cdNumber = INVALID_INPUT;
        while (cdNumber == INVALID_INPUT ) {
            System.out.println("Please enter the card number of your credit card: ");
            cdNumber = readInteger(false);
        }

        String expDate = null;
        while (expDate == null || expDate.length() <= 0) {
            System.out.println("Please enter the expiry date of your credit card ");
            expDate = readLine().trim();
        }

        RentalModel model = new RentalModel(
                rid,
                vLicense,
                dLicense,
                fromDate,
                toDate,
                odometer,
                cdName,
                cdNumber,
                expDate,
                confNo,
                NULL,
                null,
                NULL,
                null);
        delegate.insertRental(model);
    }

    private void returnRental(){
        int rid = INVALID_INPUT;
        boolean ridCheck = false;
        while ( rid==INVALID_INPUT || !ridCheck) {
            System.out.println("Please enter the rental number: ");
            rid = readInteger(true);
            ridCheck=delegate.checkRid(rid);
        }


        //Fetch information.
        RentalModel rentModel = delegate.showRentedVehicles(rid, false)[0];

        //Check if the vehicle is already rented?
        if(rentModel.getrDate()!=null){
            System.out.println(INFO_TAG + "Vehicle is already returned.");
            return;
        }
        String vLicense = rentModel.getvLicense();
        String dLicense = rentModel.getdLicense();
        Timestamp fromDate = rentModel.getFromDate();
        Timestamp toDate = rentModel.getToDate();
        int odometer = rentModel.getOdometer();
        String cdName = rentModel.getCardName();
        int cdNumber = rentModel.getCardNumber();
        String expDate = rentModel.getExpDate();
        int confNo = rentModel.getConfNo();
        int noDays = (int) ((toDate.getTime())- fromDate.getTime()) / (1000*60*60*24);

        VehiclesModel vecModel = delegate.showVehicles(vLicense,false)[0];
        VehicleTypesModel typeModel = delegate.showVehicleTypes(vecModel.getVtname(),false)[0];
        int value = noDays%7*(typeModel.getdRate()+typeModel.getdIRate())+
                noDays/7*(typeModel.getwRate()+typeModel.getwIRate());

        Timestamp rdate = new Timestamp(System.currentTimeMillis());

        String rFullTank = null;
        boolean checkTank = false;
        while (rFullTank==null || (!rFullTank.equals("YES") && !rFullTank.equals("NO"))) {
            if(checkTank){
                System.out.println(WARNING_TAG + " Your input has to be yes or no");
            }
            System.out.println("Please enter yes if the vehicle was returned with fulltank otherwise no ");
            rFullTank = readLine().trim();
            checkTank=true;
        }

        int rodometer = INVALID_INPUT;
        boolean checkOdo = false;
        while (rodometer == INVALID_INPUT || rodometer <= odometer) {
            if(checkOdo){
                System.out.println(WARNING_TAG + " Your input has to be bigger than the start value of odometer which is: "+odometer);
            }
            System.out.println("Please enter the value of the odometer: ");
            rodometer = readInteger(false);
            checkOdo=true;
        }

        RentalModel model = new RentalModel(
                rid,
                vLicense,
                dLicense,
                fromDate,
                toDate,
                odometer,
                cdName,
                cdNumber,
                expDate,
                confNo,
                rodometer,
                rFullTank,
                value,
                rdate);
        delegate.returnRental(model);
    }

    private void showRentedVehicle(){
        int rid = INVALID_INPUT;
        while (rid == INVALID_INPUT) {
            System.out.println("Please enter the rental number: ");
            rid = readInteger(false);
            if (rid != INVALID_INPUT) {
                delegate.showRentedVehicles(rid,true);
            }
        }
    }

    private void showCustomer(){
        System.out.println("Please enter the driver´s license for the customer (Press enter for all customers): ");
        String dLicense = readLine().trim().toUpperCase();
        delegate.showCustomer(dLicense);
    }

    private void showReport(String location, String city){
	    delegate.showReportLocation(location, city);
    }

    private void showReportsAll(){
	    delegate.showReportsAll();
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
			result = bufferedReader.readLine().toUpperCase();
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

		return Timestamp.valueOf(year+"-"+month+"-"+day+" "+hour+":"+minute+":00");
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
