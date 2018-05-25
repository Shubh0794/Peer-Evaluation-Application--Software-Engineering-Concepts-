package appCode;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.JLabel;

@SuppressWarnings("serial")
/**
 * Allows a student to enter scores for normalization
 */
public class SurveyPage extends JFrame implements ActionListener {
	
	private JPanel contentPane;
	private JTable surveyTable;
	private int numStudents;
	private boolean ifLoadPrevious;
	private JLabel lblPleaseEnterupdateThe;

	public SurveyPage() {
		setTitle("Fill Survey");
		initGUI();
	}
	
	/* 
	 * Checks if previous scores need to be loaded
	 */
	public SurveyPage(boolean ifLoadPrevious,int numStudents) {
		
		this.ifLoadPrevious = ifLoadPrevious;
		this.numStudents = numStudents;
		initGUI();
	}
	
	/*
	 * Lists the Students name and scoring criteria
	 */
	private void initGUI() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 350);
		contentPane = new JPanel();
		String[] studentNames = {"Puneeth", "Muthu", "Raman", "Shubham", "Karan","Bob","Jane"};
		String[] columnNames = {"Name", "Work Evaluation", "Professionalism", "Meeting Participation"};
		DefaultTableModel tableModel = new DefaultTableModel(columnNames,0) {
			@Override
			/*
			 * Makes the headers in the table non-editable by user
			 * (Input-> Number of rows, Number of columns, in the table)
			 * (Output-> returns true if editable or false if non-editable)
			 */
			public boolean isCellEditable(int row, int col){
				return (col==0) ? false: true;
			}
		};
		
		/*
		 * Creates a table to display previously entered survey scores, if user wants to use them
		 * (This table also stores the survey filled by user)
		 */
		surveyTable = new JTable(tableModel);
		Integer[] surveyValues = {0,1,2,3,4,5};
		int[][] prefilledValues = {{1,3,3},{4,5,0},{3,3,3},{4,4,4},{5,5,5},{1,1,1},{2,2,2}};
		for(int i=0; i<numStudents; i++) {
			
			if(!ifLoadPrevious) {
				
				Object[] rowData = {studentNames[i],null,null,null};
				tableModel.addRow(rowData);
			}
			else {
				
				Object[] rowData = {studentNames[i],prefilledValues[i][0],prefilledValues[i][1],prefilledValues[i][2]};
				tableModel.addRow(rowData);
			}
		}
		
		/*
		 * Receives and stores the survey scores for each cell in the table
		 */
		JComboBox<Object> surveyBox = new JComboBox<Object>(surveyValues);
		for(int j=1;j<=3;j++) {
			
			TableColumn column = surveyTable.getColumnModel().getColumn(j);
			column.setCellEditor(new DefaultCellEditor(surveyBox));
		}
		contentPane.setLayout(null);
		surveyTable.getTableHeader().setReorderingAllowed(Boolean.FALSE);
		
		/*
		 * Allows scrolling through students list if it exceeds the default size
		 */
		JScrollPane scrollPane = new JScrollPane(surveyTable);
		scrollPane.setBounds(24, 53, 476, 102);
		contentPane.add(scrollPane);
		setContentPane(contentPane);
		
		/*
		 * Submits the scores when user clicks on the button
		 */
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(372, 167, 128, 29);
		btnSubmit.addActionListener(this);
		contentPane.add(btnSubmit);
		
		/*
		 * Requests user to update the scores
		 */
		lblPleaseEnterupdateThe = new JLabel("Please enter/update the survey scores");
		lblPleaseEnterupdateThe.setBounds(26, 25, 244, 16);
		contentPane.add(lblPleaseEnterupdateThe);
	}

	@Override
	/*
	 * Detects and throws exceptions for invalid scores
	 * (Input-> User action (clicking submit button))
	 */
	public void actionPerformed(ActionEvent e) {
		
		int responseCode = NormalizeUtil.validateScores(surveyTable);
		switch(responseCode) {
			/*
			 * Throws error if scores are not entered for all students 
			 */
			case 1 : JOptionPane.showMessageDialog(this,
					"Please enter scores for all students",
					"Evaluation Error",
					JOptionPane.ERROR_MESSAGE);
			break;
			/*
			 * Throws error if all the scores are entered as zero
			 */
			case 3: JOptionPane.showMessageDialog(this,
					"All scores cannot be 0",
					"Evaluation Error",
					JOptionPane.ERROR_MESSAGE);
			break;
			/*
			 * No error
			 */
			case 0: FinalPage finalPage = new FinalPage(surveyTable);
					this.setVisible(Boolean.FALSE);
					finalPage.setVisible(Boolean.TRUE);
		}
	}
}
