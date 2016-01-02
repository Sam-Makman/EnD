package end;

import java.util.ArrayList;

public class Student {

	/** An indicator if the student is in Academic English Studies */
	private String AES;

	/** An indicator if the student is a continuing student */
	private String continuing;

	/** The student's gender */
	private String gender;

	/** An indicator if the student has already been placed in a section */
	private boolean hasSection;

	/** The student's ID number */
	private String ID;

	/** An indicator if the student is an athlete or not */
	private boolean isAthlete;

	/** A list of the preferences of the student */
	private String[] preferences = new String[6];

	/** A list of professors that the students used to have */
	private ArrayList<String> prevProf;

	/** The student's section */
	private Section section;

	/** An indicator if the student has accommodations */
	private String support;

	/** The list of the teams that the student is on */
	private ArrayList<Team> team;

	/** An indicator if the student is a transfer student */
	private String transfer;

	/**
	 * The constructor for student
	 * 
	 * @param prevProf
	 *            A list of professors that the students used to have
	 * @param preferences
	 *            A list of the preferences of the student
	 * @param ID
	 *            The student's ID number
	 * @param gender
	 *            The student's gender
	 * @param team
	 *            The list of the teams that the student is on
	 * @param support
	 *            An indicator if the student has accommodations
	 * @param AES
	 *            An indicator if the student is in Academic English Studies
	 * @param transfer
	 *            An indicator if the student is a transfer student
	 * @param continuing
	 *            indicator if the student is a continuing student
	 */
	public Student(ArrayList<String> prevProf, String[] preferences, String ID, String gender, ArrayList<String> team,
			String support, String AES, String transfer, String continuing) {
		this.prevProf = prevProf;
		this.preferences = preferences;
		this.ID = ID;
		this.gender = gender;
		this.team = new ArrayList<Team>();
		if (team == null || team.contains("") || team.isEmpty()) {
			this.isAthlete = false;
		} else {
			this.isAthlete = true;
			for (String name : team) {
				this.team.add(new Team(name));
			}
		}
		this.AES = AES;
		this.support = support;
		this.continuing = continuing;
		this.transfer = transfer;
		hasSection = false;
	}

	/**
	 * Assigns the student to given section
	 * 
	 * @param section
	 *            the given section
	 */
	public void assignSection(Section section) {
		hasSection = true;
		this.section = section;
	}

	/**
	 * 
	 * @return returns their AES level
	 */
	public String getAES() {
		return AES;
	}

	/**
	 * 
	 * @return level of continuing student
	 */
	public String getContinuing() {
		return continuing;
	}

	/** Get the gender of the student */
	public String getGender() {
		return gender;
	}

	/** Returns true if the student has been assigned a section */
	public boolean getHasSection() {
		return hasSection;
	}

	/** Get the student ID */
	public String getID() {
		return ID;
	}

	/** Get 6 the choices */
	public String[] getPreferences() {
		return preferences;
	}

	/** Get previous professors array list */
	public ArrayList<String> getPrevProf() {
		return prevProf;
	}

	/** Returns the section that a student belongs to */
	public Section getSection() {
		return section;
	}

	/**
	 * 
	 * @return the level of support they need
	 */
	public String getSupport() {
		return support;
	}

	/** Get the teams on which the student is in */
	public ArrayList<Team> getTeams() {
		return team;
	}

	/**
	 * 
	 * @return level of transfer student
	 */
	public String getTransfer() {
		return transfer;
	}

	/** Get if the student is athlete or not */
	public boolean isAthlete() {
		return isAthlete;
	}

	/**
	 * Puts the student into a different given section and returns the old
	 * section
	 * 
	 * @param s
	 *            The given section
	 * @return The section that the student used to belong to
	 */
	public Section swapSection(Section s) {
		Section old = section;
		old.removeStudent(this);
		s.addStudent(this);
		section = s;
		return old;
	}

}
