package appCode;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Window.Type;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
/**
 * Displays a page to get group size from the user
 * Asks the student if he/she wants the previously entered scores to be load
 * If checked -> Display old scores in next window
 * If unchecked -> Displays no pre-populated score entries
 */

public class HomePage extends JFrame implements ActionListener {

	private JPanel contentPane;
	private SurveyPage surveyPage;
	private static HomePage homeFrame;
	private JCheckBox chckbxLoadPreviousSurvey;
	private JSpinner numStudents;
	private final JLabel lblWelcomeToStudent = new JLabel("Welcome to Student Evaluation ... !!!");
	private JLabel lblNewLabel;

	/*
	 * Launches the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					homeFrame = new HomePage();
					homeFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * Creates the frame in the homepage with all components such as button and text
	 */
	public HomePage() {
		
		/*
		 *  Title of home page window 
		 */
		setFont(new Font("Georgia", Font.PLAIN, 12));
		setTitle("Student Peer & Self Evaluation Portal\n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 250);
		
		/*
		 *  Window Settings 
		 */
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*
		 *  Next button for moving to next window 
		 */
		JButton btnNext = new JButton("Next");
		btnNext.setFont(UIManager.getFont("Button.font"));
		btnNext.addActionListener(this);
		btnNext.setBounds(327, 94, 98, 29);
		contentPane.add(btnNext);
		
		/* 
		 * Checking if we need to load previous scores 
		 */
		chckbxLoadPreviousSurvey = new JCheckBox("Load Previous Survey ?");
		chckbxLoadPreviousSurvey.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxLoadPreviousSurvey.setFont(UIManager.getFont("TextPane.font"));
		chckbxLoadPreviousSurvey.setBounds(130, 65, 174, 29);
		contentPane.add(chckbxLoadPreviousSurvey);
		numStudents = new JSpinner();
		numStudents.setModel(new SpinnerNumberModel(2, 2, 7, 1));
		numStudents.setBounds(389, 30, 37, 40);
		contentPane.add(numStudents);
		
		/* 
		 * Checking the number of students in the group 
		 */
		JLabel lblHowManyStudents = new JLabel("How many students are in your group ?\n");
		lblHowManyStudents.setHorizontalAlignment(SwingConstants.CENTER);
		lblHowManyStudents.setBounds(137, 42, 250, 16);
		contentPane.add(lblHowManyStudents);
		lblWelcomeToStudent.setBounds(137, 7, 240, 29);
		contentPane.add(lblWelcomeToStudent);
		
		/*
		 * Shows an image on home page
		 */
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(HomePage.class.getResource("/appCode/image1.png")));
		lblNewLabel.setBounds(16, 12, 120, 111);
		contentPane.add(lblNewLabel);
	}

	@Override
	/*
	 * Takes the user to next page for entering/updating scores
	 * (Input-> User action ( clicking Next button))
	 */
	public void actionPerformed(ActionEvent e) {
		surveyPage = new SurveyPage(chckbxLoadPreviousSurvey.isSelected(),(Integer) numStudents.getValue());
		homeFrame.setVisible(Boolean.FALSE);
		surveyPage.setVisible(Boolean.TRUE);
	}
}
