package end;

public class Team {

	/** The name of the team */
	private String name;

	/** The size of the team */
	private int size;

	/**
	 * Constructor for team class
	 * 
	 * @param name
	 *            The name of the team
	 */
	public Team(String name) {
		this.name = name;
		size = 0;
	}

	/** Adds a member to the team */
	public void addMember() {
		size++;
	}

	/** Returns the size of the team */
	public int getSize() {
		return size;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/** Returns the name of the team */
	public String getName() {
		return name;
	}

	/** How to display a team as a string */
	public String toString() {
		return name;
	}
}
