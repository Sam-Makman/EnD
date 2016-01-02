package end;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Before;
import org.junit.Test;


public class DataFileTest {

	File fp;
	File fs;
	
	/**
	 * Sets fp to the professor csv file and fs to the student csv file
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		fp= new File ("ProfessorInfo.csv");
		fs = new File("StudentInfo.csv");
	}

	/**
	 * Tests the function that reads in the professor file
	 */
	@Test
	public void testReadProfessorFile() {
		String arr[][] = DataFile.readProfessorFile(fp);
		assertNotNull(arr);
		assertEquals(4,arr[0][1].length());
		assertEquals(arr[0][0], "1");
		assertEquals(arr[0][1],"Beck");
	}
	
	/**
	 * Tests the function that reads in the student file
	 */
	@Test
	public void testReadStudentFile(){
		String arr[][] = DataFile.readStudentFile(fs);
		assertNotNull(arr);
		assertEquals("16", arr[0][0]);
	//	assertEquals("32", arr[0][arr[0].length - 1]);
	}

}
