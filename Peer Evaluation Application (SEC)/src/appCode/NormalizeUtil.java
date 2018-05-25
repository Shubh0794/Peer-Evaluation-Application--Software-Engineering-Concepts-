package appCode;

import java.text.DecimalFormat;

import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * Normalizes the scores entered by a student and displays it
 */
public class NormalizeUtil {
	/*
	 * Performs normalization
	 * (Input -> The table of survey scores entered by the student)
	 * (Output-> returns Normalized scores of the group)
	 */
	public static double[] normalizeScores(JTable surveyTable) {
		TableModel surveyTableModel = surveyTable.getModel();
		int numStudents = surveyTableModel.getRowCount();
		int numColumns = surveyTableModel.getColumnCount();
		if(numStudents>0 && numColumns>1) {
			DecimalFormat decimalFormat = new DecimalFormat("#.00");
			double[] normalizedScores = new double[numStudents];
			double rowSum = 0, totalSum = 0;
			int i,j;
			totalSum = getTotalSum(surveyTable);
			if(totalSum > 0) {
				for(i=0 ; i<numStudents; i++) {
					for(j=1 ; j<numColumns; j++) {
						rowSum +=  (Integer) surveyTableModel.getValueAt(i,j);
					}
					String temp = decimalFormat.format(rowSum/totalSum);
					normalizedScores[i] = Double.valueOf(temp);
					rowSum = 0;
				}		
				return normalizedScores;
			}
		}
		return null;
	}
	
	/*
	 * Calculates the Total Sum of all the scores entered for all the student
	 * (Input-> The table containing the survey scores entered by the student)
	 * (Output-> returns the Total Sum)
	 */
	public static double getTotalSum(JTable surveyTable) {
		TableModel surveyTableModel = surveyTable.getModel();
		int numStudents = surveyTableModel.getRowCount();
		int numColumns = surveyTableModel.getColumnCount();
		int i,j;
		double totalSum = 0;
		if(0 == NormalizeUtil.validateScores(surveyTable)) {
			for(i=1 ; i<numColumns; i++) {
				for(j=0 ; j<numStudents; j++) {
					totalSum += (Integer) surveyTableModel.getValueAt(j, i);
				}
			}
			return totalSum;
		}
		return 0;
	}
	
	/*
	 * Validate the scores
	 * (Input-> The table containing the survey scores entered by the student)
	 * (Output-> returns 0 if scores are valid or number from 1 to 4 if invalid)
	 */
	public static int validateScores(JTable surveyTable) {
		TableModel surveyTableModel = surveyTable.getModel();
		int numStudents = surveyTableModel.getRowCount();
		int numColumns = surveyTableModel.getColumnCount();
		int i,j;
		double temp,totalSum = 0;;
		try {
			for(i=1 ; i<numColumns; i++) {
				for(j=0 ; j<numStudents; j++) {
					if(null == surveyTableModel.getValueAt(j, i)) {
						// Empty Survey Score
						return 1;
					}else {
						temp = (Integer) surveyTableModel.getValueAt(j, i);
					}
					if( temp< 0 || temp > Double.MAX_VALUE){
						// Invalid Input Error
						return 2;
					}else {
						totalSum +=  temp;
					}
				}
			}
			if(totalSum == 0) {
				// Zero Sum Error
				return 3;
			}
		}catch (ClassCastException e){
			// ClassCastException Error
			return 4;   
		}
		// Scores entered are valid
		return 0;
	}
}
