import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.*;

import org.powermock.reflect.Whitebox;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAOImpl;
import com.excilys.cdb.service.CompanyServiceImpl;

/**
 * Tests for {@link CompanyServiceImpl}.
 * 
 * @author sclaudet
 *
 */
@PrepareForTest(CompanyDAOImpl.class) 
public class CompanyServiceImplTest {

	@Mock private static CompanyDAOImpl companyDao;
	private static  Company company1, company2;
	private static List<Company> companies;
	
	@BeforeClass
	public static void setUp() {
		company1 = new Company(1, "Company 1");
		company2 = new Company(2, "Company 2");
		companies = new ArrayList<>();
		companies.add(company1);
		companies.add(company2);

		/*CompanyDAOImpl mockHelper = Whitebox.invokeConstructor(CompanyDAOImpl.class); 
		Whitebox.setInternalState(mockHelper, "yourdata",mockedData); 
		PowerMockito.mockStatic(CompanyDAOImpl.class); 
		Mockito.when(CompanyDAOImpl.INSTANCE).thenReturn(mockHelper);
*/
		CompanyDAOImpl mockDAO = mock(CompanyDAOImpl.class);
		Whitebox.setInternalState(CompanyDAOImpl.class, "INSTANCE", mockDAO);

		when(mockDAO.getAll()).thenReturn(companies);
		when(mockDAO.getById(100)).thenReturn(null);
		when(mockDAO.getById(1)).thenReturn(company1);
		when(mockDAO.getById(2)).thenReturn(company2);
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
		Company cExpected = new Company(1, "Company 1");
		assertEquals(cExpected, cActual);
	}

	/**
	 * Test getAll
	 */
	@Test
	public void testGetAll() {
		List<Company> listActual = CompanyServiceImpl.INSTANCE.getAll();
		assertEquals(2, listActual.size());
		Company cExpected = new Company(2, "Company 2");
		assertEquals(cExpected, listActual.get(1));
	}
	
}
