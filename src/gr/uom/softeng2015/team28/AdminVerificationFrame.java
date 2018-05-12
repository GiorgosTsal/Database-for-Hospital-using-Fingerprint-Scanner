package gr.uom.softeng2015.team28;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class AdminVerificationFrame extends JFrame{
	private ArrayList<Admins> admin_list;
	private ArrayList<Clients> client_list;
	private JTextField code_field;
	private JPanel panel;
	private JLabel text1;
	private JButton verify;
	private boolean verified;
	public AdminVerificationFrame(ArrayList admins,ArrayList clients){
		admin_list = admins;
		client_list = clients;
		code_field = new JTextField(10);
		panel = new JPanel();
		text1 = new JLabel("Give verification code: ");
		verify = new JButton("Verify User");
		
		ButtonListener buttonlistener = new ButtonListener();
		verify.addActionListener(buttonlistener);
		
		panel.add(text1);
		panel.add(code_field);
		panel.add(verify);
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
				for(Admins admin:admin_list){
					if(admin.getFinger_print()==code){
						verified = true;
						admin.Print_User_Information();
						JLabel lb1 = new JLabel("Admin Verified");
						panel.add(lb1);
						repaint();
						printAll(getGraphics());
						ClientVerificationFrame frame = new ClientVerificationFrame(client_list);
						dispose();
					}
				}
				if(verified==false){
					JLabel lb1 = new JLabel("Unkown User");
					panel.add(lb1);
					repaint();
					printAll(getGraphics());
				}
			}
		}
	}
}
