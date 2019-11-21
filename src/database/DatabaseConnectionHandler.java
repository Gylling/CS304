package database;

import java.sql.*;
import java.util.ArrayList;

import model.*;


/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
	private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";

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


	public void insertCustomer(CustomerModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO Customers VALUES (?,?,?)");
			ps.setString(1, model.getdLicense());
			ps.setString(2, model.getName());
			ps.setString(3, model.getdLicense());

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public CustomerModel[] getCustomerInfo() {
		ArrayList<CustomerModel> result = new ArrayList<>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Customers");

			while(rs.next()) {
				CustomerModel model = new CustomerModel(
						rs.getString("dLicense"),
						rs.getString("name"),
						rs.getString("address"));
				result.add(model);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new CustomerModel[result.size()]);
	}

	public boolean deleteReservation(int confNo) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM Reservations WHERE confNo = ?");
			ps.setInt(1, confNo);

			int rowCount = ps.executeUpdate();

			connection.commit();

			ps.close();
			return rowCount>0;


		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			return false;
		}
	}

	public void insertReservation(ReservationModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO Reservations VALUES (?,?,?,?,?)");
			ps.setInt(1, model.getConfNo());
			ps.setString(2, model.getVtName());
			ps.setString(3, model.getdLicense());
			ps.setTimestamp(4, model.getFromDate());
			ps.setTimestamp(5, model.getToDate());

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void updateReservation(ReservationModel model, int col){
	    String colName;
	    String newName;
	    if(col==4){
	        colName="toDate";
	        newName = "TIMESTAMP '"+model.getToDate().toString()+"'";
        } else if (col==3){
            colName="fromDate";
            newName = "TIMESTAMP '"+model.getFromDate().toString()+"'";
        } else if (col==2){
            colName="dLicense";
            newName = "'"+model.getdLicense()+"'";
        } else {
            colName="vtName";
            newName = "'"+model.getVtName()+"'";
        }

        try {
            String query = "UPDATE RESERVATIONS SET "+ colName+" = "+newName+" WHERE CONFNO = "+model.getConfNo();
            System.out.println("UPDATE RESERVATIONS SET "+ colName+" = "+newName+" WHERE CONFNO = "+model.getConfNo());

            Statement stmt = connection.createStatement();

            stmt.executeUpdate(query);


            connection.commit();

            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

	public ReservationModel[] getReservationInfo(int confNo) {
		ArrayList<ReservationModel> result = new ArrayList<>();
		String query;
		if(confNo<1)
		    query="SELECT * FROM Reservations ORDER BY CONFNO";
		else{
		    query="SELECT * FROM Reservations WHERE "+confNo +" = CONFNO ORDER BY CONFNO";
        }
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()) {
				ReservationModel model = new ReservationModel(rs.getInt("confNo"),
						rs.getString("vtName"),
						rs.getString("dLicense"),
						rs.getTimestamp("fromDate"),
						rs.getTimestamp("toDate"));
				result.add(model);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new ReservationModel[result.size()]);
	}


	public boolean deleteBranch(String location, String city) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM branch WHERE location = ? AND city = ?");
			ps.setString(1, location);
            ps.setString(2, city);

			int rowCount = ps.executeUpdate();

			connection.commit();

			ps.close();
			return rowCount>0;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			return false;
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
		ArrayList<BranchModel> result = new ArrayList<>();

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM branch");

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

    public VehiclesModel[] getAvailableVehicles(String vLicense, String vtname, String location, String city, Timestamp fromDate, Timestamp toDate) {
        ArrayList<VehiclesModel> result = new ArrayList<>();


        //Build the query for the vehicles, which meet the vehicletype and branch criteria.
        String query = "SELECT * FROM VEHICLES Ve";
        boolean checkAnd = false;
        if(!vLicense.equals("")){
            query += " WHERE '" + vLicense + "'" + " = " + "Ve.VLICENSE";
            checkAnd = true;
        }
        else {
            if (!vtname.equals("")) {
                query += " WHERE '" + vtname + "'" + " = " + "Ve.VTNAME";
                checkAnd = true;
            }
            if (!location.equals("")) {
                if (checkAnd) {
                    query += " AND " + "'" + location + "'" + " = " + "Ve.location";
                } else {
                    query += " WHERE '" + location + "'" + " = " + "Ve.location";
                }
                checkAnd = true;
            }
            if (!city.equals("")) {
                if (checkAnd) {
                    query += " AND " + "'" + city + "'" + " = " + "Ve.city";
                } else {
                    query += " WHERE '" + city + "'" + " = " + "Ve.city";
                }
                checkAnd = true;
            }
        }
		//remove vehicletypes that are fullybooked in timeinterval from the table with vehicles, which meet the vehicletype and branch criteria.
		//fullybooked means all the vehicles are rented or reserved for a given timeperiod.
		if(checkAnd){
			query += " AND Ve.VTNAME NOT IN (" +
					"SELECT VT.VTNAME " +
					"FROM VEHICLETYPES VT " +
					"WHERE " +
					"(SELECT COUNT(*) " +
					"FROM vehicles V " +
					"WHERE V.VTNAME = VT.VTNAME AND V.VLICENSE NOT IN (" +
					"SELECT Re.VLICENSE " +
					"FROM rentals Re " +
					"WHERE (TIMESTAMP '"+fromDate.toString()+"' < Re.fromDate " +
					"AND Re.fromDate < TIMESTAMP '"+toDate.toString()+"')" +
					" OR (TIMESTAMP '"+fromDate.toString()+"' < Re.toDate " +
					"AND Re.toDate < TIMESTAMP '"+toDate.toString()+"'))) " +
					"<=" +
					"(SELECT COUNT(*) " +
					"FROM reservations Rs " +
					"WHERE Rs.VTNAME = VT.VTNAME))";
		} else {
			query += " WHERE Ve.VTNAME NOT IN (" +
					"SELECT VT.VTNAME " +
					"FROM VEHICLETYPES VT " +
					"WHERE " +
					"(SELECT COUNT(*) " +
					"FROM vehicles V " +
					"WHERE V.VTNAME = VT.VTNAME AND V.VLICENSE NOT IN (" +
					"SELECT Re.VLICENSE " +
					"FROM rentals Re " +
					"WHERE (TIMESTAMP '"+fromDate.toString()+"' < Re.fromDate " +
					"AND Re.fromDate < TIMESTAMP '"+toDate.toString()+"')" +
					" OR (TIMESTAMP '"+fromDate.toString()+"' < Re.toDate " +
					"AND Re.toDate < TIMESTAMP '"+toDate.toString()+"'))) " +
					"= " +
					"(SELECT COUNT(*) " +
					"FROM reservations Rs " +
					"WHERE Rs.VTNAME = VT.VTNAME))";
		}

        //remove vehicles that are rented in timeinterval from the table with vehicles, which meet the vehicletype and branch criteria
		query += " AND Ve.VLICENSE NOT IN " +
				"(SELECT R.VLICENSE " +
				"FROM rentals R " +
				"WHERE (TIMESTAMP '"+fromDate.toString()+"' < R.fromDate " +
				"AND R.fromDate < TIMESTAMP '"+toDate.toString()+"') " +
				"OR (TIMESTAMP '"+fromDate.toString()+"' < R.toDate " +
				"AND R.toDate < TIMESTAMP '"+toDate.toString()+"'))";

		query += " ORDER BY Ve.VTNAME";
		try {

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);


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
			connection.commit();
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}

        return result.toArray(new VehiclesModel[result.size()]);
    }

    public VehiclesModel[] getVehicles(String vLicense){
        ArrayList<VehiclesModel> result = new ArrayList<>();
        String query;
        if(vLicense.equals(""))
            query="SELECT * FROM VEHICLES ORDER BY VTNAME";
        else{
            query="SELECT * FROM VEHICLES WHERE '"+ vLicense +"' = VLICENSE ORDER BY VTNAME";
        }
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
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

    public void insertRental(RentalModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO RENTALS VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, model.getRid());
            ps.setString(2, model.getvLicense());
            ps.setString(3, model.getdLicense());
            ps.setTimestamp(4, model.getFromDate());
            ps.setTimestamp(5, model.getToDate());
            ps.setInt(6, model.getOdometer());
            ps.setString(7, model.getCardName());
            ps.setInt(8, model.getCardNumber());
            ps.setString(9, model.getExpDate());

            ps.setInt(11, model.getrOdometer());
            ps.setString(12, model.getrFulltank());
            ps.setInt(13, model.getValue());
            ps.setTimestamp(14, model.getrDate());

            if(model.getConfNo()!=0){
				ps.setInt(10, model.getConfNo());
			} else {
				ps.setString(10, null);
			}

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public int last(String col, String table){
        int res = -1;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX("+ col +") FROM "+table);
            connection.commit();
            if(rs.next()) {
                res = rs.getInt("MAX("+col+")")+1;
            } else{
                res=1;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return res;
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
