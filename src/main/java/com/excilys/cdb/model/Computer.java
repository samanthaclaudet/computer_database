package com.excilys.cdb.model;

import java.time.LocalDateTime;

/**
 * Computer is composed of :
 * <ul>
 * <li>an identifier that cannot be modified</li>
 * <li>a name</li>
 * <li>a date of introduction (can be null)</li>
 * <li>a date of discontinuation (can be null)</li>
 * <li>a company</li>
 * </ul>
 * @see Company
 * 
 * @author sclaudet
 *
 */
public class Computer {
	private final int id;
	private String name;
	private LocalDateTime date_introduced;
	private LocalDateTime date_discontinued;
	private Company manufacturer;
	
	public Computer() {
		this.id = 0;
	}
	
	/**
	 * Constructor with id, used for updating
	 * 
	 * @param id
	 * @param name
	 * @param date_introduced
	 * @param date_discontinued
	 * @param manufacturer
	 */
	public Computer(int id, String name, LocalDateTime date_introduced, LocalDateTime date_discontinued, Company manufacturer) {
		this.id = id;
		this.name = name;
		this.date_introduced = date_introduced;
		this.date_discontinued = date_discontinued;
		this.manufacturer = manufacturer;
	}
	
	/**
	 * Constructor without id, used for inserting
	 * 
	 * @param name
	 * @param date_introduced
	 * @param date_discontinued
	 * @param manufacturer
	 */
	public Computer(String name, LocalDateTime date_introduced, LocalDateTime date_discontinued, Company manufacturer) {
		this.id = 0;
		this.name = name;
		this.date_introduced = date_introduced;
		this.date_discontinued = date_discontinued;
		this.manufacturer = manufacturer;
	}
	
	/**
	 * 
	 * @return the computer's id (int)
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @return the computer's name (String)
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return the computer's date of introduction (LocalDateTime)
	 */
	public LocalDateTime getDate_introduced() {
		return date_introduced;
	}
	
	/**
	 * 
	 * @param date_introduced
	 */
	public void setDate_introduced(LocalDateTime date_introduced) {
		this.date_introduced = date_introduced;
	}
	
	/**
	 * 
	 * @return the computer's date of discontinuation (LocalDateTime)
	 */
	public LocalDateTime getDate_discontinued() {
		return date_discontinued;
	}
	
	/**
	 * 
	 * @param date_discontinued
	 */
	public void setDate_discontinued(LocalDateTime date_discontinued) {
		this.date_discontinued = date_discontinued;
	}
	
	/**
	 * 
	 * @return the computer's company
	 * @see Company
	 */
	public Company getManufacturer() {
		return manufacturer;
	}
	
	/**
	 * 
	 * @param manufacturer
	 * 			a company
	 * @see Company
	 */
	public void setManufacturer(Company manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	/**
	 * The representation is "Computer#ID	name : NAME		introduced : DATE_INTRODUCED		discontinued : DATE_DISCONTINUED		manufacturer : MANUFACTURER"
	 * 
	 * @return String
	 */
	public String toString() {
		String computerToString = "Computer #"+this.id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((date_discontinued == null) ? 0 : date_discontinued
						.hashCode());
		result = prime * result
				+ ((date_introduced == null) ? 0 : date_introduced.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((manufacturer == null) ? 0 : manufacturer.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (date_discontinued == null) {
			if (other.date_discontinued != null)
				return false;
		} else if (!date_discontinued.equals(other.date_discontinued))
			return false;
		if (date_introduced == null) {
			if (other.date_introduced != null)
				return false;
		} else if (!date_introduced.equals(other.date_introduced))
			return false;
		if (id != other.id)
			return false;
		if (manufacturer == null) {
			if (other.manufacturer != null)
				return false;
		} else if (!manufacturer.equals(other.manufacturer))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
