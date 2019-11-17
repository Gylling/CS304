package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.BranchModel;
import model.ReservationModel;
import model.VehiclesModel;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
	private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";
	
	private Connection connection = null;
	
	public DatabaseConnectionHandler() {
		try {
			// Load the Oracle JDBC driver
			// Note that the path could change for new drivers
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}
	
	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void deleteReservation(int confNo) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM Reservations WHERE confNo = ?");
			ps.setInt(1, confNo);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Reservation " + confNo + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertReservation(ReservationModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO Reservation VALUES (?,?,?,?,?,?,?)");
			ps.setInt(1, model.getConfNo());
			ps.setString(2, model.getVtName());
			ps.setString(3, model.getdLicense());
			ps.setString(4, model.getFromDate());
			ps.setString(5, model.getFromTime());
			ps.setString(6, model.getToDate());
			ps.setString(7, model.getToTime());

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public ReservationModel[] getReservationInfo() {
		ArrayList<ReservationModel> result = new ArrayList<ReservationModel>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Reservations");

//    		// get info on ResultSet
//    		ResultSetMetaData rsmd = rs.getMetaData();
//
//    		System.out.println(" ");
//
//    		// display column names;
//    		for (int i = 0; i < rsmd.getColumnCount(); i++) {
//    			// get column name and print it
//    			System.out.printf("%-15s", rsmd.getColumnName(i + 1));
//    		}

			while(rs.next()) {
				ReservationModel model = new ReservationModel(rs.getInt("confNo"),
						rs.getString("vtName"),
						rs.getString("dLicense"),
						rs.getString("fromDate"),
						rs.getString("fromTime"),
						rs.getString("toDate"),
						rs.getString("toTime"));
				result.add(model);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new ReservationModel[result.size()]);
	}


	public void deleteBranch(String location, String city) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM branch WHERE location = ? AND city = ?");
			ps.setString(1, location);
            ps.setString(2, city);
			
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Branch " + location + ", " + city + " does not exist!");
			}
			
			connection.commit();
	
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}
	
	public void insertBranch(BranchModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO branch VALUES (?,?)");
			ps.setString(1, model.getLocation());
			ps.setString(2, model.getCity());
			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}
	
	public BranchModel[] getBranchInfo() {
		ArrayList<BranchModel> result = new ArrayList<BranchModel>();

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM branch");

//    		// get info on ResultSet
//    		ResultSetMetaData rsmd = rs.getMetaData();
//
//    		System.out.println(" ");
//
//    		// display column names;
//    		for (int i = 0; i < rsmd.getColumnCount(); i++) {
//    			// get column name and print it
//    			System.out.printf("%-15s", rsmd.getColumnName(i + 1));
//    		}

			while(rs.next()) {
				BranchModel model = new BranchModel(
				        rs.getString("location"),
						rs.getString("city"));
				result.add(model);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new BranchModel[result.size()]);
	}

    public VehiclesModel[] getVehiclesInfo(String vtname, String location, String city, String fromDate, String fromTime, String toDate, String toTime) {
        ArrayList<VehiclesModel> result = new ArrayList<VehiclesModel>();
        String query = "SELECT * FROM VEHICLES WHERE ";
        Boolean checkAnd = false;
        if(!vtname.equals("")){
            query += "'" + vtname + "'" + " = " + "vtname";
            checkAnd = true;
        }
        if(!location.equals("")){
            if(checkAnd){
                query += " AND " + "'" + location + "'" + " = " + "location";
            } else {
                query += "'" +location + "'" + " = " + "location";
            }
            checkAnd = true;
        }
        if(!city.equals("")){
            if(checkAnd){
                query += " AND " + "'" + city + "'" + " = " + "city";
            } else {
                query += "'" + city + "'" + " = " + "city";
            }
            checkAnd = true;
        }
//        if(!fromDate.equals("")){
//            query += fromDate + " == " + "vtname AND ";
//        }
//        if(!fromTime.equals("")){
//            query += vtname + " == " + "vtname AND ";
//        }
//        if(!toDate.equals("")){
//            query += vtname + " == " + "vtname AND ";
//        }
//        if(!toTime.equals("")){
//            query += vtname + " = " + "vtname AND ";
//        }
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

//    		// get info on ResultSet
//    		ResultSetMetaData rsmd = rs.getMetaData();
//
//    		System.out.println(" ");
//
//    		// display column names;
//    		for (int i = 0; i < rsmd.getColumnCount(); i++) {
//    			// get column name and print it
//    			System.out.printf("%-15s", rsmd.getColumnName(i + 1));
//    		}

            while(rs.next()) {
                VehiclesModel model = new VehiclesModel(
                        rs.getInt("vid"),
                        rs.getString("vLicense"),
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getString("color"),
                        rs.getInt("odometer"),
                        rs.getString("status"),
                        rs.getString("vtname"),
                        rs.getString("location"),
                        rs.getString("city"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new VehiclesModel[result.size()]);
    }
	
	public boolean login(String username, String password) {
		try {
			if (connection != null) {
				connection.close();
			}
	
			connection = DriverManager.getConnection(ORACLE_URL, username, password);
			connection.setAutoCommit(false);
	
			System.out.println("\nConnected to Oracle!");
			return true;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return false;
		}
	}

	private void rollbackConnection() {
		try  {
			connection.rollback();	
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}
}
