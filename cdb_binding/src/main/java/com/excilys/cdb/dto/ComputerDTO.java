package com.excilys.cdb.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.validators.Date;

/**
 * Same as Computer, dates are passed as String
 * 
 * @author sclaudet
 *
 * @see com.excilys.cdb.model.Computer
 */
@Component
public class ComputerDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	@NotBlank
	private String name;
	@Date
	private String introduced;
	@Date
	private String discontinued;
	private Company company;

	public ComputerDTO() {
		this.id = 0;
		this.name = "";
		this.introduced = null;
		this.discontinued = null;
		this.company = null;
	}

	public ComputerDTO(int id, String name, String introduced, String discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String toString() {
		String computerToString = "Computer #" + this.id;
		computerToString += "\t name : " + this.name;
		if (this.introduced != null)
			computerToString += "\t\t introduced : " + this.introduced;
		else
			computerToString += "\t\t introduced : null";
		if (this.discontinued != null)
			computerToString += "\t\t discontinued : " + this.discontinued;
		else
			computerToString += "\t\t discontinued : null";
		if (this.company != null)
			computerToString += "\t\t company : " + this.company.toString();
		else
			computerToString += "\t\t company : null";
		return computerToString;
	}
}
