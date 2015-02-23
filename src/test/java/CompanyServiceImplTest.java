import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
import static org.powermock.api.mockito.PowerMockito.*;

import org.powermock.reflect.Whitebox;
import org.powermock.modules.junit4.PowerMockRunner;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAOImpl;
import com.excilys.cdb.service.CompanyServiceImpl;

/**
 * Tests for {@link CompanyServiceImpl}.
 * 
 * @author sclaudet
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(CompanyDAOImpl.class) 
public class CompanyServiceImplTest {

	@Mock private static CompanyDAOImpl companyDao;
	private static  Company company1, company2, company3;
	private static List<Company> companies;
	
	@BeforeClass
	public static void setUp() {
		company1 = new Company(1, "Company 1");
		company2 = new Company(2, "Company 2");
		company3 = new Company(3, "Company 3");
		companies = new ArrayList<>();
		companies.add(company1);
		companies.add(company2);
		companies.add(company3);

		CompanyDAOImpl mockDAO = mock(CompanyDAOImpl.class);
		Whitebox.setInternalState(CompanyDAOImpl.class, "INSTANCE", mockDAO);

		when(mockDAO.getAll()).thenReturn(companies);
		when(mockDAO.getById(100)).thenReturn(null);
		when(mockDAO.getById(1)).thenReturn(company1);
		when(mockDAO.getById(2)).thenReturn(company2);
		
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				companies.remove(2);
				return null;
			}
		}).when(mockDAO).delete(3);
		
	}

	/**
	 * Test getById with a legal call invalid
	 */
	@Test
	public void testGetByIDNotExisting() {
		Company c = CompanyServiceImpl.INSTANCE.getById(100);
		assertNull(c);
	}

	/**
	 * Test getById with a legal call valid
	 */
	@Test
	public void testGetByIDEqual1() {
		Company cActual = CompanyServiceImpl.INSTANCE.getById(1);
		assertEquals(company1, cActual);
	}

	/**
	 * Test getAll
	 */
	@Test
	public void testGetAll() {
		List<Company> listActual = CompanyServiceImpl.INSTANCE.getAll();
		assertEquals(3, listActual.size());
		assertEquals(company2, listActual.get(1));
	}
	
	/**
	 * Test delete
	 */
	@Test
	public void testDelete() {
		CompanyServiceImpl.INSTANCE.delete(3);
		List<Company> listActual = CompanyServiceImpl.INSTANCE.getAll();
		assertEquals(2, listActual.size());
	}
	
}
