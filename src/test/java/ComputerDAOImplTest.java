import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.ComputerDAOImpl;
import com.excilys.cdb.utilsdb.DatabaseLoader;

/**
 * Tests for {@link ComputerDAOImpl}.
 * 
 * @author sclaudet
 *
 */
public class ComputerDAOImplTest {

	@BeforeClass
	public static void setUp() {
		DatabaseLoader.INSTANCE.load();
	}
	
	/**
	 * Test getById with an illegal call
	 * 
	 */
	public void testGetByIDNegative() {
		
		Computer c = ComputerDAOImpl.INSTANCE.getById(-1);
		assertNull(c);
	}

	/**
	 * Test getById with a legal call invalid
	 */
	@Test
	public void testGetByIDNotExisting() {
		Computer c = ComputerDAOImpl.INSTANCE.getById(1000);
		assertNull(c);
	}
	
	/**
	 * Test getById with a legal call valid
	 */
	@Test
	public void testGetByIDEqual1() {
		Computer cActual = ComputerDAOImpl.INSTANCE.getById(1);
		Computer cExpected = new Computer(1, "MacBook Pro 15.4 inch", null,
				null, new Company(1, "Apple Inc."));
		assertEquals(cExpected, cActual);
	}
	
	/**
	 * Test getById with a legal call valid
	 */
	@Test
	public void testGetByIDEqual574() {
	Computer cActual = ComputerDAOImpl.INSTANCE.getById(574);
		LocalDateTime ldt = LocalDateTime.parse("2011-10-14 00:00",
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		Computer cExpected = new Computer(574, "iPhone 4S", ldt, null,
				new Company(1, "Apple Inc."));
		assertEquals(cExpected, cActual);
	}

	/**
	 * Test getAll
	 */
	@Test
	public void testGetAll() {
		List<Computer> listActual = ComputerDAOImpl.INSTANCE.getAll();
		assertEquals(574, listActual.size());
		LocalDateTime ldt = LocalDateTime.parse("2008-01-01 00:00",
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		Computer cExpected = new Computer(573, "Gateway LT3103U", ldt, null,
				null);
		assertEquals(cExpected, listActual.get(572));
	}

	/**
	 * Test getNbComputers
	 */
	@Test
	public void testGetNbComputers() {
		int nb = ComputerDAOImpl.INSTANCE.getNbComputers("");
		assertEquals(574, nb);
	}

	/**
	 * Test getPage
	 */
	@Test
	public void testGetPage() {		
		Page page = ComputerDAOImpl.INSTANCE.getPage(0, 10);
		assertEquals(10, page.getComputers().size());
		Page page2 = ComputerDAOImpl.INSTANCE.getPage(10, 10);
		assertEquals(10, page2.getComputers().size());
		assertEquals(101, page2.getComputers().get(0).getId());
	}

	/**
	 * Test set a new computer
	 */
	@Test
	public void testSet() {
		LocalDateTime ldt = LocalDateTime.parse("2008-08-09 03:12",
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		Computer cTest = new Computer("MyComputer2", ldt, null, new Company(42,
				"Research In Motion"));
		Computer cExpected = new Computer(575, "MyComputer2", ldt, null,
				new Company(42, "Research In Motion"));
		ComputerDAOImpl.INSTANCE.set(cTest);
		assertEquals(575, ComputerDAOImpl.INSTANCE.getNbComputers(""));
		assertEquals(cExpected, ComputerDAOImpl.INSTANCE.getById(575));
	}

	/**
	 * Test update a computer
	 */
	@Test
	public void testUpdate() {
		LocalDateTime ldt = LocalDateTime.parse("2001-01-06 00:00",
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		Computer cTest = new Computer("MyComputer", ldt, null, new Company(1,
				"Apple Inc."));
		Computer cExpected = new Computer(2, "MyComputer", ldt, null,
				new Company(1, "Apple Inc."));
		ComputerDAOImpl.INSTANCE.update(2, cTest);
		assertEquals(cExpected, ComputerDAOImpl.INSTANCE.getById(2));
	}

	/**
	 * Test delete a computer
	 */
	@Test
	public void testDelete() {
		ComputerDAOImpl.INSTANCE.delete(575);
		assertEquals(574, ComputerDAOImpl.INSTANCE.getNbComputers(""));
		assertNull(ComputerDAOImpl.INSTANCE.getById(575));
	}

}
