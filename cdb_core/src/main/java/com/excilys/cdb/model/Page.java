package com.excilys.cdb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.excilys.cdb.model.Computer;

/**
 * Page is composed of :
 * <ul>
 * <li>the total number of computers to display</li>
 * <li>the number of computers per page</li>
 * <li>the total number of pages</li>
 * <li>a list of computers<\li>
 * <li>an index representing the current page number</li>
 * <li>a range of pages displayed</li>
 * </ul>
 * 
 * @author sclaudet
 *
 */
public class Page implements Serializable {

  private static final long serialVersionUID = 1L;

  private int               nbComputers;          // total number of computers
  private int               nbComputerPerPage;
  private int               nbPages;              // total number of pages
  private List<Computer>    computers;
  private int               idx;                  // the number of the page we are currently on
  private int[]             range;

  public Page() {}

  /**
   * Constructor from PageDTO
   */
  public Page(int nbComputers, int nbComputerPerPage, int nbPages, List<Computer> computers,
      int idx, int[] range) {
    this.nbComputers = nbComputers;
    this.nbComputerPerPage = nbComputerPerPage;
    this.nbPages = nbPages;
    this.computers = computers;
    this.idx = idx;
    this.range = range;
  }

  /**
   * Constructor for the first page
   * 
   * @param nbPerPage
   *            number of computers per page
   * @param nbComputers
   *            total number of computers
   */
  public Page(int nbPerPage, int nbComputers) {
    this.idx = 0;
    this.nbComputers = nbComputers;
    this.nbComputerPerPage = nbPerPage;
    this.nbPages = (int) Math.ceil((double) nbComputers / (double) nbPerPage);
    this.computers = new ArrayList<Computer>();
    this.range = new int[] { Math.max(0, this.idx - 5), Math.min(this.nbPages, this.idx + 5) };
  }

  /**
   * Constructor for a page at a specific index
   * 
   * @param nbPerPage
   *            number of computers per page
   * @param nbComputers
   *            total number of computers
   * @param idx
   *            number of the page
   */
  public Page(int nbPerPage, int nbComputers, int idx) {
    this(nbPerPage, nbComputers);
    this.idx = idx;
    this.range = new int[] { Math.max(0, idx - 5), Math.min(nbPages, idx + 5) };
  }

  /**
   * Updates the total number of pages and the range
   */
  private void updatePageValues() {
    this.nbPages = (int) Math.ceil((double) this.nbComputers / (double) this.nbComputerPerPage);
    this.range = new int[] { Math.max(0, this.idx - 5), Math.min(this.nbPages, this.idx + 5) };
  }

  /**
   * 
   * @return range
   */
  public int[] getRange() {
    return this.range;
  }

  /**
   * 
   * @return index
   */
  public int getIdx() {
    return this.idx;
  }

  /**
   * 
   * @param idx
   */
  public void setIdx(int idx) {
    this.idx = idx;
  }

  /**
   * 
   * @return number of computers
   */
  public int getNbComputers() {
    return this.nbComputers;
  }

  /**
   * 
   * @param nb
   */
  public void setNbComputers(int nb) {
    this.nbComputers = nb;
    updatePageValues();
  }

  /**
   * 
   * @return list of computers
   */
  public List<Computer> getComputers() {
    return computers;
  }

  /**
   * 
   * @param computers
   */
  public void setComputers(List<Computer> computers) {
    this.computers = computers;
  }

  /**
   * 
   * @return number of computers per page
   */
  public int getNbComputerPerPage() {
    return this.nbComputerPerPage;
  }

  /**
   * 
   * @return total number of pages
   */
  public int getNbPages() {
    return this.nbPages;
  }

  /**
   * The representation is "Page#IDX/NBPAGES" followed by the list of
   * computers
   * 
   * @see Computer#toString()
   */
  public String toString() {
    String pageToString = "Page #" + (this.idx + 1) + "/" + this.nbPages + "\n\n";
    for (Computer c : computers) {
      if (c != null) {
        pageToString += c.toString() + "\n";
      }
    }
    return pageToString;
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
  public boolean menuPage(Scanner sc) {

    System.out.println("Previous page : P");
    System.out.println("Next page : N");
    System.out.println("First page : F");
    System.out.println("Last page : L");
    System.out.println("Exit : E");
    String answer = sc.nextLine();
    if (answer.toUpperCase().matches("P")) {
      if (this.idx > 0) {
        this.idx--;
      }
    }
    if (answer.toUpperCase().matches("N")) {
      if (this.idx < nbPages) {
        this.idx++;
      }
    }
    if (answer.toUpperCase().matches("F")) {
      this.idx = 0;
    }
    if (answer.toUpperCase().matches("L")) {
      this.idx = this.nbPages - 1;
    }
    if (answer.toUpperCase().matches("E")) {
      return true;
    }
    return false;
  }

}
