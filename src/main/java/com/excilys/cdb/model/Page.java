package com.excilys.cdb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.DTOMapper;
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
	private List<ComputerDTO> computers;
	private int idx = 0; // the number of the page we are currently on
	private boolean hasNext, hasPrevious;
	private int[] range;
	public Page() {}
	
	public Page(int nbPerPage, int nbComputers) {
		this.nbComputerPerPage = nbPerPage;
		this.nbPages = (int)Math.ceil((double)nbComputers/(double)nbPerPage);
		this.computers = new ArrayList<ComputerDTO>();
		this.hasNext = true;
		this.hasPrevious = false;
		this.range = new int[] {Math.max(0, idx-5), Math.min(nbPages, idx+5)};
	}
	
	public Page(int nbPerPage, int nbComputers, int idx) {
		this.nbComputerPerPage = nbPerPage;
		this.idx = idx;
		this.nbPages = (int)Math.ceil((double)nbComputers/(double)nbPerPage);
		this.computers = new ArrayList<ComputerDTO>();
		this.hasNext = true;
		this.hasPrevious = false;
		this.range = new int[] {Math.max(0, idx-5), Math.min(nbPages, idx+5)};
	}
	
	public int[] getRange() {
		return this.range;
	}

	public int getIdx() {
		return this.idx;
	}
	
	public void setIdx(int idx) {
		this.idx = idx;
	}
	
	public List<ComputerDTO> getComputers() {
		return computers;
	}
	
	public void setComputers(List<Computer> comp) {
		List<ComputerDTO> computersDTO = new ArrayList<ComputerDTO>();
	    for(Computer c : comp) {
	    	computersDTO.add(DTOMapper.computerToDTO(c));
	    }
		this.computers = computersDTO;
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
		for (ComputerDTO c : computers) {
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
			setComputers(ComputerDAOImpl.INSTANCE.getPerPage(idx, nbComputerPerPage));
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
			setComputers(ComputerDAOImpl.INSTANCE.getPerPage(idx, nbComputerPerPage));
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
		setComputers(ComputerDAOImpl.INSTANCE.getPerPage(idx, nbComputerPerPage));
		System.out.println(this.toString());
	}
	
	/**
	 * Goes to the last page and displays it
	 */
	public void lastPage() {
		idx = nbPages-1;
		hasNext = false;
		hasPrevious = true;
		setComputers(ComputerDAOImpl.INSTANCE.getPerPage(idx, nbComputerPerPage));
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
		setComputers(ComputerDAOImpl.INSTANCE.getPerPage(idx, nbComputerPerPage));
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
