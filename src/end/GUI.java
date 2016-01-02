package end;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class GUI extends JPanel implements PropertyChangeListener {



	private JFrame frame;
	private JPanel panel;
	private JProgressBar pBar;
	private static final int HEIGHT = 600;
	private JFileChooser fc;
	private File professorFile, studentFile;
	private JLabel pFileName, sFileName;
	private JSpinner cap;
	private JSpinner iterations;
	private int progress;
	private Task t;

	private static final int WIDTH = 600;

	/**
	 * Create New GUI object
	 */
	public GUI() {
		setProgress(0);
		setModel(new Model());
		frame = createFrame();

		panel = createPanel();

		createButton("Open Professor File", 10, 10, 200, 40, new ProfessorListener());
		createButton("Open Student File", 10, 60, 200, 40, new StudentListener());

		createButton("Run", 10, 300, 150, 40, new RunListener());

		pBar = createPBar(0, 100, 50, 500, 500, 30);

		setCap(createSpinner(19, new CapListener(), 200, 120, 125, 40));
		setIterations(createSpinner(10, new IterationListener(), 200, 180, 125, 40));

		createLabel("Max number of students", 10, 120, 200, 40);
		createLabel("Number of iterations", 10, 180, 200, 40);
		pFileName = createLabel("No File Selected", 220, 10, 150, 40);
		sFileName = createLabel("No File Selected", 220, 60, 150, 40);

		frame.setVisible(true);
	}

	/**
	 * 
	 * @param n
	 *            current % progress
	 */
	public void updateBar(int n) {
		pBar.setValue(n);
	}

	/**
	 * creates a new JLabel and adds it to the JPanel
	 * 
	 * @param label
	 *            Text to be displayed
	 * @param x
	 *            x-cordinate of text
	 * @param y
	 *            y-cordinate of text
	 * @param width
	 *            width of text
	 * @param height
	 *            height of text
	 * @return new JLabel
	 */
	private JLabel createLabel(String label, int x, int y, int width, int height) {
		JLabel tempLabel = new JLabel();
		tempLabel.setText(label);
		tempLabel.setBounds(x, y, width, height);
		panel.add(tempLabel);
		return tempLabel;
	}

	/**
	 * creates new JButton and adds it to Panel
	 * 
	 * @param label
	 *            Text to be displayed on the button
	 * @param x
	 *            x-coordinate of the button
	 * @param y
	 *            y-coordinate of the button
	 * @param width
	 *            button width
	 * @param height
	 *            button height
	 * @param al
	 *            action listener for button
	 * @return new JButton
	 */
	private JButton createButton(String label, int x, int y, int width, int height, ActionListener al) {
		JButton temp = new JButton(label);
		temp.setBounds(x, y, width, height);
		temp.addActionListener(al);
		panel.add(temp);
		return temp;
	}

	/**
	 * 
	 * @param min
	 *            minimum value of progress bar
	 * @param max
	 *            maximum value of progress bar
	 * @param x
	 *            x-coordinate of progress bar
	 * @param y
	 *            y-coordinate of progress bar
	 * @param width
	 *            width of bar
	 * @param height
	 *            height of bar
	 * @return new JProgressBar
	 */
	private JProgressBar createPBar(int min, int max, int x, int y, int width, int height) {
		JProgressBar temp = new JProgressBar();
		temp.setMinimum(min);
		temp.setMaximum(max);
		temp.setBounds(x, y, width, height);
		panel.add(temp);
		return temp;
	}

	/**
	 * creates a new JSpinner and adds it to the panel
	 * 
	 * @param val
	 *            initial value of JSpinner
	 * @param cl
	 *            change listener for JSpinner
	 * @param x
	 *            x-coordinate of spinner
	 * @param y
	 *            y-coordinate of spinner
	 * @param width
	 *            width of spinner
	 * @param height
	 *            height of spinner
	 * @return new JSpinner
	 */
	private JSpinner createSpinner(int val, ChangeListener cl, int x, int y, int width, int height) {
		JSpinner temp = new JSpinner();
		temp.setValue(val);
		temp.addChangeListener(cl);
		temp.setBounds(x, y, width, height);
		panel.add(temp);
		return temp;
	}

	/**
	 * Initializes JPanel
	 * 
	 * @return new JPanel
	 */
	private JPanel createPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.add(panel);
		return panel;
	}

	/**
	 * Initializes new JFrame
	 * 
	 * @return new JFrame
	 */
	private JFrame createFrame() {
		JFrame frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		return frame;
	}

	/**
	 * shows error message
	 */
	public void errorMessage() {
		JOptionPane.showMessageDialog(null, "Program failed due to error");
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	/**
	 * 
	 * @author sam creates new listener for professor file button
	 */
	private class ProfessorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
			fc.setFileFilter(filter);
			fc.showOpenDialog(panel);
			setProfessorFile(fc.getSelectedFile());
			if (getProfessorFile() == null) {
				JOptionPane.showMessageDialog(null, "No file selected!");
			} else {
				pFileName.setText(getProfessorFile().getName());
			}
		}

	}

	/**
	 * creates new action listener for student file button
	 * 
	 * @author sam
	 *
	 */
	private class StudentListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
			fc.setFileFilter(filter);
			fc.showOpenDialog(panel);
			setStudentFile(fc.getSelectedFile());
			if (getStudentFile() == null) {
				JOptionPane.showMessageDialog(null, "No file selected!");
			} else {
				sFileName.setText(getStudentFile().getName());
			}
		}
	}

	/**
	 * creates change listener for the max number of students per section
	 * 
	 * @author sam
	 *
	 */
	private class CapListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			int cVal = (int) getCap().getValue();
			if (cVal <= 0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						JOptionPane.showMessageDialog(null, "Max number of students needs to be above 0");
						getCap().setValue(19);
					}
				});
			}
		}
	}

	/**
	 * creates change listener for male percentage
	 * 
	 * @author Sam
	 *
	 */
	private class IterationListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			int cVal = (int) getIterations().getValue();

			if (cVal < 0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						JOptionPane.showMessageDialog(null, "Iteration value needs to be above 0");
						getIterations().setValue(10);
					}
				});
			}
		}
	}

	private Model model;

	/**
	 * creates a new action listener for the run button
	 * 
	 * @author Sam
	 *
	 */
	private class RunListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (getStudentFile() == null || getProfessorFile() == null) {
				JOptionPane.showMessageDialog(null, "No file(s) selected");
			} else {
				t = new Task(GUI.this, (int) getIterations().getValue());
				t.addPropertyChangeListener(GUI.this);
				t.execute();
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		pBar.setValue(t.getProgress());
	}

	public JSpinner getIterations() {
		return iterations;
	}

	public void setIterations(JSpinner iterations) {
		this.iterations = iterations;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public File getProfessorFile() {
		return professorFile;
	}

	public void setProfessorFile(File professorFile) {
		this.professorFile = professorFile;
	}

	public File getStudentFile() {
		return studentFile;
	}

	public void setStudentFile(File studentFile) {
		this.studentFile = studentFile;
	}

	public JSpinner getCap() {
		return cap;
	}

	public void setCap(JSpinner cap) {
		this.cap = cap;
	}
}
