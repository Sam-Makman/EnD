package end;

import java.io.File;
import java.util.Random;

public class AutoRun {

	public static void main(String[] args) {
		Model model = new Model();
		File studentFile = new File("StudentInfo.csv");
		File professorFile = new File("ProfessorInfo.csv");
		model.runProgram(professorFile, studentFile, 19);
		shuffleArray(model.getAllStudents());
		System.out.println(scoreSections(model));
		for (int i = 0; i < 1000000; i++) {
			model.swap();
			System.out.println(scoreSections(model));
		}
		DataFile.writeCSVFile(model.getAllSections(), "");
		
	}

	public static double scoreSections(Model model) {
		Section sections[] = model.getAllSections();
		double total = 0;
		for (Section s : sections) {
			double count = 0;
			double scount = 0;
			for (Student stu : s.getStudents()) {
				scount = scount + 1;
				if (stu.getPreferences()[0].equalsIgnoreCase("") || stu.getPreferences()[0] == null) {
					count = count + 1;
				} else {
					for (String pref : stu.getPreferences()) {
						if (pref.equalsIgnoreCase(s.getUniqueID())) {
							count = count + 1;
							break;
						}
					}
				}

			}
			// System.out.println("Sections " + s.getUniqueID() + " Score is " +
			// count/scount);
			total = total + (count / scount);
		}
		// System.out.println("Average score is " + total/33);
		return total / sections.length;
	}

	public static void shuffleArray(Student[] s) {
		Random r = new Random();
		Student temp;
		for (int i = 0; i < 2000; i++) {
			int start = r.nextInt(s.length - 1);
			int end = r.nextInt(s.length - 1);
			temp = s[start];
			s[start] = s[end];
			s[end] = temp;
		}
	}

}
