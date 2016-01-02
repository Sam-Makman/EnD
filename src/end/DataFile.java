package end;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataFile {

	/**
	 * Reads in the CSV containing professors and their sections and puts them into an string array
	 * 
	 * @return
	 */
	public static String[][] readProfessorFile(File f) {
		ArrayList<String> professor = new ArrayList<String>();
		ArrayList<String> section = new ArrayList<String>();
		String line;
		String ls[];

		try {
			Scanner scan = new Scanner(f);
			scan.nextLine();
			while (scan.hasNext()) {

				line = scan.nextLine();
				ls = line.split(",");
				professor.add(ls[0]);
				section.add(ls[1]);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String[][] professorAndSection = new String[professor.size()][2];
		for(int i = 0; i < professor.size(); i++){
			professorAndSection[i][0] = professor.get(i);
			professorAndSection[i][1] = section.get(i);
		}
		return professorAndSection;
	}
	
	/**
	 * Reads the student csv file and creates a string array of the data
	 * @param f
	 * @return
	 */

	public static String[][] readStudentFile(File f) {
		ArrayList<String> studentTable = new ArrayList<String>();
		try {
			Scanner scan = new Scanner(f);
			String line = scan.nextLine();
			line.split(",");
			while (scan.hasNextLine()) {
				studentTable.add(scan.nextLine());
			}

			String[][] table = new String[studentTable.size()][];
			for (int i = 0; i < studentTable.size(); i++) {
				table[i] = studentTable.get(i).split(",", 15);
			}
			scan.close();
			return table;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * writes the final placement of students in each section to a csv file called Output.csv
	 * And outputs the spread of students with special attributes in a csv file called Analysis.csv
	 * @param section
	 * @param filePath
	 */
	
	public static void writeCSVFile(Section[] section, String filePath){
		try{
			FileWriter writer = new FileWriter(filePath+"Output.csv");
			writer.append("Students " + "," + "Section");
			writer.append("\n");
			for(int i = 0; i < section.length; i++){
				for(int j = 0; j < section[i].getStudents().size(); j++){
					writer.append(section[i].getStudents().get(j).getID() + "," + section[i].getUniqueID());
					writer.append("\n");
				}
			}
			writer.flush();
			writer.close();
			FileWriter writer2 = new FileWriter("Analysis.csv");
			writer2.append("Students Not Placed");
			writer2.append("\n");
			ArrayList<Student> s = section[0].getModel().getAlgorithm().getStudentsNeverPlaced();
			for(Student stu: s){
				writer2.append(stu.getID());
				writer2.append("\n");
			}
			writer2.append("Section, AES 1, AES 2, SSS 1, SSS 2, Transfer 1, Transfer 2, Continuing 1, Continuing 2, Athletes, Male, Female, Total");
			writer2.append("\n");
			for(int i = 0; i < section.length; i++){
				writer2.append(section[i].getUniqueID() + ",");
				writer2.append(section[i].getAES1Count() + "," + section[i].getAES2Count()+ ",");
				writer2.append(section[i].getSupport1Count() + "," + section[i].getSupport2Count() + ",");
				writer2.append(section[i].getTransfer1Count() + "," + section[i].getTransfer2Count()+",");
				writer2.append(section[i].getContinue1Count() + "," + section[i].getContinue2Count()+",");
				writer2.append(section[i].getAthleteCount()+ ",");
				writer2.append(section[i].getGenderCount("M") + ","+ section[i].getGenderCount("F") + ",");
				writer2.append(section[i].getStudents().size() + "\n");
			}
			writer2.flush();
			writer2.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
