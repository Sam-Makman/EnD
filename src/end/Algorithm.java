package end;

import java.util.*;

/** An algorithm class for assigning students to sections. */
public class Algorithm {

	/** An ArrayList of students who did not indicate any preferred sections. */
	private ArrayList<Student> wildcards;

	/**
	 * An ArrayList of students of students who did not get in to any of their
	 * preferred sections.
	 */
	private ArrayList<Student> studentsNotPlaced;

	/** An ArrayList of students who are never placed */
	private ArrayList<Student> studentsNeverPlaced;

	/** An array of all of the sections. */
	private Section[] sections;

	/** A temporary array of all of the sections */
	private Section[] sectionTemp;

	/** An array of all of the students */
	private Student[] students;

	/**
	 * The constructor for the Algorithm class
	 * 
	 * @param All
	 *            of the sections
	 * @param All
	 *            of the students
	 */
	public Algorithm(Section[] sections, Student[] students) {
		wildcards = new ArrayList<Student>();
		studentsNotPlaced = new ArrayList<Student>();
		this.sections = sections;
		this.students = students;
		studentsNeverPlaced = new ArrayList<Student>();
		sectionTemp = new Section[sections.length];
		for (int i = 0; i < sections.length; i++) {
			sectionTemp[i] = sections[i].copyOf();
		}
	}

	/**
	 * A getter for studentsNeverPlaced
	 * 
	 * @returns The ArrayList of studentsNeverPlaced
	 */
	public ArrayList<Student> getStudentsNeverPlaced() {
		return studentsNeverPlaced;
	}

	/**
	 * Assigns students to the studentsNotPlaced ArrayList if they have
	 * preferences or the wildcards ArrayList if they didn't indicate any
	 * preferences.
	 */
	public void sortStudents() {
		for (Student s : students) {
			String[] preferences = s.getPreferences();
			if (preferences[0].equals("") || preferences[0] == null) {
				// Student has no preferences
				wildcards.add(s);
			} else {
				studentsNotPlaced.add(s);
			}
		}
	}

	/**
	 * A getter for the ArrayList of studentsNotPlaced
	 * 
	 * @return The ArrayList of studentsNotPlaced
	 */
	public ArrayList<Student> getStudentsNotPlaced() {
		return studentsNotPlaced;
	}

	/**
	 * A getter for the ArrayList of wildcards.
	 * 
	 * @return The ArrayList of wildcards.
	 */
	public ArrayList<Student> getWildcards() {
		return wildcards;
	}

	/**
	 * Calls other methods to place the Students in to a section
	 * 
	 */
	public void placementHelper() {
		sortStudents();
		for (int j = 0; j <= 6; j++) {
			for (Student s : studentsNotPlaced) {
				String[] tempPref = new String[s.getPreferences().length - j];
				for (int i = j; i < s.getPreferences().length; i++) {
					tempPref[i - j] = s.getPreferences()[i];
				}
				assignStudent(tempPref, s);
				// assert(s.getHasSection());
			}
			studentsNotPlaced.clear();
			optimizeSections();
			for (Section s : sectionTemp) {
				s.getStudents().clear();
			}
		}
		placeWildCard();
		// System.out.println(wildcards.size());

	}

	/**
	 * Assigns students into one of their preferred sections using the
	 * assignStudent() method. Then calls optimizeSecions() and placeWildCard()
	 */
	public void assignByPreference() {
		for (Student s : students) {
			String[] preferences = s.getPreferences();
			assignStudent(preferences, s);
		}
		studentsNotPlaced.clear();
		optimizeSections();
		placeWildCard();
	}

	/**
	 * Makes sure the Sections don't exceed the allowed amount of students per
	 * field and replaces the student if they do.
	 */
	public void optimizeSections() {
		for (int i = 0; i < sectionTemp.length; i++) {
			ArrayList<Student> studentTemp = new ArrayList<Student>();
			Student stu;
			Iterator<Student> it = sectionTemp[i].getStudents().iterator();
			while (it.hasNext()) {
				stu = it.next();
				if (stu.getHasSection()) {
					continue;
				}
				boolean add = false;
				if (!sections[i].hasRoom()) {
					studentsNotPlaced.add(stu);
					continue;
				}
				if (!add) {
					add = aes1Check(stu, sections[i])
							|| aes2Check(stu, sections[i])
							|| support1Check(stu, sections[i])
							|| support2Check(stu, sections[i])
							|| continuing1Check(stu, sections[i])
							|| continuing2Check(stu, sections[i])
							|| athleteCheck(stu, sections[i])
							|| transfer1Check(stu, sections[i])
							|| transfer2Check(stu, sections[i]);
				}
				if (add && validStudentPlacement(stu, i)) {
					sections[i].addStudent(stu);
				} else if (stu.getAES().equals("")
						&& stu.getSupport().equals("")
						&& stu.getContinuing().equals("") && !stu.isAthlete()
						&& stu.getTransfer().equals("")) {
					studentTemp.add(stu);
				} else {
					studentsNotPlaced.add(stu);
				}

			}
			addGenericStudent(studentTemp, i);

		}
		for (Section s : sectionTemp) {
			if (!s.hasRoom()) {
				s = null;
			}
		}
	}

	/**
	 * Places a student that has no fields (generic student) based on gender
	 * 
	 * @param studentTemp
	 *            An ArrayList of students which temporary holds students.
	 * @param i
	 *            the current section
	 */
	private void addGenericStudent(ArrayList<Student> studentTemp, int i) {
		for (Student stud : studentTemp) {
			if (stud.getHasSection()) {
				continue;
			}
			if (stud.getGender().equals("M")
					&& !sections[i].containsStudent(stud)
					&& sections[i].maleHasRoom() && sections[i].hasRoom()) {
				sections[i].addStudent(stud);
			} else if (stud.getGender().equals("F")
					&& !sections[i].containsStudent(stud)
					&& sections[i].femaleHasRoom() && sections[i].hasRoom()) {
				sections[i].addStudent(stud);
				stud.assignSection(sections[i]);
			} else {
				wildcards.add(stud);
			}
		}
	}

	/**
	 * Assigns student to first preference, ignores cap on sections. If the
	 * student can't be placed then they are placed in wildcards.
	 * 
	 * @param preferences
	 *            The Student's preferences
	 * @param student
	 *            The current student being placed
	 */
	public void assignStudent(String[] preferences, Student student) {
		for (String preference : preferences) {
			for (int i = 0; i < sections.length; i++) {
				if (preference.equals(sectionTemp[i].getUniqueID())
						&& sectionTemp[i].isUniqueProfessor(student)
						&& !sections[i].containsStudent(student)) {
					sectionTemp[i].addStudent(student);
					return;
				}
			}
		}
		wildcards.add(student);
	}

	/** Calls the placeOtherStudent() method on all wild card students */
	public void placeWildCard() {
		for (Student student : wildcards) {
			placeOtherStudent(student);
		}
	}

	/**
	 * Places the student in a random section that has room and that is a valid
	 * student placement.
	 * 
	 * @param student
	 *            A wildcard student.
	 */
	public void placeOtherStudent(Student student) {
		int i;
		ArrayList<Integer> rand = new ArrayList<Integer>();
		for (int j = 0; j < sections.length; j++) {
			rand.add(j);
		}

		Collections.shuffle(rand);

		for (Object obj : rand) {
			i = (int) obj;
			if (sections[i].hasRoom() && validStudentPlacement(student, i)) {
				sections[i].addStudent(student);
				student.assignSection(sections[i]);
				return;
			}
		}
		System.out.println("Student " + student.getID() + " not placed");
		studentsNeverPlaced.add(student);
	}

	/**
	 * Checks if the student is valid in that section given the index.
	 * 
	 * @param student
	 *            The student being checked.
	 * @param i
	 *            The index of the current section the student is being checked
	 *            on.
	 * @return a boolean
	 */
	private boolean validStudentPlacement(Student student, int i) {
		for (String prof : student.getPrevProf()) {
			if (sections[i].getProfessor().equals(prof)) {
				return false;
			}
		}

		if (student.getAES().equals("1")) {
			if (!sections[i].aes1HasRoom()) {
				return false;
			}
		}

		if (student.getAES().equals("2")) {
			if (!sections[i].aes2HasRoom()) {
				return false;
			}
		}
		if (student.getContinuing().equals("1")) {
			if (!sections[i].continuing1HasRoom()) {
				return false;
			}
		}
		if (student.getContinuing().equals("2")) {
			if (!sections[i].continuing2HasRoom()) {
				return false;
			}
		}
		if (student.getSupport().equals("1")) {
			if (!sections[i].support1HasRoom()) {
				return false;
			}
		}
		if (student.getSupport().equals("2")) {
			if (!sections[i].support2HasRoom()) {
				return false;
			}
		}
		if (student.getTransfer().equals("1")) {
			if (!sections[i].transfer1HasRoom()) {
				return false;
			}
		}
		if (student.getTransfer().equals("2")) {
			if (!sections[i].transfer2HasRoom()) {
				return false;
			}
		}
		if (student.isAthlete()) {
			if (!sections[i].athleteHasRoom()) {
				return false;
			} else {
				for (Team t : student.getTeams()) {
					if (!sections[i].teamHasRoom(t)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Checks if the student is valid in that section given the section.
	 * 
	 * @param student
	 *            The student being checked.
	 * @param s
	 *            The Section the student is being checked on.
	 * @return a boolean
	 */
	private boolean validStudentPlacement(Student student, Section s) {
		for (String prof : student.getPrevProf()) {
			if (s.getProfessor().equals(prof)) {
				return false;
			}
		}

		if (student.getAES().equals("1")) {
			if (!s.aes1HasRoom()) {
				return false;
			}
		}

		if (student.getAES().equals("2")) {
			if (!s.aes2HasRoom()) {
				return false;
			}
		}
		if (student.getContinuing().equals("1")) {
			if (!s.continuing1HasRoom()) {
				return false;
			}
		}
		if (student.getContinuing().equals("2")) {
			if (!s.continuing2HasRoom()) {
				return false;
			}
		}
		if (student.getSupport().equals("1")) {
			if (!s.support1HasRoom()) {
				return false;
			}
		}
		if (student.getSupport().equals("2")) {
			if (!s.support2HasRoom()) {
				return false;
			}
		}
		if (student.getTransfer().equals("1")) {
			if (!s.transfer1HasRoom()) {
				return false;
			}
		}
		if (student.getTransfer().equals("2")) {
			if (!s.transfer2HasRoom()) {
				return false;
			}
		}
		if (student.isAthlete()) {
			if (!s.athleteHasRoom()) {
				return false;
			} else {
				for (Team t : student.getTeams()) {
					if (!s.teamHasRoom(t)) {
						return false;
					}
				}
			}
		}
		
		if (student.getGender().equals("M") && !s.maleHasRoom()) {
			return false;
		}
		if (student.getGender().equals("F") && !s.femaleHasRoom()) {
			return false;
		}
		return true;
	}

	/**
	 * A getter for the sectionTemp
	 * 
	 * @return The ArrayList sectionTemp
	 */
	public Section[] getSectionTemp() {
		return sectionTemp;
	}

	/**
	 * A checker on the student if they have the AES 1 parameter and if the
	 * Section has room.
	 * 
	 * @param s
	 *            The student being checked
	 * @param section
	 *            The Section which is being checked on.
	 * @return A boolean
	 */
	public boolean aes1Check(Student s, Section section) {
		return s.getAES().equals("1") && section.aes1HasRoom();
	}

	/**
	 * A checker on the student if they have the AES 2 parameter and if the
	 * Section has room.
	 * 
	 * @param s
	 *            The student being checked
	 * @param section
	 *            The Section which is being checked on.
	 * @return A boolean
	 */
	public boolean aes2Check(Student s, Section section) {
		return s.getAES().equals("2") && section.aes2HasRoom();
	}

	/**
	 * A checker on the student if they have the SSS 1 parameter and if the
	 * Section has room.
	 * 
	 * @param s
	 *            The student being checked
	 * @param section
	 *            The Section which is being checked on.
	 * @return A boolean
	 */
	public boolean support1Check(Student s, Section section) {
		return s.getSupport().equals("1") && section.support1HasRoom();
	}

	/**
	 * A checker on the student if they have the SSS 2 parameter and if the
	 * Section has room.
	 * 
	 * @param s
	 *            The student being checked
	 * @param section
	 *            The Section which is being checked on.
	 * @return A boolean
	 */
	public boolean support2Check(Student s, Section section) {
		return s.getSupport().equals("2") && section.support2HasRoom();

	}

	/**
	 * A checker on the student if they have the continuing 1 parameter and if
	 * the Section has room.
	 * 
	 * @param s
	 *            The student being checked
	 * @param section
	 *            The Section which is being checked on.
	 * @return A boolean
	 */
	public boolean continuing1Check(Student s, Section section) {
		return s.getContinuing().equals("1") && section.continuing1HasRoom();
	}

	/**
	 * A checker on the student if they have the Continuing 2 parameter and if
	 * the Section has room.
	 * 
	 * @param s
	 *            The student being checked
	 * @param section
	 *            The Section which is being checked on.
	 * @return A boolean
	 */
	public boolean continuing2Check(Student s, Section section) {
		return s.getContinuing().equals("2") && section.continuing2HasRoom();
	}

	/**
	 * A checker on the student if they have the Athlete parameter and if the
	 * Section has room.
	 * 
	 * @param s
	 *            The student being checked
	 * @param section
	 *            The Section which is being checked on.
	 * @return A boolean
	 */
	public boolean athleteCheck(Student s, Section section) {
		return s.isAthlete() && section.athleteHasRoom();
	}

	/**
	 * A checker on the student if they have the Transfer 1 parameter and if the
	 * Section has room.
	 * 
	 * @param s
	 *            The student being checked
	 * @param section
	 *            The Section which is being checked on.
	 * @return A boolean
	 */
	public boolean transfer1Check(Student s, Section section) {
		return s.getTransfer().equals("1") && section.transfer1HasRoom();
	}

	/**
	 * A checker on the student if they have the Transfer 2 parameter and if the
	 * Section has room.
	 * 
	 * @param s
	 *            The student being checked
	 * @param section
	 *            The Section which is being checked on.
	 * @return A boolean
	 */
	public boolean transfer2Check(Student s, Section section) {
		return s.getTransfer().equals("2") && section.transfer2HasRoom();
	}

	/**
	 * A checker if two students can be swapped.
	 * 
	 * @param a
	 *            Student that may be potentially swapped with student b.
	 * @param aS
	 *            Student a's Section.
	 * @param b
	 *            Student that may be potentially swapped with student a.
	 * @param bS
	 *            Student b's Section.
	 * @return A boolean value if the two students can be swapped
	 */
	public boolean swapValid(Student a, Section aS, Student b, Section bS) {
		return validStudentPlacement(a, bS) && validStudentPlacement(b, aS);
	}
}
