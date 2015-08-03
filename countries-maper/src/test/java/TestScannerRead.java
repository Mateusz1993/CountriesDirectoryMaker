import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.Scanner;

import org.junit.Test;

public class TestScannerRead {

	@Test
	public void test() {
		Scanner scanner = null;
		try {
			scanner = new Scanner("//home/mateusz/workspace/countries-mapper");
			String result = scanner.next();
			
			assertThat(result, is("//home/mateusz/workspace/countries-mapper"));
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		
	}
}
