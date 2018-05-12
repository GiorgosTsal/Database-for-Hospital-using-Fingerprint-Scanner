package gr.uom.softeng2015.team28;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientVerificationFrame extends JFrame{
	private ArrayList<Clients> client_list;
	private JTextField code_field,name,surname,date,code;
	private JPanel panel;
	private JLabel text1,lb1,lb2,lname,lsurname,ldate,lcode,lb3;
	private JButton verify,create,ok;
	private boolean verified;
	private PreparedStatement st;
	public ClientVerificationFrame(ArrayList clients){
		client_list = clients;
		code_field = new JTextField(10);
		panel = new JPanel();
		text1 = new JLabel("Give verification code: ");
		verify = new JButton("Verify User");
		create = new JButton("Create New User");
		ok = new JButton("OK");
		verified=false;
		
		try
        {
            PreparedStatement st = Main.con.prepareStatement("select * from clients");
            ResultSet r1=st.executeQuery();
            while(r1.next()) {
            	User newUser = new Clients(r1.getString("Name"),r1.getString("SurName"),r1.getDate("Date"),r1.getInt("Code"));
            	clients.add(newUser);
            }
        }

        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage().toString());
        }
		
		ButtonListener buttonlistener = new ButtonListener();
		verify.addActionListener(buttonlistener);
		create.addActionListener(buttonlistener);
		ok.addActionListener(buttonlistener);
		
		panel.add(text1);
		panel.add(code_field);
		panel.add(verify);
		panel.add(create);
		this.setContentPane(panel);
		
		this.setVisible(true);
		this.setSize(500, 500);
		this.setTitle("Finger Print Scaner");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==verify){
				String string_code = code_field.getText();
				int code = Integer.parseInt(string_code);
				for(Clients client:client_list){
					if(client.getFinger_print()==code){
						verified = true;
						client.Print_User_Information();
						lb1 = new JLabel("Client Verified");
						panel.add(lb1);
						repaint();
						printAll(getGraphics());
					}
				}
				if(verified==false){
					lb2 = new JLabel("Unkown Client");
					//panel.remove(lb1);
					panel.add(lb2);
					repaint();
					printAll(getGraphics());
				}
			}
			if(verified==false && e.getSource()==create){
				lname = new JLabel("Name:");
				lsurname = new JLabel("Surname:");
				ldate = new JLabel("Date:");
				lcode = new JLabel("Code:");
				name = new JTextField(10);
				surname = new JTextField(10);
				date = new JTextField(10);
				code = new JTextField(5);
				panel.add(lname);
				panel.add(name);
				panel.add(lsurname);
				panel.add(surname);
				panel.add(ldate);
				panel.add(date);
				panel.add(lcode);
				panel.add(code);
				panel.add(ok);
				repaint();
				printAll(getGraphics());
				}
			if(e.getSource()==ok){
				String newname = name.getText();
				String newsurname = surname.getText();
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				java.util.Date newdate= null;
				try {
					newdate = format.parse(date.getText());
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				int newcode = Integer.parseInt(code.getText());
				try {
					st = Main.con.prepareStatement("insert into clients values (?,?,?,?)");
					st.setInt(1, Integer.parseInt(code.getText()));
					st.setString(2,surname.getText());
					st.setString(3,name.getText());
					st.setDate(4, new java.sql.Date(newdate.getTime()));
					st.executeUpdate();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				Clients new_client = new Clients(newname,newsurname,newdate,newcode);
				client_list.add(new_client);
				panel.remove(name);
				panel.remove(lname);
				panel.remove(surname);
				panel.remove(lsurname);
				panel.remove(date);
				panel.remove(ldate);
				panel.remove(code);
				panel.remove(lcode);
				panel.remove(ok);
				lb3 = new JLabel("Your register is complete !");
				panel.add(lb3);
				repaint();
				printAll(getGraphics());
				new_client.Print_User_Information();
			}
		}
	}
}
