package com.excilys.cdb.persistence.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.dto.DTOMapper;
import com.excilys.cdb.exceptions.SQLRuntimeException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.interfaces.ComputerDAO;
import com.excilys.cdb.persistence.mappers.ComputerMapperSpring;

/**
 * ComputerDAO makes the connection between the database and the Computer object
 * The available methods are :
 * <ul>
 * <li>getAll()</li>
 * <li>getById(int id)</li>
 * <li>getPage(String name, int idx, int size)</li>
 * <li>getNbComputers()</li>
 * <li>set(Computer c)</li>
 * <li>update(int id, Computer c)</li>
 * <li>delete(int id)</li>
 * <li>delete(int id, Connection conn)</li>
 * </ul>
 * 
 * @see Computer
 * 
 * @author sclaudet
 *
 */
@Repository
public class ComputerDAOImpl implements ComputerDAO {
	
	@Autowired
	private ComputerMapperSpring mapperSpring;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public ComputerDAOImpl() {
	}

	/**
	 * Counts the number of computers in the database
	 * 
	 * @return total number of computers in the database (int)
	 */
	public int getNbComputers(String name) {
		int nb = 0;
		name = "%" + name + "%";
		nb = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM computer LEFT JOIN company ON computer.company_id = company.id"
				+ " WHERE computer.name LIKE ? OR company.name LIKE ?", new Object[] {name, name}, Integer.class);
		return nb;
		
	}
	
	/**
	 * Gets a list of all computers
	 * 
	 * @return the list of all computers in the database
	 */
	public List<Computer> getAll() {
		return jdbcTemplate.query("SELECT * FROM computer", mapperSpring);
	}

	/**
	 * Gets a Computer by its id
	 * 
	 * @param id
	 * @return a Computer whose id was passed as parameter
	 */
	public Computer getById(int id) {
		Computer c = null;
		c = jdbcTemplate.queryForObject("SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?",
		    new Object[] {id}, mapperSpring);
		return c;
	}

	/**
	 * Gets a Page, can look for Computers by their name or the name of their company
	 * 
	 * @param name
	 * 			  name of the computer or the company you are looking for
	 * @param idx
	 *            offest for the select query
	 * @param size
	 *            number of computers per page
	 * @param orderBy
	 * 			  order by feature
	 * @return a Page with all computers containing the name
	 */
	public Page getPage(String name, int idx, int size, String orderBy) {
		name = "%" + name + "%";
		List<Computer> lc = null;
		int column;
		switch (orderBy) {
		case "computerName" :
			column = 2;
			//orderBy = "computer.name";
			break;
		case "introduced" :
			column = 3;
			//orderBy = "computer.introduced";
			break;
		case "discontinued" :
			column = 4;
			//orderBy = "computer.discontinued";
			break;
		case "company" :
			column = 7;
			//orderBy = "company.name";
			break;
		default :
			column = 1;
			//orderBy = "computer.id";
			break;
		}

		lc = jdbcTemplate.query("SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id"
				+ " WHERE computer.name LIKE ? OR company.name LIKE ? ORDER BY ? LIMIT ?, ?",
				new Object[] {name, name, column, idx * size, size}, mapperSpring);
		Page p = new Page(size, 0, idx);
		p.setComputers(DTOMapper.listToDto(lc));
		return p;
	}
	
	/**
	 * Inserts the computer passed in parameter into the database
	 * 
	 * @param c
	 *            the computer you want to add in the database
	 */
	public void set(Computer c) {
		String name = c.getName();
		Timestamp dateIn = null, dateDis = null;
		Integer companyId = null;
		if (c.getIntroduced() != null) {
			dateIn = Timestamp.valueOf(c.getIntroduced());
		}
		if (c.getDiscontinued() != null) {
			dateDis = Timestamp.valueOf(c.getDiscontinued());
		}
		if (c.getCompany() != null) {
			companyId = c.getCompany().getId();
		}
		
		jdbcTemplate.update("INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)",
		    new Object[] {name, dateIn, dateDis, companyId});
		
	}

	/**
	 * Replaces the computer in the database at the id passed in parameter by
	 * the computer passed in parameter
	 * 
	 * @param id
	 *            the id of the computer you want to modify in the database
	 * @param c
	 *            the new computer that will replace the one in the database
	 */
	public void update(int id, Computer c) {
		String name = c.getName();
		Timestamp dateIn = null, dateDis = null;
		Integer companyId = null;
		if (c.getIntroduced() != null) {
			dateIn = Timestamp.valueOf(c.getIntroduced());
		}
		if (c.getDiscontinued() != null) {
			dateDis = Timestamp.valueOf(c.getDiscontinued());
		}
		if (c.getCompany() != null) {
			companyId = c.getCompany().getId();
		}

		jdbcTemplate.update("UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?",
		    new Object[] {name, dateIn, dateDis, companyId, id});
	}

	/**
	 * Deletes a computer from the database at the id passed in parameter
	 * 
	 * @param id
	 *            the id of the computer you want to delete
	 */
	public void delete(int id) {
		jdbcTemplate.update("DELETE FROM computer WHERE id=?", new Object[] {id});
	}

	/**
	 * Deletes a computer from the database whose company has the id passed in parameter
	 * 
	 * @param id
	 *            the id of the company you want to delete
	 */
	public void deleteFromCompany(int id) throws SQLRuntimeException{
		try {
			jdbcTemplate.update("DELETE FROM computer WHERE company_id=?", new Object[] {id});
		} catch (DataAccessException e) {
			throw new SQLRuntimeException();
		}
	}
	
}