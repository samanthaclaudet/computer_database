package com.excilys.cdb.model;

/**
 * Company is composed of :
 * <ul>
 * <li>an identifier that cannot be modified</li>
 * <li>a name that cannot be modified</li>
 * </ul>
 * 
 * @author sclaudet
 *
 */
public class Company {
	private final int id;
	private final String name;

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
	 * @return name (String)
	 */
	public String getName() {
		return name;
	}

	/**
	 * The representation is "Company#ID	name : NAME"
	 */
	public String toString() {
		return "Company #" + this.id + "\t name : " + this.name;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

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
