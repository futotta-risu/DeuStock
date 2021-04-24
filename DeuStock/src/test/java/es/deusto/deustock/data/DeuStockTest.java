package es.deusto.deustock.data;

import java.math.BigDecimal;
import static es.deusto.deustock.dataminer.cleaner.SocialTextCleaner.removeInvalidChars;
import static org.junit.Assert.*;

import org.junit.Test;

public class DeuStockTest {

	@Test
	public void testGetPrice() {
		BigDecimal testDecimal = 0.0000003;
		BigDecimal expectedDecimal = 0.0000003;

		assertEquals("The invalid chars remover doesn't work", expectedDecimal, removeInvalidChars(testDecimal));
	}

}
