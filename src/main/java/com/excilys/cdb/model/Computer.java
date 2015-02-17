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
	private LocalDateTime introduced;
	private LocalDateTime discontinued;
	private Company company;
	
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
	 * @param company
	 */
	public Computer(int id, String name, LocalDateTime date_introduced, LocalDateTime date_discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = date_introduced;
		this.discontinued = date_discontinued;
		this.company = company;
	}
	
	/**
	 * Constructor without id, used for inserting
	 * 
	 * @param name
	 * @param date_introduced
	 * @param date_discontinued
	 * @param company
	 */
	public Computer(String name, LocalDateTime date_introduced, LocalDateTime date_discontinued, Company company) {
		this.id = 0;
		this.name = name;
		this.introduced = date_introduced;
		this.discontinued = date_discontinued;
		this.company = company;
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
	public LocalDateTime getIntroduced() {
		return introduced;
	}
	
	/**
	 * 
	 * @param date_introduced
	 */
	public void setIntroduced(LocalDateTime dateIntroduced) {
		this.introduced = dateIntroduced;
	}
	
	/**
	 * 
	 * @return the computer's date of discontinuation (LocalDateTime)
	 */
	public LocalDateTime getDiscontinued() {
		return discontinued;
	}
	
	/**
	 * 
	 * @param date_discontinued
	 */
	public void setDiscontinued(LocalDateTime dateDiscontinued) {
		this.discontinued = dateDiscontinued;
	}
	
	/**
	 * 
	 * @return the computer's company
	 * @see Company
	 */
	public Company getCompany() {
		return company;
	}
	
	/**
	 * 
	 * @param company
	 * @see Company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}
	
	/**
	 * The representation is "Computer#ID	name : NAME		introduced : DATE_INTRODUCED		discontinued : DATE_DISCONTINUED		company : COMPANY"
	 * 
	 * @return String
	 */
	public String toString() {
		String computerToString = "Computer #"+this.id;
		computerToString += "\t name : "+this.name;
		if (this.introduced !=null)
			computerToString += "\t\t introduced : "+this.introduced.toString();
		else
			computerToString += "\t\t introduced : null";
		if (this.discontinued !=null)
			computerToString += "\t\t discontinued : "+this.discontinued.toString();
		else
			computerToString += "\t\t discontinued : null";
		if (this.company !=null)
			computerToString += "\t\t company : "+this.company.toString();
		else
			computerToString += "\t\t company : null";
		return computerToString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((discontinued == null) ? 0 : discontinued
						.hashCode());
		result = prime * result
				+ ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((company == null) ? 0 : company.hashCode());
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
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (id != other.id)
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
