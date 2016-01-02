package end;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author sam
 *
 */
public class AlgorithmTest {

	Algorithm algo;
	Student [] students;
	Section [] sections;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Model m = new Model();
		students = new Student[10];
		sections = new Section[2];
		sections[0] = new Section(5, "0", "bill", m);
		sections[1] = new Section(5, "1", "nye", m);
		
		students[0] = new Student(null, new String[] {"0", "1"}, "0", "M", null, null, null, null, null);
		students[1] = new Student(null, new String[] {"0", "1"}, "1", "M", null, null, null, null, null);
		students[2] = new Student(null, new String[] {"0", "1"}, "2", "M", null, null, null, null, null);
		students[3] = new Student(null, new String[] {"0", "1"}, "3", "M", null, null, null, null, null);
		students[4] = new Student(null, new String[] {"0", "1"}, "4", "M", null, null, null, null, null);
		students[5] = new Student(null, new String[] {"0", "1"}, "5", "F", null, null, null, null, null);
		students[6] = new Student(null, new String[] {"0", "1"}, "6", "F", null, null, null, null, null);
		students[7] = new Student(null, new String[] {""}, "7", "F", null, null, null, null, null);
		students[8] = new Student(null, new String[] {""}, "8", "F", null, null, null, null, null);
		students[9] = new Student(null, new String[] {""}, "9", "F", null, null, null, null, null);
		algo = new Algorithm(sections,students);

	}

	/**
	 * Test method for {@link end.Algorithm#sortStudents()}.
	 */
	@Test
	public void testFindWildcards() {
		assertTrue(algo.getWildcards().isEmpty());
		algo.sortStudents();
		assertEquals(algo.getWildcards().size(), 3);
		assertTrue(algo.getWildcards().contains(students[8]));
		assertEquals(algo.getStudentsNotPlaced().size(), 7);
	}

	/**
	 * Test method for {@link end.Algorithm#assignStudent(java.lang.String[], end.Student)}.
	 */
	@Test
	public void testAssignStudent() {
		for(Student s : students){
			algo.assignStudent(s.getPreferences(), s);
		}
		for(Student s: algo.getSectionTemp()[0].getStudents()){
			assertTrue(algo.getSectionTemp()[0].getStudents().contains(s));
		}
		
	}

}
