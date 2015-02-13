import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAOImpl;

/**
 * Tests for {@link ComputerDAOImpl}.
 * 
 * @author sclaudet
 *
 */
public class ComputerDAOImplTest {

	@Test
	public void testGetByIDNotExisting() {
		int id = -1;
		Computer c = ComputerDAOImpl.INSTANCE.getById(id);
		assertNull(c);
	}
	
	@Test
	public void testGetByIDEqual1() {
		int id = 1;
		Computer cActual = ComputerDAOImpl.INSTANCE.getById(id);
		Computer cExpected = new Computer(1,"MacBook Pro 15.4 inch",null,null,new Company(1, "Apple Inc."));
		assertEquals(cExpected, cActual);
	}

	@Test
	public void testGetByIDEqual574() {
		int id = 574;
		Computer  cActual = ComputerDAOImpl.INSTANCE.getById(id);
		LocalDateTime ldt = LocalDateTime.parse("2011-10-14 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		Computer cExpected = new Computer(574,"iPhone 4S",ldt,null,new Company(1, "Apple Inc."));
		assertEquals(cExpected, cActual);
	}
	
	@Test
	public void testGetAll() {
		List<Computer> listActual = ComputerDAOImpl.INSTANCE.getAll();
		assertEquals(574, listActual.size());
		LocalDateTime ldt = LocalDateTime.parse("2008-01-01 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		Computer cExpected = new Computer(573, "Gateway LT3103U", ldt, null, null);
		assertEquals(cExpected, listActual.get(572));
	}
	
	@Test
	public void testGetNbComputers() {
		int nb = ComputerDAOImpl.INSTANCE.getNbComputers();
		assertEquals(574, nb);
	}

	@Test
	public void testGetPerPage() {
		List<Computer> listActual = ComputerDAOImpl.INSTANCE.getPerPage(0, 10);
		assertEquals(10, listActual.size());
		listActual = ComputerDAOImpl.INSTANCE.getPerPage(10, 10);
		assertEquals(10, listActual.size());
		assertEquals(101, listActual.get(0).getId());
	}

	@Test
	public void testSet() {
		LocalDateTime ldt = LocalDateTime.parse("2008-08-09 03:12", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		Computer cTest = new Computer("MyComputer2",ldt,null,new Company(42, "Research In Motion"));
		Computer cExpected = new Computer(575, "MyComputer2",ldt,null,new Company(42, "Research In Motion"));
		ComputerDAOImpl.INSTANCE.set(cTest);
		assertEquals(575, ComputerDAOImpl.INSTANCE.getNbComputers());
		assertEquals(cExpected, ComputerDAOImpl.INSTANCE.getById(575));
	}
	
	@Test
	public void testUpdate() {
		LocalDateTime ldt = LocalDateTime.parse("2001-01-06 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		Computer cTest = new Computer("MyComputer",ldt,null,new Company(1, "Apple Inc."));
		Computer cExpected = new Computer(2, "MyComputer",ldt,null,new Company(1, "Apple Inc."));
		ComputerDAOImpl.INSTANCE.update(2, cTest);
		assertEquals(cExpected, ComputerDAOImpl.INSTANCE.getById(2));
	}
	
	@Test
	public void testDelete() {
		ComputerDAOImpl.INSTANCE.delete(575);
		assertEquals(574, ComputerDAOImpl.INSTANCE.getNbComputers());
		assertNull(ComputerDAOImpl.INSTANCE.getById(575));
	}
	
}

