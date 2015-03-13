import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.*;

import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.impl.ComputerDAOImpl;
import com.excilys.cdb.service.impl.CompanyServiceImpl;
import com.excilys.cdb.service.impl.ComputerServiceImpl;

/**
 * Tests for {@link CompanyServiceImpl}.
 * 
 * @author sclaudet
 *
 */
@RunWith(MockitoJUnitRunner.class)
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(ComputerDAOImpl.class)
@ContextConfiguration(locations = { "classpath:/application-context-test.xml" })
public class ComputerServiceImplTest {

	@InjectMocks private static ComputerDAOImpl mockDAO; //computerDao;
	private static  Computer computer1, computer2, computer3, computer4;
	private static Company company1, company2;
	private static LocalDateTime ldt1, ldt2;
	private static List<Computer> computers;
	
	@Autowired
	ComputerServiceImpl computerServiceImpl;
	
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

		// when ComputerDAOImpl used to be a singleton...
		ComputerDAOImpl mockDAO = mock(ComputerDAOImpl.class);
		//Whitebox.setInternalState(ComputerDAOImpl.class, "INSTANCE", mockDAO);

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
				Computer c = computers.get(0);
				c.setCompany(company2);
				return null;
			}
		}).when(mockDAO).update(any(Computer.class));
		
	}

	/**
	 * Test getById with a legal call invalid
	 */
	@Test
	public void testGetByIDNotExisting() {
		Computer c = computerServiceImpl.getById(100);
		assertNull(c);
	}

	/**
	 * Test getById with a legal call valid
	 */
	@Test
	public void testGetByIDEqual1() {
		Computer cActual = computerServiceImpl.getById(1);
		assertEquals(computer1, cActual);
	}

	/**
	 * Test getAll
	 */
	@Test
	public void testGetAll() {
		List<Computer> listActual = computerServiceImpl.getAll();
		assertEquals(3, listActual.size());
		assertEquals(computer2, listActual.get(1));
	}
	
	/**
	 * Test delete
	 */
	@Test
	public void testDelete() {
		computerServiceImpl.delete(3);
		List<Computer> listActual = computerServiceImpl.getAll();
		assertEquals(2, listActual.size());
	}
	
	/**
	 * Test add
	 */
	@Test
	public void testAdd() {
		List<Computer> listActual = computerServiceImpl.getAll();
		assertEquals(2, listActual.size());
		computerServiceImpl.set(new Computer("Computer 4", null, null, null));
		listActual = computerServiceImpl.getAll();
		assertEquals(3, listActual.size());
	}
	
	/**
	 * Test update
	 */
	@Test
	public void testUpdate() {
		computerServiceImpl.update(new Computer("Computer 1", ldt1, ldt2, company2));
		Computer c = computerServiceImpl.getById(1);
		assertEquals(new Computer(1, "Computer 1", ldt1, ldt2, company2), c);
	}
	
}
