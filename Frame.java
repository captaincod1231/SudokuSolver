import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class deals with the making of the JFrame and all of the components
 * within it.
 * Date Last Modified: 9/21/19
 * 
 * @author Will Holland
 *
 */
public class Frame {
	private static JFrame JF; // The main display frame
	private static JPanel pan1, pan2; // Holds text fields and buttons
	public static Box[][] boxes; // Text field values become Box objects
	static JTextField jt[][]; // Array of text fields
	private JButton solve; // The solve button at the bottom
	public static boolean outputFrame;
	private static int rows, cols; // The number of rows and columns in the sudoku

	public static int[][] board = { { 5, 3, 0, 0, 7, 0, 0, 0, 0 }, { 6, 0, 0, 1, 9, 5, 0, 0, 0 },
			{ 0, 9, 8, 0, 0, 0, 0, 6, 0 }, { 8, 0, 0, 0, 6, 0, 0, 0, 3 }, { 4, 0, 0, 8, 0, 3, 0, 0, 1 },
			{ 7, 0, 0, 0, 2, 0, 0, 0, 6 }, { 0, 6, 0, 0, 0, 0, 2, 8, 0 }, { 0, 0, 0, 4, 1, 9, 0, 0, 5 },
			{ 0, 0, 0, 0, 8, 0, 0, 7, 9 } }; // This is a testing board you can toggle at line 60

	public static int[][] antiop = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 3, 0, 8, 5 },
			{ 0, 0, 1, 0, 2, 0, 0, 0, 0 }, { 0, 0, 0, 5, 0, 7, 0, 0, 0 }, { 0, 0, 4, 0, 0, 0, 1, 0, 0 },
			{ 0, 9, 0, 0, 0, 0, 0, 0, 0 }, { 5, 0, 0, 0, 0, 0, 0, 7, 3 }, { 0, 0, 2, 0, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 4, 0, 0, 0, 9 } }; // This is a testing board that was designed to work against brute force
												// and is also toggleable at line 61

	/**
	 * This constructor initializes everything and controls the JFrame and the
	 * elements inside it.
	 */
	public Frame() {
		JF = new JFrame("Sudoku Solver");
		JF.setVisible(true);
		JF.pack();
		JF.setLayout(new BorderLayout());
		JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		rows = 9;
		cols = 9;

		pan1 = new JPanel(); // text fields
		pan2 = new JPanel(); // solve button
		pan1.setLayout(new GridLayout(rows, cols, 5, 5));

		jt = new JTextField[rows][cols];
		solve = new JButton("Solve!");
		JF.add(pan1);
		pan2.add(solve);
		JF.add(pan2, BorderLayout.SOUTH);
		outputFrame = false;

		// Loops through all of the text fields and initializes them
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				// jt[i][j] = new JTextField(Integer.toString(board[i][j])); //Preset sudoku
				// puzzle
				// jt[i][j] = new JTextField(Integer.toString(antiop[i][j])); //Preset sudoku
				// puzzle
				jt[i][j] = new JTextField(""); // User input through the JTextFields
				jt[i][j].setFont(new Font("Arial", Font.PLAIN, 36));
				jt[i][j].setColumns(2);
				pan1.add(jt[i][j]);
			}
		}
		JF.setSize(JF.getPreferredSize());

		// Waits for the solve button to get pressed so that all of the solving can
		// occur
		solve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boxes = new Box[rows][cols];
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						boxes[i][j] = new Box(jt[i][j], i, j);
					}
				}
				Solver.solve(boxes, rows, cols);
			}
		});
	}

	/**
	 * This method displays the solved sudoku puzzle on an identical JFrame window.
	 * 
	 * @param boxes is what is going to have the values taken from them to add to
	 *              the JTextFields
	 */
	public static void lastStep(Box[][] boxes) {
		final JFrame jf2 = new JFrame("Solved Sudoku");
		jf2.setLayout(new BorderLayout());
		JPanel jp = new JPanel();
		final JPanel buttons = new JPanel();
		final JButton b = new JButton("Solve Another?"); // Prompts user to allow another puzzle to be solved
		final JButton yes = new JButton("Yes!   (:"); // The yes answer button
		final JButton no = new JButton("No!   );"); // The no answer button
		jp.setLayout(new GridLayout(rows, cols, 5, 5));
		JTextField jt2[][] = new JTextField[rows][cols];

		// Loops through to set the text fields equal to their corresponding Box value
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				jt2[i][j] = new JTextField(boxes[i][j].valueOfText + "");
				jt2[i][j].setEditable(false);
				jt2[i][j].setColumns(2);
				jt2[i][j].setFont(new Font("Arial", Font.PLAIN, 36));
				jp.add(jt2[i][j]);
			}
		}

		buttons.add(b);
		jf2.add(buttons, BorderLayout.SOUTH);
		jf2.add(jp, BorderLayout.NORTH);
		jf2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf2.pack();
		jf2.setSize(JF.getWidth(), JF.getHeight());
		jf2.setVisible(true);

		// Waits for the button to be solved to prompt the yes or no answer buttons
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttons.add(yes);
				buttons.add(no);
				b.setVisible(false);
			}
		});
		// Creates a new JFrame window and allows another puzzle to be solved
		yes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jf2.dispose();
				JF.dispose();
				new Frame();
			}
		});
		// Cancels the program
		no.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
				;
			}
		});
	}

	public static void main(String args[]) {
		new Frame();
	}
}