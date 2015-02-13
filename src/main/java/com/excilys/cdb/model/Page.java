package com.excilys.cdb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.excilys.cdb.persistence.ComputerDAOImpl;

/**
 *  Page is composed of :
 * <ul>
 * <li>a number of results per page</li>
 * <li>the total number of pages</li>
 * <li>a list of computer<\li>
 * </ul>
 * 
 * @author sclaudet
 *
 */
public class Page {

	private int nbComputerPerPage; // set at instantiation, can't be changed after
	private int nbPages;
	private List<Computer> computerList;
	private int idx = 0; // the number of the page we are currently on
	private boolean hasNext, hasPrevious;
	
	public Page() {}
	
	public Page(int nbPerPage, int nbComputers) {
		this.nbComputerPerPage = nbPerPage;
		this.nbPages = (int)Math.ceil((double)nbComputers/(double)nbPerPage);
		computerList = new ArrayList<Computer>();
		this.hasNext = true;
		this.hasPrevious = false;
	}
	
	public List<Computer> getComputerList() {
		return computerList;
	}
	
	public void setComputerList(List<Computer> c) {
		this.computerList = c;
	}
	
	public int getNbComputerPerPage() {
		return this.nbComputerPerPage;
	}
	
	public int getNbPages() {
		return this.nbPages;
	}
	
	/**
	 * The representation is "Page#IDX/NBPAGES" followed by the list of computers
	 * @see Computer#toString()
	 */
	public String toString() {
		String pageToString = "Page #"+(this.idx+1)+"/"+this.nbPages+"\n\n";
		for (Computer c : computerList) {
			if (c != null) {
				pageToString += c.toString()+"\n";
			}
		}
		return pageToString;
	}
	
	/**
	 * Goes to the next page if there is one and displays it
	 * Otherwise prints "Last page"
	 */
	public void nextPage() {
		if (hasNext) {
			idx ++;
			if (idx == nbPages-1) {
				hasNext = false;
			} else {
				hasPrevious = true;
			}
			setComputerList(ComputerDAOImpl.INSTANCE.getPerPage(idx, nbComputerPerPage));
		 	System.out.println(this.toString());
		} else {
			System.out.println("Last page !");
		}
	}
	
	/**
	 * Goes to the previous page if there is one and displays it
	 * Otherwise prints "First page"
	 */
	public void previousPage() {
		if (hasPrevious) {
			idx--;
			if (idx == 0) {
				hasPrevious = false;
			} else {
				hasNext = true;
			}
			setComputerList(ComputerDAOImpl.INSTANCE.getPerPage(idx, nbComputerPerPage));
			System.out.println(this.toString());
		} else {
			System.out.println("First page !");
		}
	}
	
	/**
	 * Goes to the firstPage and displays it
	 */
	public void firstPage() {
		idx = 0;
		hasNext = true;
		hasPrevious = false;
		setComputerList(ComputerDAOImpl.INSTANCE.getPerPage(idx, nbComputerPerPage));
		System.out.println(this.toString());
	}
	
	/**
	 * Goes to the last page and displays it
	 */
	public void lastPage() {
		idx = nbPages-1;
		hasNext = false;
		hasPrevious = true;
		setComputerList(ComputerDAOImpl.INSTANCE.getPerPage(idx, nbComputerPerPage));
		System.out.println(this.toString());
	}
	
	/**
	 * Displays the menu for the page :
	 * <ul>
	 * <li>go to the first page</li>
	 * <li>go to the last page</li>
	 * <li>go to previous page</li>
	 * <li>go to next page</li>
	 * <li>exit</li>
	 * </ul>
	 * 
	 * @param sc
	 */
	public void menuPage(Scanner sc) {
		setComputerList(ComputerDAOImpl.INSTANCE.getPerPage(idx, nbComputerPerPage));
		System.out.println(this.toString());
		boolean end = false;
		do {
			System.out.println("Previous page : P");
			System.out.println("Next page : N");
			System.out.println("First page : F");
			System.out.println("Last page : L");
			System.out.println("Exit : E");
			String answer = sc.nextLine();
			if (answer.toUpperCase().matches("P")) {
				this.previousPage();
			}
			if (answer.toUpperCase().matches("N")) {
				this.nextPage();
			}
			if (answer.toUpperCase().matches("F")) {
				this.firstPage();
			}
			if (answer.toUpperCase().matches("L")) {
				this.lastPage();
			}
			if (answer.toUpperCase().matches("E")) {
				end = true;
			}
		} while (!end);
	}
	
}
