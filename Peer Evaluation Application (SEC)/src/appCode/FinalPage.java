package appCode;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JLabel;

@SuppressWarnings("serial")
/**
 * Displays the Normalized Scores in tabular form
 */
public class FinalPage extends JFrame {

	private JPanel contentPane;
	private JTable normalizedTable;
	private TableModel surveyTableModel; 
	private double[] normalizedScores;
	private JLabel lblNewLabel;

	/*
	 * Initializes the area for displaying normalized scores
	 * (Input-> Table containing the scores)
	 */
	private void initGUI(JTable surveyTable) {
		/*
		 * Creates panel for showing scores and displays students name and their scores
		 */
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		surveyTableModel = surveyTable.getModel();
		int numStudents = surveyTableModel.getRowCount();
		int i=0;
		String[] studentNames = new String[numStudents];
		String[] columnNames = {surveyTable.getColumnName(0),"Normalized Score"};
		for(i=0; i<studentNames.length; i++) {
			studentNames[i] = (String)surveyTableModel.getValueAt(i, 0);
		}
		DefaultTableModel tableModel = new DefaultTableModel(columnNames,0) {
			@Override
			
			/*
			 *  Sets all cells to non-editable mode for restricting a student to not modify normalized scores
			 *  (Input-> Number of rows, Numbers of Columns, in the table)
			 *  (Output-> returns false (non-editable) by default)
			 */
			public boolean isCellEditable(int row, int col){
			   return false;
			}
		};
		normalizedTable = new JTable(tableModel);
		for(i=0; i<studentNames.length; i++) {
			Object[] rowData = {studentNames[i],normalizedScores[i]};
			tableModel.addRow(rowData);
		}
		contentPane.setLayout(null);
		normalizedTable.getTableHeader().setReorderingAllowed(Boolean.FALSE);
		
		/*
		 *  Enables scrolling through the normalized score list, in case the panel size exceeds the limit
		 */
		JScrollPane scrollPane = new JScrollPane(normalizedTable);
		scrollPane.setBounds(26, 48, 300, 150);
		contentPane.add(scrollPane);
		
		/*
		 * Exits window on while closing
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		setContentPane(contentPane);
		
		/*
		 * Tells the user about the completion of normalization
		 */
		lblNewLabel = new JLabel("Thank you for submitting the survey and the normalized scores are listed below");
		lblNewLabel.setBounds(26, 20, 521, 16);
		contentPane.add(lblNewLabel);
	}

	/*
	 * Sets the title of the window
	 */
	public FinalPage() {
		setTitle("Normalized Scores");
		initGUI(null);
	}
	
	/*
	 * Constructor for the final page
	 */
	public FinalPage(JTable surveyTable) {
		normalizedScores = NormalizeUtil.normalizeScores(surveyTable);
		initGUI(surveyTable);
	}
}
