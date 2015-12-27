import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Programming AE2
 * Processes report on letter frequencies
 */
public class LetterFrequencies
{
	/** Size of the alphabet */
	private final int SIZE = 26;
	
	/** Count for each letter */
	private int [] alphabetCounts;
	
	/** The alphabet */
	private char [] alphabet; 
												 	
	/** Average frequency counts */
	private double [] avgCounts = {8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0,
							       0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0,  
								   6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1};

	/** Character that occurs most frequently */
	private char maxCh;

	/** Total number of characters encrypted/decrypted */
	private int totChars;
	
	/**FileReader object to read C or D files*/
	private FileReader reader;
	
	/** the string that will hold the report */
	private String freq;
	
	/**
	 * Instantiates a new letterFrequencies object.
	 */
	public LetterFrequencies()
	{
		//initialize alphabet array with letters
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char)('A' + i);
		
		//create alphabetCounts array and initialize to 0's
		alphabetCounts = new int[alphabet.length];
		for(int j = 0; j<alphabet.length; j++)
			alphabetCounts[j] = 0;
		
		freq = "";//initialize string to empty
		
		totChars = 0;//initialize to 0
	}
		
	/**
	 * Increases frequency details for given character
	 * @param ch the character just read
	 */
	public void addChar(char ch)
	{
		for(int i = 0; i<alphabet.length; i++)
		{
			if(alphabet[i] == (char) ch)
			alphabetCounts[i]++;
		}
	}
	
	/**
	 * Gets the maximum frequency (double)
	 * @return the maximum frequency
	 */
	private double getMaxPC()
     {
		double max = (double)alphabetCounts[0] / totChars;//assume A has the most frequency
		
		for(int i = 0; i< alphabet.length; i++)//go through all alphabet
		{
			if((double)alphabetCounts[i] / totChars >= max)//compare to max
			{
				max = (double)alphabetCounts[i] / totChars;
			}
		}
	    return max;  // return max frequency amount(double)
	}
	/**
	 * Gets the maximum frequency (character)
	 * @return the maximum frequency character
	 */
	private char getMaxCh()
    {
	double max = (double)alphabetCounts[0] / totChars;//assume A has the most frequency
	
	for(int i = 0; i<alphabet.length; i++)//go through all alphabet
	{
		if((double)alphabetCounts[i] / totChars >= max)//compare to max
		{
			max = (double)alphabetCounts[i] / totChars;
			maxCh = alphabet[i];
		}
	}
    return maxCh;  // return max frequency letter(char)
}
	
	
	/**
	 * Returns a String consisting of the full frequency report
	 * @return the report
	 */
	public String getReport()
	{
		
				
				
				//to calculate totChars
				for(int v = 0; v<alphabet.length; v++)
					
					if(alphabetCounts[v] != 0)
						totChars++;
				
				//TO GENERATE FREQ STRING
				for(int x = 0; x<alphabet.length; x++)
				
					freq += alphabet[x] + "\t\t" + alphabetCounts[x] + "\t\t"+ String.format("%.2f", (double)alphabetCounts[x] / totChars) 
					
					+ "\t\t" + avgCounts[x]
					
					+ "\t\t" + String.format("   %05.2f", (double)alphabetCounts[x] / totChars - avgCounts[x])
					
					
					+ "\n";
			
		
		
		//return string for file's data
	    return "Letter" + " \t" + "Freq" +  "\t" + "Freq%" + "\t\t" 
	    
	    	   + "AvgFreq%" + " \t" + "Diff"+ "\n" + freq
	    	   
	    	   +"\n" + "The most frequent letter is " + getMaxCh() + " at " + String.format("%.2f", getMaxPC());
	    	   
	    
	    
	}//end of getReport method
}//end of class
