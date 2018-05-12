package gr.uom.softeng2015.team28;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {

	public static Connection con;

	public static void main(String[] args) {
		ArrayList<User> admin_list = new ArrayList();
		ArrayList<User> client_list = new ArrayList();

		try {
			connectionQuerry();
			PreparedStatement st = con.prepareStatement("select * from admins");
			ResultSet r1=st.executeQuery();
			while(r1.next()) {
				User newUser = new Admins(r1.getString("Name"),r1.getString("SurName"),r1.getDate("Date"),r1.getInt("Code"));
				admin_list.add(newUser);
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage().toString());
		}

		AdminVerificationFrame adminFrame = new AdminVerificationFrame(admin_list,client_list);
		SerialConnectFrame serialFrame = new SerialConnectFrame();

	}

	public static void connectionQuerry()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://cobalt.mastermind33.eu:3306/SoftTech2015","team28","T28Pass");
			System.out.println("Remote DB connection established");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Remote server could not be connected");
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("Remote server could not be connected");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Remote db connection establishment error");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("False querry");
		}
	}
}
