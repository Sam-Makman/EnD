package end;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class ModelTest {

	private Model model;
	private Student[] students;
	private Section[] sections;
	
	@Before
	public void setUp() {
		model = new Model();
		model.runProgram( new File("ProfessorInfo.csv"), new File("StudentInfo.csv"), 19);
		students = model.getAllStudents();
		sections = model.getAllSections();
	}

	/***
	 * This test is customizable depending on if you're testing the students array, or the sections array
	 */
	@Test
	public void runProgramTest() {
		for(int i=0; i<students.length; i++){
			assertNotNull(students[i]);
			assertNotNull(students[i].getID());
		}
		
		for(int i=0; i<sections.length; i++){
			assertNotNull(sections[i]);
			assertNotNull(sections[i].getUniqueID());
		}
	}
	
	@Test
	public void testProfessors() {
		//Tests the first professor
		assertEquals("1", sections[0].getUniqueID());
		assertEquals("Beck", sections[0].getProfessor());
		
		//Tests the last professor
		assertEquals("34", sections[sections.length - 1].getUniqueID());
		assertEquals("Mirabile", sections[sections.length - 1].getProfessor());
	}
	
	@Test
	public void testStudents() {
		//Tests the first student
		assertEquals("16", students[0].getID());
		assertEquals("M", students[0].getGender());
		assertTrue(students[0].isAthlete());
		assertTrue(students[0].getTeams().contains(new Team("MGO")));
		assertEquals("1", students[0].getTransfer());
		assertTrue(students[0].getPrevProf().contains("Holzwarth"));
		String[] prev1 = {"5","22","32","19","20","32"};
		assertArrayEquals(prev1, students[0].getPreferences());
		
		//Tests the last student
		assertEquals("4989", students[students.length - 1].getID());
		assertEquals("F", students[students.length - 1].getGender());
		assertFalse(students[students.length - 1].isAthlete());
		assertTrue(students[students.length - 1].getPrevProf().contains("Morrill"));
		String[] prev2 = {"28","18","29","32","13","29"};
		assertArrayEquals(prev2, students[students.length - 1].getPreferences());
	}
	@Test
	public void testTeams(){
		assertEquals(new Team("FB"), students[15].getTeams().get(0));
		assertEquals(new Team("MTF"), students[15].getTeams().get(1));
	}

}
