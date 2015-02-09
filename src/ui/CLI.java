package ui;

import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import persistence.CompanyDAO;
import persistence.ComputerDAO;
import persistence.ConnexionDB;
import model.Company;
import model.Computer;
import model.Page;

public class CLI {
	private static boolean runProgram = true;
	
	public static Scanner sc= new Scanner(System.in);
	
	public static void main(String[] args) throws SQLException {
		do {
			showMenu();
			requestTreatment();
		} while(runProgram);
		
		sc.close();
		ConnexionDB.closeConnexion();
		
		System.out.println("Fin du programme");
		System.exit(0);
		
	}
	
	public static void showMenu() {
		System.out.println("\n Que voulez-vous faire ?");
		System.out.println("Tapez 1 -> Lister les ordinateurs");
		System.out.println("Tapez 2 -> Lister les entreprises");
		System.out.println("Tapez 3 -> Voir les détails d'un ordinateur");
		System.out.println("Tapez 4 -> Ajouter un ordinateur");
		System.out.println("Tapez 5 -> Mettre à jour un ordinateur");
		System.out.println("Tapez 6 -> Supprimer un ordinateur");
		System.out.println("Tapez 7 -> Quitter \n");
	}
	
	public static void requestTreatment() {
		boolean validEntry;
		do {
			System.out.print("Votre choix : ");
			String choix = sc.nextLine();
			validEntry = choixUtilisateur(choix);
		} while (!validEntry);
	}
	
	public static boolean choixUtilisateur(String choix) {
		switch (choix) {
		case "1" : 
			System.out.println("Voulez-vous utiliser la fonction de pagination ? O/N");
			String answer = sc.nextLine();
			if (answer.toUpperCase().matches("O")) {
				ComputerDAO cdao = new ComputerDAO();
				ArrayList<Computer> CL = null;;
				try {
					CL = cdao.getComputersList();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				Page p = new Page(10);
				p.miseEnPage(0, CL);
				p.afficherPage();
				
				menuPage(p, CL);				
			}
			else {
				listComputers();
			}
			return true;
		case "2" : 
			listCompanies();
			return true;
		case "3" :
			showComputer();
			return true;
		case "4" : 
			createComputer();
			return true;
		case "5" : 
			updateComputer();
			return true;
		case "6" : 
			deleteComputer();
			return true;
		case "7" :
			runProgram = false;
			return true;
		default : 
			System.out.println("Entrée non valide, veuillez recommencer");
			return false;
		}		
	}
	
	public static void menuPage(Page p, ArrayList<Computer> CL) {
		boolean end = false;
		do {
			System.out.println("Précédant : P");
			System.out.println("Suivant : N");
			System.out.println("Premiere page : Z");
			System.out.println("Fin : F");
			String answer = sc.nextLine();
			if (answer.toUpperCase().matches("P")) {
				p.previousPage(CL);
			}
			if (answer.toUpperCase().matches("N")) {
				p.nextPage(CL);
			}
			if (answer.toUpperCase().matches("Z")) {
				p.firstPage(CL);
			}
			if (answer.toUpperCase().matches("F")) {
				end = true;
			}
		} while (!end);
	}
	
	public static void listComputers() {
		
		ComputerDAO cdao = new ComputerDAO();

		ArrayList<Computer> CL;
		try {
			CL = cdao.getComputersList();
			for(Computer c : CL ) {
				System.out.println(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void listCompanies() {
		 
		CompanyDAO cdao = new CompanyDAO();

		ArrayList<Company> CL;
		try {
			CL = cdao.getCompaniesList();
			for(Company c : CL ) {
				System.out.println(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void showComputer() {
		System.out.println("Quel est l'ID de l'ordinateur que vous souhaitez afficher ?");
		int id = checkId();

		ComputerDAO cdao = new ComputerDAO();
		try {
			Computer c = cdao.getAComputer(id);
			if (c != null) {
				System.out.println(c);
			} else {
				System.out.println("pas d'ordinateur à l'id sélectionné");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void createComputer() {
		Computer c = setComputerData();
		System.out.println("Ordinateur a rajouter : " + c);
		
		ComputerDAO cdao = new ComputerDAO();
		try {
			cdao.setAComputer(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateComputer() {
		System.out.println("Quel est l'ID de l'ordinateur que vous souhaitez modifier ?");
		int id = checkId();
		Computer newc = null;
		try {
			ComputerDAO cdao = new ComputerDAO();
			Computer c = cdao.getAComputer(id);
			if (c != null) {
				newc = updateComputerData(c);
				cdao.setAComputer(id, newc);
			} else {
				System.out.println("pas d'ordinateur à l'id sélectionné");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteComputer(){
		System.out.println("Quel est l'ID de l'ordinateur que vous souhaitez supprimer ?");
		int id = checkId();
		ConnexionDB conn = ConnexionDB.getInstance();
		conn.executeQueryAction("DELETE FROM computer WHERE id="+id);
	}
	
	public static Computer setComputerData() {
		System.out.println("Veuillez entrer le nom du nouvel ordinateur");
		String name = sc.nextLine();
		System.out.println("Veuillez entrer la date de mise en circulation, format yyyy-MM-dd HH:mm (null si non renseigné)");
		LocalDateTime ldti = checkDate();
		System.out.println("Veuillez entrer la date de fin de circulation, format yyyy-MM-dd HH:mm (null si non renseigné)");
		LocalDateTime ldtd = checkDate();
		System.out.println("Veuillez entrer l'id de l'entreprise (ou 0 si null)");
		Company cy = checkCompany();
		
		Computer c = new Computer(name, ldti, ldtd, cy);
		return c;
	}
	
	public static Computer updateComputerData(Computer c) {

		System.out.println("Voulez-vous entrer un nouveau nom d'ordinateur ? O/N");
		String answer = sc.nextLine();
		if (answer.toUpperCase().matches("O")) {
			System.out.println("Veuillez entrer le nouveau nom");
			String name = sc.nextLine();
			c.setName(name);
		}

		System.out.println("Voulez-vous entrer une nouvelle date de mise en circulation ? O/N");
		answer = sc.nextLine();
		if (answer.toUpperCase().matches("O")) {
			System.out.println("Veuillez entrer la nouvelle date, format yyyy-MM-dd HH:mm (null si non renseigné)");
			LocalDateTime ldti = checkDate();
			c.setDate_introduced(ldti);
		}

		System.out.println("Voulez-vous entrer une nouvelle date de fin de circulation ? O/N");
		answer = sc.nextLine();
		if (answer.toUpperCase().matches("O")) {
			System.out.println("Veuillez entrer la nouvelle date, format yyyy-MM-dd HH:mm (null si non renseigné)");
			LocalDateTime ldtd = checkDate();
			c.setDate_discontinued(ldtd);
		}

		System.out.println("Voulez-vous entrer une nouvelle entreprise ? O/N");
		answer = sc.nextLine();
		if (answer.toUpperCase().matches("O")) {
			System.out.println("Veuillez entrer l'id de la nouvelle entreprise (ou 0 si null)");
			Company cy = checkCompany();
			c.setManufacturer(cy);
		}
		return c;
	}
	
	public static int checkId() {
		boolean validEntry = true;
		int id = 0;
		do {
			String idx = sc.nextLine();
			try {
				id = Integer.parseInt(idx);
				validEntry = true;
			} catch (NumberFormatException e) {
				System.out.println ("Veuillez entrer un entier");
				validEntry = false;
			}
		} while (!validEntry);
		return id;
	}
	
	public static LocalDateTime checkDate() {
		boolean validEntry = true;
		LocalDateTime ldt = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		do {
			String date = sc.nextLine();
			if (date.equals("null")) {
				return null;
			}
			try {
				ldt = LocalDateTime.parse(date, formatter);
				validEntry = true;
			} catch (DateTimeException e) {
				System.out.println ("Format non valide, veuillez entrer une date selon le modèle yyyy-MM-dd HH:mm ou null");
				validEntry = false;
			}
		} while (!validEntry);
		return ldt;
	}
	
	public static Company checkCompany() {
		boolean validEntry = true;
		int manufacture = 0;
		Company cy = null;
		do {
			listCompanies();
			manufacture = checkId();
		
			CompanyDAO cdao = new CompanyDAO();
			
			try {
				if (manufacture == 0) {
					return null;
				}
				else if (manufacture > 0) {
					cy = cdao.getACompany(manufacture);
				}
				if (cy == null || manufacture < 0) {
					 System.out.println("pas d'entreprise à l'id sélectionné ! Veuillez entrer une nouvelle valeur \n");
					 validEntry = false;
				} else {
					validEntry = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} while (!validEntry);
		return cy;
	}
	
}
