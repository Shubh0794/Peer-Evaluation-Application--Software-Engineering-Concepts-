	package test;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.junit.jupiter.api.Test;

import appCode.NormalizeUtil;

/*
 * Tests the Score Normalization Portal
 */
class NormalizeUtilTest {

	private DefaultTableModel tableModel;
	private JTable surveyTable;
	private double[] actualNomalizedScores;

	private DefaultTableModel initializeTableModel() {
		String[] columnNames = {"Name", "Work Evaluation", "Professionalism", "Meeting Participation"};
		DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);
		return tableModel;
	}

	/**
	 * Checks end to end flow of Score Normalization Portal
	 */
	@Test
	void end_to_end_flow_Test() {
		tableModel = initializeTableModel();
		Object[] rowData1 = {"Student Name",5,5,5};
		Object[] rowData2 = {"Student Name",5,5,5};
		double[] expecatedNormalizedScores = {0.5,0.5};
		tableModel.addRow(rowData1);
		tableModel.addRow(rowData2);
		surveyTable = new JTable(tableModel);
		actualNomalizedScores = NormalizeUtil.normalizeScores(surveyTable);
		boolean isEqual = Arrays.equals(actualNomalizedScores, expecatedNormalizedScores);
		assertTrue(isEqual);
	}

	/**
	 * Testing normalization when all survey scores are zero
	 */
	@Test
	void all_Scores_Zero_Test() {
		tableModel = initializeTableModel();
		Object[] rowData1 = {"Student Name",0,0,0};
		Object[] rowData2 = {"Student Name",0,0,0};
		Object[] rowData3 = {"Student Name",0,0,0};
		tableModel.addRow(rowData1);
		tableModel.addRow(rowData2);
		tableModel.addRow(rowData3);
		surveyTable = new JTable(tableModel);
		actualNomalizedScores = NormalizeUtil.normalizeScores(surveyTable);
		assertNull(actualNomalizedScores);
	}

	/**
	 * Testing normalization when One Column Scores are all Zero
	 */
	@Test
	void one_Column_Scores_Zero_Test() {
		tableModel = initializeTableModel();
		Object[] rowData1 = {"Student Name",0,0,1};
		Object[] rowData2 = {"Student Name",0,1,1};
		Object[] rowData3 = {"Student Name",0,3,4};
		double[] expecatedNormalizedScores = {0.1,0.2,0.7};
		tableModel.addRow(rowData1);
		tableModel.addRow(rowData2);
		tableModel.addRow(rowData3);
		surveyTable = new JTable(tableModel);
		actualNomalizedScores = NormalizeUtil.normalizeScores(surveyTable);
		boolean isEqual = Arrays.equals(actualNomalizedScores, expecatedNormalizedScores);
		assertTrue(isEqual);
	}

	/**
	 * Test case to skip outer loop entirely
	 */
	@Test
	void zero_Students_Test() {
		tableModel = initializeTableModel();
		surveyTable = new JTable(tableModel);
		actualNomalizedScores = NormalizeUtil.normalizeScores(surveyTable);
		assertNull(actualNomalizedScores);
	}

	/**
	 * Test case to skip inner loop entirely
	 */
	@Test
	void zero_Grading_Columns_Test() {
		Object[] rowData1 = {"Name1"};
		Object[] rowData2 = {"Name2"};
		String[] columnNames = {"Student Name"};
		DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);
		tableModel.addRow(rowData1);
		tableModel.addRow(rowData2);
		tableModel.addRow(rowData1);
		surveyTable = new JTable(tableModel);
		actualNomalizedScores = NormalizeUtil.normalizeScores(surveyTable);
		assertNull(actualNomalizedScores);
	}

	/**
	 * Test 1 pass through the loop
	 */
	@Test
	void only_1_Student_Test() {
		tableModel = initializeTableModel();
		Object[] rowData1 = {"Student Name",3,4,5};
		double[] expecatedNormalizedScores = {1};
		tableModel.addRow(rowData1);
		surveyTable = new JTable(tableModel);
		actualNomalizedScores = NormalizeUtil.normalizeScores(surveyTable);
		boolean isEqual = Arrays.equals(actualNomalizedScores, expecatedNormalizedScores);
		assertTrue(isEqual);
	}

	/**
	 * Test normalization for Invalid Input (character, symbols)
	 */
	@Test
	void invalid_Input_NaN_Test() {
		tableModel = initializeTableModel();
		Object[] rowData1 = {"Student Name","x","y","z"};
		tableModel.addRow(rowData1);
		surveyTable = new JTable(tableModel);
		actualNomalizedScores = NormalizeUtil.normalizeScores(surveyTable);
		assertNull(actualNomalizedScores);
	}
	
	/**
	 * Test normalization for negative scores
	 */
	@Test
	void invalid_Input_Negative_Scores_Test() {
		tableModel = initializeTableModel();
		Object[] rowData1 = {"Student Name",-3};
		tableModel.addRow(rowData1);
		surveyTable = new JTable(tableModel);
		actualNomalizedScores = NormalizeUtil.normalizeScores(surveyTable);
		assertNull(actualNomalizedScores);
	}
	
	/**
	 * Test normalization for Values Greater than Maximum Double Value
	 **/
	@Test
	void invalid_Input_Value_Exceeds_Maximum_Test() {
		tableModel = initializeTableModel();
		Object[] rowData1 = {"Student Name",(Double.MAX_VALUE*5)};
		tableModel.addRow(rowData1);
		surveyTable = new JTable(tableModel);
		actualNomalizedScores = NormalizeUtil.normalizeScores(surveyTable);
		assertNull(actualNomalizedScores);
	}
	
	/**
	 * Test normalization for Null Score Values
	 **/
	@Test
	void invalid_Input_Null_Scores_Test() {
		tableModel = initializeTableModel();
		Object[] rowData1 = {"Student Name",(Double.MAX_VALUE*5)};
		tableModel.addRow(rowData1);
		surveyTable = new JTable(tableModel);
		actualNomalizedScores = NormalizeUtil.normalizeScores(surveyTable);
		assertNull(actualNomalizedScores);
	}
	
	/**
	 * Test multiple pass through the loop, minimum number of students + 1
	 * 	    * (within the range 2 to 7, we take minimum as 4)
	 */
	@Test
	void test_Min_No_Of_Students_plus1() {
		tableModel = initializeTableModel();
		Object[] rowData1 = {"Student Name1",3,4,5};
		Object[] rowData2 = {"Student Name2",1,5,2};
		Object[] rowData3 = {"Student Name3",4,1,3};
		double[] expecatedNormalizedScores = {0.43, 0.29, 0.29};
		tableModel.addRow(rowData1);
		tableModel.addRow(rowData2);
		tableModel.addRow(rowData3);
		surveyTable = new JTable(tableModel);
		actualNomalizedScores = NormalizeUtil.normalizeScores(surveyTable);
		boolean isEqual = Arrays.equals(actualNomalizedScores, expecatedNormalizedScores);
		assertTrue(isEqual);
	}
	/**
	    * Testing multiple pass through the outer loop, testing for average number of group members
	    * (within the range 2 to 7, we take average as 4)
	    */
	    @Test
	    void test_Average_Number_Of_Students() {
	        tableModel = initializeTableModel();
	        Object[] rowData1 = {"Student Name1",3,4,5};
	        Object[] rowData2 = {"Student Name2",1,5,2};
	        Object[] rowData3 = {"Student Name3",4,1,3};
	        Object[] rowData4 = {"Student Name4",5,2,4};
	        double[] expecatedNormalizedScores = {0.31, 0.21, 0.21, 0.28};
	        tableModel.addRow(rowData1);
	        tableModel.addRow(rowData2);
	        tableModel.addRow(rowData3);
	        tableModel.addRow(rowData4);
	        surveyTable = new JTable(tableModel);
	        actualNomalizedScores = NormalizeUtil.normalizeScores(surveyTable);
	        boolean isEqual = Arrays.equals(actualNomalizedScores, expecatedNormalizedScores);
	        assertTrue(isEqual);
	    }
	    
	    /**
	    * Testing multiple pass through the outer loop, testing for maximum number of group members - 1 
	    * (for this program maximum number of )
	    */
	    @Test
	    void test_Max_No_Of_Students_Minus1() {
	        tableModel = initializeTableModel();
	        Object[] rowData1 = {"Student Name1",3,4,5};
	        Object[] rowData2 = {"Student Name2",1,5,2};
	        Object[] rowData3 = {"Student Name3",4,1,3};
	        Object[] rowData4 = {"Student Name4",5,2,4};
	        Object[] rowData5 = {"Student Name5",4,3,1};
	        Object[] rowData6 = {"Student Name6",1,2,3};
	        double[] expecatedNormalizedScores = {0.23, 0.15, 0.15, 0.21, 0.15, 0.11};
	        tableModel.addRow(rowData1);
	        tableModel.addRow(rowData2);
	        tableModel.addRow(rowData3);
	        tableModel.addRow(rowData4);
	        tableModel.addRow(rowData5);
	        tableModel.addRow(rowData6);
	        surveyTable = new JTable(tableModel);
	        actualNomalizedScores = NormalizeUtil.normalizeScores(surveyTable);
	        boolean isEqual = Arrays.equals(actualNomalizedScores, expecatedNormalizedScores);
	        assertTrue(isEqual);
	    }
	    
	    /**
	    * Testing multiple pass through the outer loop, testing for maximum number of group members
	    * (for this program maximum number of )
	    */
	    @Test
	    void test_Max_Number_Of_Students() {
	        tableModel = initializeTableModel();
	        Object[] rowData1 = {"Student Name1",3,4,5};
	        Object[] rowData2 = {"Student Name2",1,5,2};
	        Object[] rowData3 = {"Student Name3",4,1,3};
	        Object[] rowData4 = {"Student Name4",5,2,4};
	        Object[] rowData5 = {"Student Name5",4,3,1};
	        Object[] rowData6 = {"Student Name6",1,2,3};
	        Object[] rowData7 = {"Student Name7",3,5,2};
	        double[] expecatedNormalizedScores = {0.19, 0.13, 0.13, 0.17, 0.13, 0.1, 0.16};
	        tableModel.addRow(rowData1);
	        tableModel.addRow(rowData2);
	        tableModel.addRow(rowData3);
	        tableModel.addRow(rowData4);
	        tableModel.addRow(rowData5);
	        tableModel.addRow(rowData6);
	        tableModel.addRow(rowData7);
	        surveyTable = new JTable(tableModel);
	        actualNomalizedScores = NormalizeUtil.normalizeScores(surveyTable);
	        boolean isEqual = Arrays.equals(actualNomalizedScores, expecatedNormalizedScores);
	        assertTrue(isEqual);
	    }
	    /**
	     * Test for only 1 response in the survey
	     * Outer loop (number of students) runs once, inner loop (number of columns in survey) runs for minimum number of times
	     */
	    @Test
	    void only_1_Student_only_1_survey_response_Test() {
	    		String[] columnNames = {"Name", "Work Evaluation"};
			DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);
			Object[] rowData1 = {"Student Name",3};
			double[] expecatedNormalizedScores = {1};
			tableModel.addRow(rowData1);
			surveyTable = new JTable(tableModel);
			actualNomalizedScores = NormalizeUtil.normalizeScores(surveyTable);
			boolean isEqual = Arrays.equals(actualNomalizedScores, expecatedNormalizedScores);
			assertTrue(isEqual);
	    }

}
