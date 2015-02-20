import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAOImpl;
import com.excilys.cdb.utilsdb.DatabaseLoader;

/**
 * Tests for {@link CompanyDAOImpl}.
 * 
 * @author sclaudet
 *
 */
public class CompanyDAOImplTest {

	@BeforeClass
	public static void setUp() {
		DatabaseLoader.INSTANCE.load();
	}

	/**
	 * Test getById with an illegal call
	 * 
	 * @throws SQLException
	 */
	@Test(expected = SQLException.class)
	public void testGetByIDNegative() throws SQLException{
		Company c = CompanyDAOImpl.INSTANCE.getById(-1);
		assertNull(c);
	}

	/**
	 * Test getById with a legal call invalid
	 */
	@Test
	public void testGetByIDNotExisting() {
		Company c = CompanyDAOImpl.INSTANCE.getById(100);
		assertNull(c);
	}

	/**
	 * Test getById with a legal call valid
	 */
	@Test
	public void testGetByIDEqual43() {
		Company cActual = CompanyDAOImpl.INSTANCE.getById(43);
		Company cExpected = new Company(43, "Samsung Electronics");
		assertEquals(cExpected, cActual);
	}

	/**
	 * Test getAll
	 */
	@Test
	public void testGetAll() {
		List<Company> listActual = CompanyDAOImpl.INSTANCE.getAll();
		assertEquals(42, listActual.size());
		Company cExpected = new Company(42, "Research In Motion");
		assertEquals(cExpected, listActual.get(40));
	}

}
