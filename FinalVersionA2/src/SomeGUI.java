import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JTextField;

public class SomeGUI {

	private JFrame frame;
	public ImageNodeHandler inh;
	File f = new File("src/imageNodeHandlerInformation.ser");
	Serializing ser = new Serializing("src/imageNodeHandlerInformation.ser");
	private JTextField retrievedTextField;
	private JTextField textField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SomeGUI window = new SomeGUI();
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
	public SomeGUI() {
		initialize();
	}
	
	
	public void windowClosing(WindowEvent e) {
		ser.Serialize(inh);
		System.out.println("Saving your work...");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Photo Renamer");
		frame.setBounds(100, 100, 788, 585);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 772, 336);
		
		JTextArea display = new JTextArea(16,58);
		display.setEditable(false);
		
		
		JScrollPane scroller = new JScrollPane(display);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		panel.add(scroller);
		
		frame.getContentPane().add(panel);
		
		if (f.exists()) {
			if (f.length() > 0) {
				inh = ser.Deserialize();
				display.setText(getAllImages());
			}
		}
		
		//DO SOMETHING BEFORE CLOSING THE PROGRAM
		frame.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		        ser.Serialize(inh);
		        System.out.println("System is closing now...");
		    }
		});
		
		JButton btnNewButton = new JButton("Load Files");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				LoadingDirectory ld = new LoadingDirectory();
				ArrayList<File> tempFiles = ld.showFileChooserAndReturnFiles();
				File f = new File("src/imageNodeHandlerInformation.ser");
				if (f.exists()) {
					if (f.length() > 0) {
						inh.addImageNodes(ld.convertToImageNodes(tempFiles));
					} else {
						inh = new ImageNodeHandler(ld.convertToImageNodes(tempFiles));
					}
				}

				display.setText(getAllImages());
			}
		});
		btnNewButton.setBounds(10, 512, 752, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Add Tag");
		btnNewButton_1.setBounds(10, 434, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Remove Tag");

		btnNewButton_2.setBounds(110, 434, 108, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		retrievedTextField = new JTextField();
		retrievedTextField.setBounds(171, 404, 418, 20);
		frame.getContentPane().add(retrievedTextField);
		retrievedTextField.setColumns(10);
		
		JButton btnNewButton_3 = new JButton("Retrieve Tag");
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (display.getSelectedText() != null) {
					String somePath = display.getSelectedText();
					retrievedTextField.setText(somePath);
				}
			}
		});
		
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String newTagName = textField.getText().trim();
				Tag tempTag = new Tag(newTagName);
				ImageNode tempNode = inh.findImageNode(retrievedTextField.getText());
				if (!retrievedTextField.getText().isEmpty()) { 
					inh.addTag(tempNode, tempTag);
				}
				retrievedTextField.setText(tempNode.findChild(tempNode).getPathName());
				display.setText(getAllImages());
			}
		});
		
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String newTagToRemove = textField.getText().trim();
				Tag tagToRemove = new Tag(newTagToRemove);
				ImageNode tempNode = inh.findImageNode(retrievedTextField.getText());
				if (!retrievedTextField.getText().isEmpty()) { 
					inh.removeTag(tempNode, tagToRemove);
				}
				retrievedTextField.setText(tempNode.findChild(tempNode).getPathName());
				display.setText(getAllImages());
			}
		});
		
		btnNewButton_3.setBounds(10, 403, 151, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		textField = new JTextField();
		textField.setBounds(228, 435, 207, 20);
		textField.setEditable(true);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

	}
	
	public String getAllImages() {
		String textAreaStr = "";
		for (ImageNode imgz : inh.getKeys()) {
			textAreaStr += imgz.findChild(imgz).getPathName() + "\n";
		}
		return textAreaStr;
	}
}
