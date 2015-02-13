import static org.junit.Assert.*;

import java.util.List;
import org.junit.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAOImpl;

/**
 * Tests for {@link CompanyDAOImpl}.
 * 
 * @author sclaudet
 *
 */
public class CompanyDAOImplTest {

	@Test
	public void testGetByIDNotExisting() {
		int id = -1;
		Company c = CompanyDAOImpl.INSTANCE.getById(id);
		 assertNull(c);
	}
	
	@Test
	public void testGetByIDEqual1() {
		int id = 1;
		Company cActual = CompanyDAOImpl.INSTANCE.getById(id);
		Company cExpected = new Company(1, "Apple Inc.");
		assertEquals(cExpected, cActual);
	}

	@Test
	public void testGetByIDEqual43() {
		int id = 43;
		Company cActual = CompanyDAOImpl.INSTANCE.getById(id);
		Company cExpected = new Company(43, "Samsung Electronics");
		assertEquals(cExpected, cActual);
	}
	
	@Test
	public void testGetAll() {
		List<Company> listActual = CompanyDAOImpl.INSTANCE.getAll();
		assertEquals(42, listActual.size());
		Company cExpected = new Company(42, "Research In Motion");
		assertEquals(cExpected, listActual.get(40));
	}
}
