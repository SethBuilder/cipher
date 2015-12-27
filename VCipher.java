/**
 * Programming AE2
 * Class contains Vigenere cipher and methods to encode and decode a character
 */
public class VCipher
{
	private char [] alphabet;   //the letters of the alphabet
	private final int SIZE = 26;
	private int countEn = 0;//to keep track of the order of character to be encoded
	private int countDe = 0;//to keep track of the order of character to be decoded
	private int len;//keyword length
	private char[][] vig;//Vigenere character array
	/** 
	 * The constructor generates the cipher
	 * @param keyword the cipher keyword
	 */
	public VCipher(String keyword)
	{
		
		//create alphabet
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char)('A' + i);
		
		//keyword length
		len = keyword.length();
		
	    //create Vigenere array with  number of rows equal to keyword length
		//and number of columns equal to the number of alphabet letters
	    vig = new char[len][SIZE];
	    
	    //initialize it with 0's
	    for(int i=0;i<len;i++)
	    	for(int j = 0; j<SIZE; j++)
	    		vig[i][j] = 0;
	    
	    //loop through all rows and adds letters until we reach 'Z'
	    for(int i = 0; i<len; i++)
	        {
		    	vig[i][0] = keyword.charAt(i);//fill first spot of each row with a letter from keyword
		    	int loc =  keyword.charAt(i) - 'A';//calculate position of each keyword letter on alphabet array
		    	
		    	//after filling first spot of each row, loop to all position of each row
		    	for(int j = 1; j<SIZE; j++)
		    		{
		    			if(j + loc < SIZE)//if we're still below 'Z'
		    				vig[i][j] = alphabet[j+loc];//keep adding letters after the letter from keyword
		    		}//end of j loop
	    	}//end of i loop
	    
	    //now to fill remaining spots on each row after 'Z'
	    for(int i = 0; i<len; i++)//loop through all rows
        	{
	    		for(int j = 1; j<SIZE; j++)//loop every row after the first letter(the one from the keyword)
	    			{
			    		if(vig[i][0] == 'Z')//special case - if the letter from the keyword is Z
			    			
			    			{
				    			int a = 0;//used to increment alphabet
				    			for(int x = 1; x < SIZE-1; x++)//go to all spots following 'Z' which is at the column 0
				    				{
					    				vig[i][x] = alphabet[0+a];//add letters starting with 'A' until the end
					    				a++;
				    				}
			    			}//end of if
			    		
			    		else if(vig[i][j] == 'Z')//this is for all other scenarios where 'Z' could come at a column other than the first one
			    			{
				    			int a = 0;//used to increment alphabet
				    			for(int x = j; x<SIZE-1; x++)
				    				{
					    				vig[i][x+1] = alphabet[0+a];//add letters starting with 'A'
					    				a++;
				    				}
			    			}//end of if
	    			}//end of j loop
        	}//end of i loop
	   
	    
	    //this is to print alphabet and Vigenere arrays
	    //ALPHABET
	    for(int i = 0; i<SIZE; i++)
			System.err.print(alphabet[i]);
	    System.err.println();
	    
	    //VIGENERE
	    for(int i=0;i<len;i++)
	    	{
		    	for(int j = 0; j<SIZE; j++)
		    			System.err.print(vig[i][j]);
		    	System.err.println();
	    	}
	    }//end of method
	 
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */	
	public char encode(char ch)
	{
		
		int isValid=0;//incremented when ch is an upper case letter, stays 0 if it is space or punctuation
		
		for(int i = 0; i<SIZE; i++)
			{
	            if( alphabet[i] == ch) 
	            	isValid++;
			}
		if(isValid==0)//when space or punctuation return as is
            return ch;
		
		else//when actual upper case letter then encode
			{
				int loc = ch - 'A';//calculate location on alphabet array - column
				
				//start with row 0,1,2..until end of array and then calculate row by 
				//order_of_character in the document % length of keyword
				ch = vig[countEn%len][loc];
				countEn++;//increment order for next character
				
			    return ch;  // return encoded character
			}
	}//end of encode method
	
	/**
	 * Decode a character
	 * @param ch the character to be decoded
	 * @return the decoded character
	 */  
	public char decode(char ch)
	{
		
		int isValid=0;//incremented when ch is an upper case letter, stays 0 if it is space or punctuation
		
		for(int i = 0; i<SIZE; i++)
			{
		        if( alphabet[i] == ch) 
		        	isValid++;
			}
				
		if(isValid==0)//when space or punctuation return as is
	            return ch;
		
		else//else return decoded character
			{
				//start with row 0 - find letter to be decoded on that row 
				//and return matching letter on alphabet array
				int i;
				for(i = 0; i < SIZE; i++)
					if(vig[countDe%len][i] == ch)//calculate which row we need by - order of character % length of keyword
						break;//stop when we find it
				countDe++;//increment counter for next letter
				
		    return alphabet[i];//return matching character on alphabet array
			}
	}//end of decode
}//end of class
