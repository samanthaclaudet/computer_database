package model;

public class Company {
	int id;
	String name;
	
	public Company(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		String companyToString = "Entreprise n°"+this.id;
		companyToString += "\t name : "+this.name;

		return companyToString;
	}
	
}
