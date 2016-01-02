package end;

import java.util.ArrayList;

public class Section {

	/** The maximum number of students allowed per section */
	private int maxStudents;

	/** The model, which is used for getting totals of students */
	private Model model;

	/** The name of the professor */
	private String professor;
	/** The list of students in the class */
	private ArrayList<Student> students;

	/** The code for the ID of the section */
	private String uniqueID;

	/** Constructor for section */
	public Section(int maxStudents, String uniqueID, String professor, Model model) {
		students = new ArrayList<Student>();
		this.maxStudents = maxStudents;
		this.uniqueID = uniqueID;
		this.professor = professor;
		this.model = model;
	}

	/** Adds a student to a section */
	public void addStudent(Student s) {
		students.add(s);
	}

	/** Returns true if the section has room for more AES 1 students */
	public boolean aes1HasRoom() {
		return getAES1Count() < (model.getTotalAES1() / model.getTotalSections()) + 2;
	}

	/** Returns true if the section has room for more AES 2 students */
	public boolean aes2HasRoom() {
		return getAES2Count() < (model.getTotalAES2() / model.getTotalSections()) + 2;
	}

	/** Returns true if the section has room for more athletes students */
	public boolean athleteHasRoom() {
		return getAthleteCount() < (model.getTotalAthletes() / model.getTotalSections()) + 2;
	}

	/**
	 * Returns true if this section contains a given student
	 * 
	 * @param student
	 *            The given student
	 */
	public boolean containsStudent(Student student) {
		for (Student s : students) {
			if (s == student) {
				return true;
			}
		}
		return false;
	}

	/** Returns true if the section has room for more continuing 1 students */
	public boolean continuing1HasRoom() {
		return getContinue1Count() < (model.getTotalContinuing1() / model.getTotalSections()) + 2;
	}

	/** Returns true if the section has room for more continuing 2 students */
	public boolean continuing2HasRoom() {
		return getContinue2Count() < (model.getTotalContinuing2() / model.getTotalSections()) + 2;
	}

	/** Returns a copy of the section */
	public Section copyOf() {
		return new Section(maxStudents, uniqueID, professor, model);
	}

	/**
	 * 
	 * @return number of spots left in section
	 */
	public int getAvailableSeats() {
		return maxStudents - students.size();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Section other = (Section) obj;
		if (maxStudents != other.maxStudents)
			return false;
		if (professor == null) {
			if (other.professor != null)
				return false;
		} else if (!professor.equals(other.professor))
			return false;
		if (uniqueID == null) {
			if (other.uniqueID != null)
				return false;
		} else if (!uniqueID.equals(other.uniqueID))
			return false;
		return true;
	}

	/** Returns true if the section has room for more female students */
	public boolean femaleHasRoom() {
		return getGenderCount("F") < (model.getTotalFemale() / model.getTotalSections()) + 2;
	}

	/** Returns the number of AES students in this section */
	public int getAES1Count() {
		int AES = 0;
		for (Student s : students) {
			if (s.getAES().equals("1")) {
				AES++;
			}
		}
		return AES;
	}

	/** Returns the number of AES students in this section */
	public int getAES2Count() {
		int AES = 0;
		for (Student s : students) {
			if (s.getAES().equals("2")) {
				AES++;
			}
		}
		return AES;
	}

	/** Returns the number of athletes in this section */
	public int getAthleteCount() {
		int athlete = 0;
		for (Student s : students) {
			if (s.isAthlete()) {
				athlete++;
			}
		}
		return athlete;
	}

	/** Returns the number of continuing students in this section */
	public int getContinue1Count() {
		int cont = 0;
		for (Student s : students) {
			if (s.getContinuing().equals("1")) {
				cont++;
			}
		}
		return cont;
	}

	/** Returns the number of continuing students in this section */
	public int getContinue2Count() {
		int cont = 0;
		for (Student s : students) {
			if (s.getContinuing().equals("2")) {
				cont++;
			}
		}
		return cont;
	}

	/**
	 * Returns the number of students in a the section of a given gender
	 * 
	 * @param gender
	 *            The given gender
	 */
	public int getGenderCount(String gender) {
		int genderCount = 0;
		for (Student stu : students) {
			if (stu.getGender().equalsIgnoreCase(gender)) {
				genderCount++;
			}
		}
		return genderCount;
	}

	/** Returns the model */
	public Model getModel() {
		return model;
	}

	/** Return the professor for this section */
	public String getProfessor() {
		return professor;
	}

	/**
	 * 
	 * @return returns array list of students
	 */
	public ArrayList<Student> getStudents() {
		return students;
	}

	/** Returns the number of SSS students in this section */
	public int getSupport1Count() {
		int support = 0;
		for (Student s : students) {
			if (s.getSupport().equals("1")) {
				support++;
			}
		}
		return support;
	}

	/** Returns the number of SSS students in this section */
	public int getSupport2Count() {
		int support = 0;
		for (Student s : students) {
			if (s.getSupport().equals("2")) {
				support++;
			}
		}
		return support;
	}

	/**
	 * Returns the number of players a given team
	 * 
	 * @param t
	 *            The given team
	 */
	public int getTeamCount(Team t) {
		int player = 0;
		for (Student s : students) {
			if (s.getTeams().contains(t)) {
				player++;
			}
		}
		return player;
	}

	/** Returns the number of transfer students in this section */
	public int getTransfer1Count() {
		int transfer = 0;
		for (Student s : students) {
			if (s.getTransfer().equals("1")) {
				transfer++;
			}
		}
		return transfer;
	}

	/** Returns the number of transfer students in this section */
	public int getTransfer2Count() {
		int transfer = 0;
		for (Student s : students) {
			if (s.getTransfer().equals("2")) {
				transfer++;
			}
		}
		return transfer;
	}

	/** Return the unique ID of the section */
	public String getUniqueID() {
		return uniqueID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + maxStudents;
		result = prime * result + ((professor == null) ? 0 : professor.hashCode());
		result = prime * result + ((uniqueID == null) ? 0 : uniqueID.hashCode());
		return result;
	}

	/**
	 * 
	 * @return returns true if there is room in section
	 */
	public boolean hasRoom() {
		return students.size() < maxStudents;
	}

	/**
	 * @param student
	 *            student to check
	 * @return true if student has not taken a class with the professor
	 */
	public boolean isUniqueProfessor(Student student) {
		if (student.getPrevProf() == null) {
			return true;
		}
		return !student.getPrevProf().contains(professor);
	}

	/** Returns true if the section has room for more male students */
	public boolean maleHasRoom() {
		return getGenderCount("M") < (model.getTotalMale() / model.getTotalSections()) + 2;
	}

	/**
	 * Removes a given student from the section
	 * 
	 * @param s
	 *            The given student
	 */
	public void removeStudent(Student s) {
		students.remove(s);
		s.assignSection(null);
	}

	/** Returns true if the section has room for more SSS 1 students */
	public boolean support1HasRoom() {
		return getSupport1Count() < (model.getTotalSupport1() / model.getTotalSections()) + 1;
	}

	/** Returns true if the section has room for more SSS 2 students */
	public boolean support2HasRoom() {
		return getSupport2Count() < (model.getTotalSupport2() / model.getTotalSections()) + 2;
	}

	/**
	 * Returns true if a given team has room
	 * 
	 * @param The
	 *            given team
	 */
	public boolean teamHasRoom(Team t) {
		return getTeamCount(
				t) <= (model.getAllTeams().get(model.getAllTeams().indexOf(t)).getSize() / model.getTotalSections());
	}

	/** Returns true if the section has room for more transfer 1 students */
	public boolean transfer1HasRoom() {
		return getTransfer1Count() < (model.getTotalTransfer1() / model.getTotalSections()) + 2;
	}

	/** Returns true if the section has room for more transfer 2 students */
	public boolean transfer2HasRoom() {
		return getTransfer2Count() < (model.getTotalTransfer2() / model.getTotalSections()) + 2;
	}

}
