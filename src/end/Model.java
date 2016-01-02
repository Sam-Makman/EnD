package end;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Logical model of the scheduler. */
public class Model {

	private Algorithm algorithm;
	
	private Section[] allSections;

	private Student[] allStudents;

	private ArrayList<Team> allTeams;

	private int totalAES1;

	private int totalAES2;

	private int totalSupport1;

	private int totalSupport2;

	private int totalTransfer1;

	private int totalTransfer2;

	private int totalContinuing1;

	private int totalContinuing2;

	private int totalAthletes;

	private int totalFemale;

	private int totalMale;

	private int totalSections;

	private int totalStudents;

	private double score;

	/** Constructor for Model class. Sets all student attribute counts to 0. */
	public Model() {
		totalAES1 = 0;
		totalAES2 = 0;
		totalSupport1 = 0;
		totalSupport2 = 0;
		totalTransfer1 = 0;
		totalTransfer2 = 0;
		totalContinuing1 = 0;
		totalContinuing2 = 0;
		totalSections = 0;
		totalStudents = 0;
		allSections = null;
		allStudents = null;
		allTeams = new ArrayList<Team>();
	}

	/**
	 * Another constructor for model used for testing purposes
	 * 
	 * @param b
	 */

	public Model(boolean b) {
		totalAES1 = 1;
		totalAES2 = 1;
		totalSupport1 = 1;
		totalSupport2 = 1;
		totalTransfer1 = 1;
		totalTransfer2 = 1;
		totalContinuing1 = 1;
		totalContinuing2 = 1;
		allTeams = new ArrayList<Team>();
	}

	/**
	 * 
	 * @return all the sections present for E&D.
	 */
	public Section[] getAllSections() {
		return allSections;
	}

	/**
	 * 
	 * @return all the students present to be sorted into sections.
	 */
	public Student[] getAllStudents() {
		return allStudents;
	}

	/**
	 * 
	 * @return the array list with all the athletic teams.
	 */
	public ArrayList<Team> getAllTeams() {
		return allTeams;
	}

	/**
	 * 
	 * @return the count of athletic students amongst all the students.
	 */
	public int getTotalAthletes() {
		return totalAthletes;
	}

	/**
	 * 
	 * @return the count of female students amongst all the students.
	 */
	public int getTotalFemale() {
		return totalFemale;
	}

	/**
	 * 
	 * @return the count of male students amongst all the students.
	 */
	public int getTotalMale() {
		return totalMale;
	}

	/**
	 * 
	 * @return the total number of E&D sections present.
	 */
	public int getTotalSections() {
		return totalSections;
	}

	/**
	 * Receives files and information from the GUI and creates student and
	 * section data base
	 * 
	 * @param section
	 *            The professor info file
	 * @param student
	 *            The student info file
	 * @param capStudents
	 *            The max number of students per section
	 */
	public void runProgram(File section, File student, int capStudents) {
		String[][] studentStrings = DataFile.readStudentFile(student);
		String[][] sectionStrings = DataFile.readProfessorFile(section);
		allSections = new Section[sectionStrings.length];
		totalSections = sectionStrings.length;
		allStudents = new Student[studentStrings.length];
		allTeams = new ArrayList<Team>();

		// Make all the sections
		for (int i = 0; i < sectionStrings.length; i++) {
			allSections[i] = new Section(capStudents, sectionStrings[i][0],
					sectionStrings[i][1], this);
		}

		// Make decelerations early for efficiency
		int counter = 0;
		String prevProfs;
		String teams;
		String delims = "[ ]+";
		String[] prevProfsArray;
		String support;
		String AES;
		String continuing;
		String transfer;

		// Iterate through each student
		for (int i = 0; i < studentStrings.length; i++) {
			String[] teamsArray = new String[3];
			String[] preferences = new String[6];
			ArrayList<String> teamsList = new ArrayList<String>();
			ArrayList<String> prevProfsList = new ArrayList<String>();
			counter = 0;

			// Get the preferences of each student
			for (int j = 9; j < studentStrings[i].length; j++) {
				preferences[counter] = studentStrings[i][j];
				counter++;
			}

			// Get the past professors
			prevProfs = studentStrings[i][8];
			prevProfsArray = prevProfs.split(delims);
			for (int j = 0; j < prevProfsArray.length; j++) {
				prevProfsList.add(prevProfsArray[j]);
			}

			// Get the teams of a student
			teams = studentStrings[i][3];
			teamsArray = teams.split(delims);
			for (int j = 0; j < teamsArray.length; j++) {
				if (teamsArray[j] != null && !teamsArray[j].equals("")) {
					Team temp = new Team(teamsArray[j]);
					if (!allTeams.contains(temp)) {
						allTeams.add(temp);
					}
					for (Team t : allTeams) {
						if (t.equals(temp)) {
							t.addMember();
						}
					}
					teamsList.add(teamsArray[j]);
				} else {
					break;
				}
			}

			// Get the total number of athletes
			if (!teamsList.isEmpty()) {
				totalAthletes++;
			}

			// Get the total number of SSS
			support = studentStrings[i][4];

			if (support.equals("1")) {
				totalSupport1++;
			}
			if (support.equals("2")) {
				totalSupport2++;
			}

			// Get the total number of AES
			AES = studentStrings[i][5];
			if (AES.equals("1")) {
				totalAES1++;
			}
			if (AES.equals("2")) {
				totalAES2++;
			}

			// Get the total number of transfer students
			transfer = studentStrings[i][6];

			if (transfer.equals("1")) {
				totalTransfer1++;
			}
			if (transfer.equals("2")) {
				totalTransfer2++;

			}

			// Get the total number of continuing students
			continuing = studentStrings[i][7];

			if (continuing.equals("1")) {
				totalContinuing1++;
			}
			if (continuing.equals("2")) {
				totalContinuing2++;

			}

			// Get the total number of males
			if (studentStrings[i][1].equalsIgnoreCase("M")) {
				totalMale++;
			}

			// Get the total number of females
			if (studentStrings[i][1].equalsIgnoreCase("F")) {
				totalFemale++;
			}

			// Create the student
			allStudents[i] = new Student(prevProfsList, preferences,
					studentStrings[i][0], studentStrings[i][1], teamsList,
					support, AES, transfer, continuing);
		}
		totalStudents = allStudents.length;
		totalSections = allSections.length;

		// Make a copy of the list and shuffle
		List<Student> list = Arrays.asList(Arrays.copyOf(allStudents,
				allStudents.length));
		Collections.shuffle(list);

		Student[] shuffledStudents = (Student[]) list.toArray();

		// Use the algorithm to place students and then write to a CSV file
		algorithm = new Algorithm(allSections, shuffledStudents);

		algorithm.placementHelper();

		// swap 1000000 students per iteration.
		for (int i = 0; i < 1000000; i++) {
			swap();
		}

		score = scoreSections(this);
		// System.out.println(score);
		// DataFile.writeCSVFile(allSections, "");

	}

	/**
	 * 
	 * @return the score of the iteration
	 */
	public double getScore() {
		return score;
	}

	/**
	 * 
	 * @param model
	 * @return the average score of the iteration.
	 */
	public static double scoreSections(Model model) {
		Section sections[] = model.getAllSections();
		double total = 0;
		for (Section s : sections) {
			double count = 0;
			double scount = 0;
			for (Student stu : s.getStudents()) {
				scount = scount + 1;
				if (stu.getPreferences()[0].equalsIgnoreCase("")
						|| stu.getPreferences()[0] == null) {
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

	/**
	 * 
	 * @return the instance of the algorithm
	 */
	public Algorithm getAlgorithm() {
		return algorithm;
	}

	/** This setter is for testing purposes only */
	public void setAllTeams(ArrayList<Team> allTeams) {
		this.allTeams = allTeams;
	}

	/** This setter is for testing purposes only */
	public void setNumberOfSections(int numberOfSections) {
		this.totalSections = numberOfSections;
	}

	/** This setter is for testing purposes only */
	public void setTotalAES(int totalAES) {
		this.totalAES1 = totalAES;
	}

	/** This setter is for testing purposes only */
	public void setTotalSSS(int totalSSS) {
		this.totalSupport1 = totalSSS;
	}

	/** This setter is for testing purposes only */
	public void setTotalTransfer(int totalTransfer) {
		this.totalTransfer1 = totalTransfer;
	}

	/** This setter is for testing purposes only */
	public void setTotalContinuing(int totalContinuing) {
		this.totalContinuing1 = totalContinuing;

	}

	/** This setter is for testing purposes only */
	public void setTotalAthletes(int totalAthletes) {
		this.totalAthletes = totalAthletes;
	}

	/** This setter is for testing purposes only */
	public void setTotalFemale(int totalFemale) {
		this.totalFemale = totalFemale;
	}

	/** This setter is for testing purposes only */
	public void setTotalMale(int totalMale) {
		this.totalMale = totalMale;
	}

	/** This setter is for testing purposes only */
	public void setTotalSections(int totalSections) {
		this.totalSections = totalSections;
	}

	/** This setter is for testing purposes only */
	public void setTotalStudents(int totalStudents) {
		this.totalStudents = totalStudents;
	}

	/** This is for testing purposes only */
	public void addAllTeams(ArrayList<String> teams) {
		for (String name : teams) {
			allTeams.add(new Team(name));
		}
	}

	/**
	 * 
	 * @return total students that need to be placed
	 */
	public int getTotalStudents() {
		return totalStudents;
	}

	/**
	 * 
	 * @return total number of AES1 students amongst all students.
	 */
	public int getTotalAES1() {
		return totalAES1;
	}

	/**
	 * 
	 * @return total number of AES2 students amongst all students.
	 */
	public int getTotalAES2() {
		return totalAES2;
	}

	/**
	 * 
	 * @return total number of support services 1 students amongst all students.
	 */
	public int getTotalSupport1() {
		return totalSupport1;
	}

	/**
	 * 
	 * @return total number of support services 2 students amongst all students.
	 */
	public int getTotalSupport2() {
		return totalSupport2;
	}

	/**
	 * 
	 * @return total number of transfer 1 students amongst all students.
	 */
	public int getTotalTransfer1() {
		return totalTransfer1;
	}

	/**
	 * 
	 * @return total number of transfer 2 students amongst all students.
	 */
	public int getTotalTransfer2() {
		return totalTransfer2;
	}

	/**
	 * 
	 * @return total number of continuing 1 students amongst all students.
	 */
	public int getTotalContinuing1() {
		return totalContinuing1;
	}

	/**
	 * 
	 * @return total number of continuing 2 students amongst all students.
	 */
	public int getTotalContinuing2() {
		return totalContinuing2;
	}

	Student a;
	Student b;

	/**
	 * Takes 2 students and swaps them keeping all the attribute counters in
	 * check.
	 */
	public void swap() {
		a = allStudents[(int) (Math.random() * totalStudents)];
		b = allStudents[(int) (Math.random() * totalStudents)];
		Section aS = a.getSection(), bS = b.getSection();
		if (aS != null && bS != null) {
			for (String prefA : a.getPreferences()) {
				if (prefA.equals(bS.getUniqueID())) {
					for (String prefB : b.getPreferences()) {
						if (prefB.equals(aS.getUniqueID())) {
							if (algorithm.swapValid(a, aS, b, bS)) {
								a.swapSection(bS);
								b.swapSection(aS);
							}
						}
					}
				}
			}
		}
	}
}
