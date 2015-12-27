import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
//WHEN COMING BACK:: PRODUCE FREQ FILES
/** 
 * Programming AE2
 * Class to display cipher GUI and listen for events
 */
public class CipherGUI extends JFrame implements ActionListener
{
	//instance variables which are the components
	private JPanel top, bottom, middle;
	private JButton monoButton, vigenereButton;
	private JTextField keyField, messageField;
	private JLabel keyLabel, messageLabel;
	private FileReader reader;
	private PrintWriter writer;
	private String keyword;
	//application instance variables
	//including the 'core' part of the text file filename
	//some way of indicating whether encoding or decoding is to be done
	private MonoCipher mcipher;
	private VCipher vcipher;
	private String filename;//file name user enters
	
	/**
	 * The constructor adds all the components to the frame
	 */
	public CipherGUI()
	{
		this.setSize(400,150);
		this.setLocation(100,100);
		this.setTitle("Cipher GUI");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.layoutComponents();
	}
	
	/**
	 * Helper method to add components to the frame
	 */
	public void layoutComponents()
	{
		//top panel is yellow and contains a text field of 10 characters
		top = new JPanel();
		top.setBackground(Color.yellow);
		keyLabel = new JLabel("Keyword : ");
		top.add(keyLabel);
		
		//key field
		keyField = new JTextField(10);
		keyField.addActionListener(this);
		top.add(keyField);
		
		this.add(top,BorderLayout.NORTH);
		
		//middle panel is yellow and contains a text field of 10 characters
		middle = new JPanel();
		middle.setBackground(Color.yellow);
		messageLabel = new JLabel("Message file : ");
		middle.add(messageLabel);
		messageField = new JTextField(10);
		middle.add(messageField);
		this.add(middle,BorderLayout.CENTER);
		
		//bottom panel is green and contains 2 buttons
		
		bottom = new JPanel();
		bottom.setBackground(Color.green);
		//create mono button and add it to the top panel
		monoButton = new JButton("Process Mono Cipher");
		monoButton.addActionListener(this);
		bottom.add(monoButton);
		//create vigenere button and add it to the top panel
		vigenereButton = new JButton("Process Vigenere Cipher");
		vigenereButton.addActionListener(this);
		bottom.add(vigenereButton);
		//add the top panel
		this.add(bottom,BorderLayout.SOUTH);
	}
	
	/**
	 * Listen for and react to button press events
	 * (use helper methods below)
	 * @param e the event
	 */
	public void actionPerformed(ActionEvent e)
	{   
		if(!getKeyword())//if key word is invalid issue error message
			{
			JOptionPane.showMessageDialog(null, "keyword is invalid", 
			"Entry error", JOptionPane.ERROR_MESSAGE);
			keyField.setText("");//clear field
			
			}
		
		else if(!processFileName())//if file name is invalid issue error message
		{
			JOptionPane.showMessageDialog(null, "File name is invalid", 
					"Entry error", JOptionPane.ERROR_MESSAGE);
					messageField.setText("");//clear field name
		}
		
		else //if key word and file name are valid, create Mono Cipher or Vigenere cipher object and read file
			{
			
			try
				{
		    	filename = messageField.getText() + ".txt";//construct file name by reading user entry and appending .txt
		    	reader = new FileReader(filename);//read file
		    	
		    	
		    	if(e.getSource()==monoButton)//if the user chose mono cipher create object for that and encode/decode content
		    		{
		    		mcipher = new MonoCipher(keyword);
		    		this.processFile(false);//incorporate processFile method to encode/decode file
		    		System.exit(0);
		    		}
		    	
		    	else//if the user chose Vigenere cipher create object for that and encode/decode content
		    		{
		    		vcipher = new VCipher(keyword);
		    		this.processFile(true);//incorporate processFile method to encode/decode file
		    		System.exit(0);
		    		}
			
				}//end of try
			
			catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null, "No file was found", 
				"File Not Found error", JOptionPane.ERROR_MESSAGE);
				messageField.setText("");//clear field name
				}//end of catch
		
		
			}//end of else
	}//end of action performed method
	
	
	/** 
	 * Obtains cipher keyword
	 * If the keyword is invalid, a message is produced
	 * @return whether a valid keyword was entered
	 */
	private boolean getKeyword()
	
	{
		int checks = 0;//incremented when each condition is met (non-empty && upper case && no letters repeated)
		
		//first condition - non-empty
		keyword = keyField.getText();
		if(!keyword.trim().isEmpty())//
			checks++;//if not empty increment checks
			
		//second condition - checks repetition
		int rep = 0;
		for(int i = 0; i< keyword.length(); i++)
		{	
			//checks one letter at a time for repetition
			for(int j = 0; j<keyword.length(); j++)
				{
				if(keyword.charAt(i) == keyword.charAt(j))
					rep++;
				
				}
		}
		if(rep == keyword.length())//each letter is compared with all letters including itself. So, if rep=keyword length then each letter exist only once
			checks++;
		
		//third condition - upper case
		int isUpper = 0;//incremented every time a letter is checked to be in upper case
		
		for(int i = 0; i<keyword.length(); i++)
		{
			switch(keyword.charAt(i))
			{
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				
				
				isUpper++;
			}//end of switch
		}//end of for statement
		if(isUpper==keyword.length())//if all letters are upper case, increments checks
			checks++;
		
			
		if(checks == 3)//if all conditions were met return true
			{
			checks=0;//to start fresh when user enters new keyword
			return true;
			}
		else
			{
			checks=0;//to start fresh when user enters new keyword
			return false;
			}
	}//end of method
	
	/** 
	 * Obtains filename from GUI
	 * The details of the filename and the type of coding are extracted
	 * If the filename is invalid, a message is produced 
	 * The details obtained from the filename must be remembered
	 * @return whether a valid filename was entered
	 */
	private boolean processFileName()
	{
		String fname = messageField.getText();
		int fname_length = fname.length(); 
		
		if(fname.isEmpty())
			return false;//if message file is empty return false
		
		else if(fname.charAt(fname_length-1) == 'P' || fname.charAt(fname_length-1) == 'C')
			return true;//if it's not empty then check if it ends with 'P' or 'C' and return true if so
		
		else
			return false;//return false if it ends with a different letter
		
	}
	
	/** 
	 * Reads the input text file character by character
	 * Each character is encoded or decoded as appropriate
	 * and written to the output text file
	 * @param vigenere whether the encoding is Vigenere (true) or Mono (false)
	 * @return whether the I/O operations were successful
	 */
	private boolean processFile(boolean vigenere)
	{
		LetterFrequencies let = new LetterFrequencies();//create letter frequencies object to access its methods


		//to extract last letter of file name without the .txt
		String mField = messageField.getText();
		int len = mField.length();//length of keyword 
		
		String cFile = mField.substring(0, len-1) + "C" + ".txt";//file name for encoding
		
		String fileFreq = mField.substring(0, len-1) + "F" + ".txt";//replace last letter with 'F' and add txt extension
		
		String dFile = mField.substring(0, len-1) + "D" + ".txt";//file name for decoding
		
		
		
		if(mField.charAt(len-1)=='P')
		{//ENCODE
		
			try {
	    	
		    	writer = new PrintWriter(cFile);//open new file that ends with 'C'
		    	int c = 0;//this variable is to store integer value for each letter read
				char cchar;
				
		    	for(;;)//to read all letters in file until the end
		    		{
		    		try {
		    			c = reader.read();//read letter by letter
		    			if(c == -1)//break upon EOF
		    				break;
	
		    			if(vigenere == false)//if mono cipher encoder
		    			{
		    				cchar = mcipher.encode((char) c);//encode character
		    				let.addChar(cchar);//
		    			}
		    			
		    			else//if vigenere encoder
		    			{
		    				cchar = vcipher.encode((char) c);
		    				let.addChar(cchar);
		    			}
		    			
		    			writer.print(cchar);
	    			
		    		} catch (IOException e) {
					// TODO Auto-generated catch block
					System.err.println("problem occured");;
		    			}//end of catch
		    		}//end of for
		    writer.close();//when all letters are encoded, save file.
		    
		    
		    //to write into frequency file
		    writer = new PrintWriter(fileFreq);//open file that ends with 'F'
		    writer.println(let.getReport());//pull frequency result from LetterFrequency class and print it to file
		        
		    writer.close();//to save content to frequency file
		}//end of try
		 catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "No file was found", 
			"File Not Found error", JOptionPane.ERROR_MESSAGE);
			}
		    return true;  
	    
	    
		}//END OF ENCODE
		
		else if(mField.charAt(len-1)=='C')
		{ //DECODE
			try {
		    	
		    	writer = new PrintWriter(dFile);//open file that ends with 'D'
		    	int c = 0;//to save integer value of each character 
				char cchar;
		    	for(;;)//to read all content until EOF
		    	{
		    		try {
		    			c = reader.read();//read letter by letter
		    			if(c == -1)
		    				break;
	    			
		    			if(vigenere == false)
		    			{
		    				cchar = mcipher.decode((char) c);//decode according to mono cipher
		    				let.addChar(cchar);
		    			}
		    			
		    			
		    			else
		    			{
		    				cchar = vcipher.decode((char) c);//decode according to vigenere
		    				let.addChar(cchar);
		    			}
		    			
		    			writer.print(cchar);//print to file
	    			
		    		} catch (IOException e) {
					// TODO Auto-generated catch block
					System.err.println("problem occured");;
		    		}//end of catch
		    	}//end of endless for
		    	writer.close();//to save content to D file
		    	
		    	//to generate frequency file
		    	 writer = new PrintWriter(fileFreq);//open file that ends with 'F'
				 writer.println(let.getReport());//pull frequency result and save it to file
				 
				 writer.close();//save content to frequency file
		 }//end of try
		 catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "No file was found", 
			"File Not Found error", JOptionPane.ERROR_MESSAGE);
			messageField.setText("");//clear field name
			}//end of catch
		    return true; 
		}//END OF DECODE
		
		else
			JOptionPane.showMessageDialog(null, "invalid file name", "Invalid File", JOptionPane.ERROR_MESSAGE);
		    return false;
	   
	}//end of process file method
}//end of class
