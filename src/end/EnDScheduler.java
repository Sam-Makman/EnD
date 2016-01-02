package end;

public class EnDScheduler {
	
	/**
	 * Our main method!!!!!!
	 * @param args
	 */

	public static void main(String[] args) {
		new EnDScheduler();
	}
	
	/**
	 * Calls the GUI to begin the program
	 */
	public EnDScheduler() {
		GUI display = new GUI();
		try{
			
		} catch(Exception e) {
			display.errorMessage();
			e.printStackTrace();
		}
	}
	
}
