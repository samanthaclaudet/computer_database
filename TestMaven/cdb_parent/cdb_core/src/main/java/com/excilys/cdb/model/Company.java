package com.excilys.cdb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Company is composed of :
 * <ul>
 * <li>an identifier</li>
 * <li>a name</li>
 * </ul>
 * 
 * @author sclaudet
 *
 */
@Entity
@Table(name = "company")
public class Company {
  
    @Id
    @GeneratedValue
    @Column(name="id")
	private int id;
    
    @Column(name = "name")
	private String name;

	public Company() {
		this.id = 0;
		this.name = null;
	}

	/**
	 * 
	 * @param id
	 * @param name
	 */
	public Company(int id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * 
	 * @return id (int)
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId (int id) {
	  this.id = id;
	}
	
	/**
	 * 
	 * @return name (String)
	 */
	public String getName() {
		return name;
	}

	/**
     * 
     * @param name
     */
    public void setName (String name) {
      this.name = name;
    }
	
	/**
	 * The representation is "Company#ID	name : NAME"
	 */
	@Override
	public String toString() {
		return "Company #" + this.id + "\t name : " + this.name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Company other = (Company) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
