import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.powermock.api.mockito.PowerMockito.*;

import org.powermock.reflect.Whitebox;
import org.powermock.modules.junit4.PowerMockRunner;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAOImpl;
import com.excilys.cdb.service.CompanyServiceImpl;
import com.excilys.cdb.service.ComputerServiceImpl;

/**
 * Tests for {@link CompanyServiceImpl}.
 * 
 * @author sclaudet
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ComputerDAOImpl.class) 
public class ComputerServiceImplTest {

	@Mock private static ComputerDAOImpl computerDao;
	private static  Computer computer1, computer2, computer3, computer4;
	private static Company company1, company2;
	private static LocalDateTime ldt1, ldt2;
	private static List<Computer> computers;
	
	@BeforeClass
	public static void setUp() {
		ldt1 = LocalDateTime.parse("2010-10-10 10:10", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		ldt2 = LocalDateTime.parse("2011-11-11 11:11", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		
		company1 = new Company(1, "Company 1");
		company2 = new Company(2, "Company 2");
		
		computer1 = new Computer(1, "Computer 1", ldt1, ldt2, company1);
		computer2 = new Computer(2, "Computer 2", ldt2, null, null);
		computer3 = new Computer(3, "Computer 3", null, null, company2);
		computer4 = new Computer(4, "Computer 4", null, null, null);
		computers = new ArrayList<>();
		computers.add(computer1);
		computers.add(computer2);
		computers.add(computer3);

		ComputerDAOImpl mockDAO = mock(ComputerDAOImpl.class);
		Whitebox.setInternalState(ComputerDAOImpl.class, "INSTANCE", mockDAO);

		when(mockDAO.getAll()).thenReturn(computers);
		when(mockDAO.getById(100)).thenReturn(null);
		when(mockDAO.getById(1)).thenReturn(computer1);
		when(mockDAO.getById(2)).thenReturn(computer2);
		when(mockDAO.getById(3)).thenReturn(computer3);
		
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				computers.remove(2);
				return null;
			}
		}).when(mockDAO).delete(3);
		
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				computers.add(computer4);
				return null;
			}
		}).when(mockDAO).set(any(Computer.class));
		
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				Computer c = computers.get(2);
				c.setCompany(company1);
				return null;
			}
		}).when(mockDAO).set(any(Computer.class));
		
	}

	/**
	 * Test getById with a legal call invalid
	 */
	@Test
	public void testGetByIDNotExisting() {
		Computer c = ComputerServiceImpl.INSTANCE.getById(100);
		assertNull(c);
	}

	/**
	 * Test getById with a legal call valid
	 */
	@Test
	public void testGetByIDEqual1() {
		Computer cActual = ComputerServiceImpl.INSTANCE.getById(1);
		assertEquals(computer1, cActual);
	}

	/**
	 * Test getAll
	 */
	@Test
	public void testGetAll() {
		List<Computer> listActual = ComputerServiceImpl.INSTANCE.getAll();
		assertEquals(3, listActual.size());
		assertEquals(computer2, listActual.get(1));
	}
	
	/**
	 * Test delete
	 */
	@Test
	public void testDelete() {
		ComputerServiceImpl.INSTANCE.delete(3);
		List<Computer> listActual = ComputerServiceImpl.INSTANCE.getAll();
		assertEquals(2, listActual.size());
	}
	
	/**
	 * Test add
	 */
	@Test
	public void testAdd() {
		ComputerServiceImpl.INSTANCE.set(new Computer("Computer 4", null, null, null));
		List<Computer> listActual = ComputerServiceImpl.INSTANCE.getAll();
		assertEquals(3, listActual.size());
	}
	
	/**
	 * Test update
	 */
	@Test
	public void testUpdate() {
		ComputerServiceImpl.INSTANCE.update(3, new Computer("Computer 3", null, null, company1));
		Computer c = ComputerServiceImpl.INSTANCE.getById(3);
		assertEquals(new Computer(3, "Computer 3", null, null, company1), c);
	}
	
}
