import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.*;
import java.net.*;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;


public class LauncherFrame extends JFrame {
	
	// Declare Vars
	private JLabel contentPane;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private Image img;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {

			LauncherFrame frame = new LauncherFrame();
			frame.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public LauncherFrame(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(860, 440, 450, 300);
		setResizable(false);
		contentPane = new JLabel(new ImageIcon("src/ask.png"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(18, 148, 203));
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel, BorderLayout.SOUTH);
        
        //Text Area, use outputText.append() to send stuff
        JTextArea outputText = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputText);
        contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JButton btnClient = new JButton("Client");
		panel.add(btnClient);
		
		JButton btnHost = new JButton("Host");
		panel.add(btnHost);
		
		JButton btnCancel = new JButton("Cancel");
		panel.add(btnCancel);
		
		btnHost.addActionListener(new ActionListener() { //required to be opened twice
			public void actionPerformed(ActionEvent e) {
				EelsAndEscalatorsServer server = new EelsAndEscalatorsServer();
				ClientFrame frame = new ClientFrame();
				contentPane.removeAll();
				//panel.remove(btnClient);
				//panel.remove(btnHost);
				panel.updateUI();
				outputText.append("Server now started.");
			}
		});
		
		btnClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientFrame frame = new ClientFrame();
				outputText.append("Client now started.");
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				outputText.append("Closing launcher.");
				System.exit(0);
			}
		});
	}
	
	
	public class ConsoleArea{
		private final JTextArea textArea;
		
		public ConsoleArea(JTextArea textArea){
			this.textArea = textArea;
			
		}
	}
}
