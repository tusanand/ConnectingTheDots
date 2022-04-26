import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConnectingTheDotsMain extends JFrame implements ActionListener, MouseListener {
	private JFrame frame;
	private JButton btnSave;
	private JButton btnLoad;
	private JButton btnAutogenerateDots;
	private JPanel grid;
	private JButton btnClear;
	private JButton btnRun;
	private JDialog dotCountDialog;
	private JTextField dotCountInput;
	private JButton dotCountConfirm;
	private JLabel errorLabel;
	private Graphics graphics;
	private int dotCount;
	private int circleDiameter = 10;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectingTheDotsMain window = new ConnectingTheDotsMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ConnectingTheDotsMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Connecting the Dots");
		frame.setBounds(100, 100, 679, 494);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSave.setBounds(588, 10, 70, 32);
		btnSave.addActionListener(this);
		frame.getContentPane().add(btnSave);

		btnLoad = new JButton("Load");
		btnLoad.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnLoad.setBounds(511, 10, 70, 32);
		btnLoad.addActionListener(this);
		frame.getContentPane().add(btnLoad);

		btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnClear.setBounds(357, 10, 100, 32);
		btnClear.addActionListener(this);
		frame.getContentPane().add(btnClear);

		btnRun = new JButton("Run");
		btnRun.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnRun.setBounds(187, 10, 70, 32);
		btnRun.addActionListener(this);
		frame.getContentPane().add(btnRun);

		grid = new JPanel();
		grid.setBackground(Color.WHITE);
		grid.setBounds(10, 52, 645, 395);
		grid.addMouseListener(this);
		frame.getContentPane().add(grid);

		btnAutogenerateDots = new JButton("Auto-Generate Dots");
		btnAutogenerateDots.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAutogenerateDots.setBounds(10, 10, 167, 32);
		btnAutogenerateDots.addActionListener(this);
		frame.getContentPane().add(btnAutogenerateDots);
		
		errorLabel = new JLabel();
		errorLabel.setBounds(20, 60, 200, 30);
		errorLabel.setForeground(Color.RED);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAutogenerateDots) {
			this.showDotCountDialog();
		} else if (e.getSource() == dotCountConfirm) {
			this.dotCountConfirmClick();
		}
		
	}
	
	/**
	 * This method displays a dialog to take user input for dot count to randomly
	 * generate
	 */
	private void showDotCountDialog() {
		dotCountDialog = new JDialog(frame, "Dot Count");
		dotCountDialog.setSize(350, 200);
		dotCountDialog.setResizable(false);
		dotCountDialog.setVisible(true);
		dotCountDialog.setLocationRelativeTo(frame);

		JPanel dotCountPanel = new JPanel();
		dotCountPanel.setLayout(null);
		dotCountDialog.add(dotCountPanel);

		JLabel enterDotCountLabel = new JLabel("Enter number of dots: ");
		enterDotCountLabel.setBounds(20, 20, 150, 50);
		dotCountPanel.add(enterDotCountLabel);

		dotCountPanel.add(errorLabel);

		dotCountInput = new JTextField();
		this.textFieldValidator(dotCountInput, errorLabel);
		dotCountInput.setSize(100, 50);
		dotCountInput.setBounds(170, 30, 100, 30);
		dotCountPanel.add(dotCountInput);

		dotCountConfirm = new JButton("Confirm");
		dotCountConfirm.setBounds(110, 100, 100, 35);
		dotCountConfirm.addActionListener(this);
		dotCountPanel.add(dotCountConfirm);
	}
	
	/**
	 * This method draws dots on the panel
	 * 
	 * @param x
	 * @param y
	 */
	private void drawDots(int x, int y) {
		graphics = grid.getGraphics();
		graphics.fillOval(x, y, circleDiameter, circleDiameter);
	}
	
	/**
	 * This method validates if the input to the field is an integer
	 */
	private void textFieldValidator(JTextField field, JLabel errorLabel) {
		field.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				if (((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)
						|| (ke.getKeyChar() == KeyEvent.VK_DELETE))) {
					field.setEditable(true);
					errorLabel.setText("");
				} else {
					field.setText("");
					errorLabel.setText("*Enter only numeric digits(0-9)");
				}
			}
		});
	}
	
	/**
	 * This method checks if valid dot count was entered or not
	 */
	private void dotCountConfirmClick() {
		dotCountDialog.dispose();
		if (!dotCountInput.getText().equals("")) {
			this.setDotCount(Integer.parseInt(dotCountInput.getText()));
		}
	}
	
	/**
	 * This method displays dots randomly on the screen
	 */
	private void displayRandomDots() {
		Dimension gridDim = grid.getSize();
		int gridHeight = gridDim.height - circleDiameter * 2;
		int gridWidth = gridDim.width - circleDiameter * 2;
		Random r = new Random();
		while (this.dotCount > 0) {
			int randomY = r.nextInt(gridHeight);
			int randomX = r.nextInt(gridWidth);
			this.drawDots(randomX, randomY);
			this.dotCount--;
		}
	}
	
	/**
	 * This method sets dot count entered by the user
	 * 
	 * @param dotCount
	 */
	private void setDotCount(int dotCount) {
		this.dotCount = dotCount;
		this.displayRandomDots();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		this.drawDots(x, y);
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
