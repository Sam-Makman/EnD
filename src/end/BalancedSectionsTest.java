package end;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class BalancedSectionsTest {

	private Section[] sections;
	private Model model;

	@Before
	public void setUp() {
		model = new Model();
		model.runProgram(new File("ProfessorInfo.csv"), new File(
				"StudentInfo.csv"), 19);
		sections = model.getAllSections();
	}

	@Test
	public void SSSTest() {
		for (int i = 0; i < sections.length; i++) {
			assertTrue(sections[i].getSupport1Count() <= (model
					.getTotalSupport1()) / (model.getTotalSections()) + 2);
			assertTrue(sections[i].getSupport2Count() <= (model
					.getTotalSupport2()) / (model.getTotalSections()) + 2);

		}

	}

	@Test
	public void AESTest() {
		for (int i = 0; i < sections.length; i++) {
			assertTrue(sections[i].getAES1Count() <= (model.getTotalAES1())
					/ (model.getTotalSections()) + 2);
			assertTrue(sections[i].getAES2Count() <= (model.getTotalAES2())
					/ (model.getTotalSections()) + 2);

		}

	}

	@Test
	public void athleteTest() {
		for (int i = 0; i < sections.length; i++) {
			assertTrue(sections[i].getAthleteCount() <= (model
					.getTotalAthletes()) / (model.getTotalSections()) + 2);

		}

	}

	@Test
	public void continuingTest() {

		for (int i = 0; i < sections.length; i++) {

			assertTrue(sections[i].getContinue1Count() <= (model
					.getTotalContinuing1()) / (model.getTotalSections()) + 2);

		}

	}

	@Test
	public void transferTest() {
		for (int i = 0; i < sections.length; i++) {
			assertTrue(sections[i].getTransfer1Count() <= (model
					.getTotalTransfer1()) / (model.getTotalSections()) + 2);

		}

	}

}
