package model;

import java.time.LocalDateTime;

public class Computer {
	int id;
	private String name;
	private LocalDateTime date_introduced;
	private LocalDateTime date_discontinued;
	private Company manufacturer;
	
	public Computer(int id, String name, LocalDateTime date_introduced, LocalDateTime date_discontinued, Company manufacturer) {
		this.id = id;
		this.name = name;
		this.date_introduced = date_introduced;
		this.date_discontinued = date_discontinued;
		this.manufacturer = manufacturer;
	}
	
	public Computer(String name, LocalDateTime date_introduced, LocalDateTime date_discontinued, Company manufacturer) {
		this.id = 0;
		this.name = name;
		this.date_introduced = date_introduced;
		this.date_discontinued = date_discontinued;
		this.manufacturer = manufacturer;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public LocalDateTime getDate_introduced() {
		return date_introduced;
	}
	
	public void setDate_introduced(LocalDateTime date_introduced) {
		this.date_introduced = date_introduced;
	}
	
	public LocalDateTime getDate_discontinued() {
		return date_discontinued;
	}
	
	public void setDate_discontinued(LocalDateTime date_discontinued) {
		this.date_discontinued = date_discontinued;
	}
	
	public Company getManufacturer() {
		return manufacturer;
	}
	
	public void setManufacturer(Company manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public String toString() {
		String computerToString = "Ordinateur n°"+this.id;
		computerToString += "\t name : "+this.name;
		if (this.date_introduced !=null)
			computerToString += "\t\t introduced : "+this.date_introduced.toString();
		else
			computerToString += "\t\t introduced : null";
		if (this.date_discontinued !=null)
			computerToString += "\t\t discontinued : "+this.date_discontinued.toString();
		else
			computerToString += "\t\t discontinued : null";
		if (this.manufacturer !=null)
			computerToString += "\t\t manufacturer : "+this.manufacturer.toString();
		else
			computerToString += "\t\t manufacturer : null";
		return computerToString;
	}
}
