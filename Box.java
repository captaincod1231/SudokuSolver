import javax.swing.*;

/**
 * This class is the Box object that is used to store data about each text field
 * in the sudoku puzzle.
 * Date Last Modified: 9/21/19
 * 
 * @author Will Holland
 *
 */
public class Box {
	public int cellNum, row, col, valueOfText;
	public JTextField field;
	public boolean changeable;

	/**
	 * This constructor manages the variables of the Box objects.
	 * 
	 * @param t   is the corresponding text field
	 * @param row is the row location in the sudoku
	 * @param col is the column location in the sudoku
	 */
	public Box(JTextField t, int row, int col) {
		field = t;
		this.row = row;
		this.col = col;
		cellNum = 3 * ((int) (row / 3)) + (int) (col / 3);
		// Checks if the text cell is empty or if it contains a value parsable
		if (field.getText().equals("") || field.getText().equals("0")) {
			valueOfText = 0;
			changeable = true;
		} else {
			valueOfText = Integer.parseInt(field.getText());
			changeable = false;
		}
	}
}