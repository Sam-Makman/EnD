package end;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class SectionTest {

	private Section section;
	private Student student, student2, student3;
	private	Model model;
	@Before
	public void setUp() {
		model = new Model();
		ArrayList<String> empty = new ArrayList<String>();
		section = new Section(19, "13", "FooBar", model);

		ArrayList<String> prof = new ArrayList<String>();
		prof.add("FooBar");
		ArrayList<String> prof2 = new ArrayList<String>();
		prof2.add("Foo");

		String preference[] = {"13", "3", "4", "5", "6", "2" };
		ArrayList<String> athlete = new ArrayList<String>();
		athlete.add("B");
		model.addAllTeams(athlete);
		
		student = new Student(prof, preference, "3", "M", athlete ,"1","1","1","1");
		student2 = new Student(prof2, preference, "4", "F", empty,"","","","");
		student3 = new Student(empty, preference, "5", "F", empty,"","","","");
		section.addStudent(student);
		section.addStudent(student2);
		section.addStudent(student3);
		model.setNumberOfSections(2);

	}

	@Test
	public void testSize() {
		assertEquals(3, section.getStudents().size());
	}

	@Test
	public void isRoomTest() {
		assertTrue(section.hasRoom());
	}

	@Test
	public void addStudentTest() {
		assertTrue(section.hasRoom());
		while (section.hasRoom()) {
			section.addStudent(student);
		}
		assertNotNull(section);
		assertFalse(section.hasRoom());
		assertEquals(19, section.getStudents().size());
	}
	
	@Test
	public void isUniqueProfessorTest() {
		assertFalse(section.isUniqueProfessor(student));
		assertTrue(section.isUniqueProfessor(student2));
		assertTrue(section.isUniqueProfessor(student3));
	}
	
	@Test
	public void sssHasRoomTest(){
		model.setTotalSSS(1);
		assertFalse(section.support1HasRoom());
		model.setTotalSSS(2);
		assertTrue(section.support1HasRoom());
	}
	
	@Test
	public void sssCountTest(){
		assertEquals(1, section.getSupport1Count());
	}

	@Test
	public void aesHasRoomTest(){
		model.setTotalAES(1);
		assertTrue(section.aes1HasRoom());
		model.setTotalAES(2);
		assertTrue(section.aes1HasRoom());
	}
	
	@Test
	public void aesCountTest(){
		assertEquals(1, section.getAES1Count());
	}
	
	@Test
	public void continueHasRoomTest(){
		model.setTotalContinuing(1);
		assertTrue(section.continuing1HasRoom());
		model.setTotalContinuing(2);
		assertTrue(section.continuing1HasRoom());
	}
	
	@Test
	public void continuingCountTest(){
		assertEquals(1, section.getContinue1Count());
	}
	
	@Test
	public void transferHasRoomTest(){
		model.setTotalTransfer(1);
		assertTrue(section.transfer1HasRoom());
		model.setTotalTransfer(2);
		assertTrue(section.transfer1HasRoom());
	}	
	
	@Test
	public void transferCountTest(){
		assertEquals(1, section.getTransfer1Count());
	}
	
	@Test
	public void athleteHasRoomTest(){
		model.setTotalAthletes(1);
		assertTrue(section.athleteHasRoom());
		model.setTotalAthletes(2);
		assertTrue(section.athleteHasRoom());
	}
	
	@Test
	public void athleteCountTest(){
		assertEquals(1, section.getAthleteCount());
	}
	
	@Test
	public void maleCountTest(){
		assertEquals(1, section.getGenderCount("M"));
	}	
	
	@Test
	public void femaleCountTest(){
		assertEquals(2, section.getGenderCount("F"));
	}
	
	@Test
	public void teamCountTest(){
		assertEquals(1, section.getTeamCount(new Team("B")));
	}
	
	@Test
	public void teamHasRoomTest(){
		assertFalse(section.teamHasRoom(new Team("B")));
	}
	
}
