package model;

import java.util.ArrayList;

public class Page {

	private int nbComputerPerPage;
	private Computer[] computerList;
	private int idx = 0;
	boolean hasNext = true;
	
	public Page(int nb) {
		this.nbComputerPerPage = nb;
		computerList = new Computer[nb];
	}
	
	public void miseEnPage(int idx, ArrayList<Computer> c) {
		int cpt = 0;
		for (int i=idx*this.nbComputerPerPage; i<idx*this.nbComputerPerPage + this.nbComputerPerPage; ++i) {
			if (i>=c.size()) {
				computerList[cpt] = null;
			}
			computerList[cpt] = c.get(i);
			cpt++;
		}
		if ((idx+1)*this.nbComputerPerPage >= c.size()) {
			hasNext = false;
		}
	}
	
	public void afficherPage() {
		for (Computer c : computerList) {
			System.out.println(c);
		}
	}
	
	public void nextPage(ArrayList<Computer> c) {
		if (hasNext) {
		 idx ++;
		 miseEnPage(idx, c);
		 afficherPage();
		}		
	}
	
	public void previousPage(ArrayList<Computer> c) {
		if (idx > 0) {
			idx--;
			hasNext = true;
			miseEnPage(idx, c);
			afficherPage();
		}
	}
	
	public void firstPage(ArrayList<Computer> c) {
		idx = 0;
		hasNext = true;
		miseEnPage(idx, c);
		afficherPage();
	}
	
	public Computer[] getComputerList() {
		return computerList;
	}
	
}
