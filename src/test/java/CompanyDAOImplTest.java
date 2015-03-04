import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.impl.CompanyDAOImpl;
import com.excilys.cdb.utilsdb.DatabaseLoader;

/**
 * Tests for {@link CompanyDAOImpl}.
 * 
 * @author sclaudet
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/application-context-test.xml" })
public class CompanyDAOImplTest {

	@Autowired
	private CompanyDAOImpl companyDAOImpl;
	
	@BeforeClass
	public static void setUp() {
		DatabaseLoader.INSTANCE.load();
	}

	/**
	 * Test getById with an illegal call
	 * 
	 */
	@Test(expected=DataAccessException.class)
	public void testGetByIDNegative() throws DataAccessException {
		Company c = companyDAOImpl.getById(-1);
		assertNull(c);
	}

	/**
	 * Test getById with a legal call invalid
	 */
	@Test(expected=DataAccessException.class)
	public void testGetByIDNotExisting() throws DataAccessException {
		Company c = companyDAOImpl.getById(100);
		assertNull(c);
	}

	/**
	 * Test getById with a legal call valid
	 */
	@Test
	public void testGetByIDEqual43() {
		Company cActual = companyDAOImpl.getById(43);
		Company cExpected = new Company(43, "Samsung Electronics");
		assertEquals(cExpected, cActual);
	}

	/**
	 * Test getAll
	 */
	@Test
	public void testGetAll() {
		List<Company> listActual = companyDAOImpl.getAll();
		assertEquals(42, listActual.size());
		Company cExpected = new Company(42, "Research In Motion");
		assertEquals(cExpected, listActual.get(40));
	}

}
