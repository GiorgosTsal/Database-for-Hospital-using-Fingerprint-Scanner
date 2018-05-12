package gr.uom.softeng2015.team28;
import java.util.Date;


public class User {
	private String Name;
	private String Surname;
	private Date date_of_birth;
	private int finger_print;
	
	public User(String name,String surname,Date date,int code){
		Name=name;
		Surname=surname;
		date_of_birth=date;
		finger_print=code;
	}
	public void Print_User_Information(){
		System.out.println(this.getSurname()+" "+this.getName()+" "+this.getDate_of_birth()+" "+this.getFinger_print());
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getSurname() {
		return Surname;
	}
	public void setSurname(String surname) {
		Surname = surname;
	}
	public Date getDate_of_birth() {
		return date_of_birth;
	}
	public void setDate_of_birth(Date date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	public void setFinger_print(int finger_print) {
		this.finger_print = finger_print;
	}
	public int getFinger_print() {
		return finger_print;
	}
}
