package end;

import java.awt.Toolkit;
import java.text.DecimalFormat;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

class Task extends SwingWorker<Void, Void> {

	private GUI g;

	public Task(GUI g, int total) {
		this.g = g;
	}

	/*
	 * Main task. Executed in background thread.
	 */
	@Override
	public Void doInBackground() {

		// Initialize progress property.
		setProgress(0);
		double maxScore = 0;
		System.out.println("iterations " + (int) g.getIterations().getValue());
		for (int i = 0; i < (int) g.getIterations().getValue(); i++) {
			g.setModel(new Model());
			g.getModel().runProgram(g.getProfessorFile(), g.getStudentFile(), (int) g.getCap().getValue());
			if (maxScore < g.getModel().getScore()) {
				maxScore = g.getModel().getScore();
				DataFile.writeCSVFile(g.getModel().getAllSections(), "");

			}

			double prog = 0;
			int prog2 = 0;
			int iter = (int) g.getIterations().getValue();
			prog = (double) (((double) i + 1) / ((double) iter)) * 100;
			prog2 = (int) prog;
			setProgress(prog2);
		}
		DecimalFormat df = new DecimalFormat("#.##");

		JOptionPane.showMessageDialog(null, " Done Running!\n " + df.format(g.getModel().getScore() * 100)
				+ "% of students were placed in one of their preferences");

		return null;
	}

	/*
	 * Executed in event dispatching thread
	 */
	@Override
	public void done() {
		Toolkit.getDefaultToolkit().beep();
	}
}
