/**
 * Programming AE2
 * Contains monoalphabetic cipher and methods to encode and decode a character.
 */
public class MonoCipher
{
	/** The size of the alphabet. */
	private final int SIZE = 26;

	/** The alphabet. */
	private char [] alphabet;
	
	/** The cipher array. */
	private char [] cipher;
	
	int rep; // stays zero if letter is not mentioned in keyword
	/**
	 * Instantiates a new mono cipher.
	 * @param keyword the cipher keyword
	 */
	public MonoCipher(String keyword)
	{
		
		//fill alphabet array
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char)('A' + i);
			
		//create cipher array
		cipher = new char[SIZE];
		
		int len = keyword.length(); //keyword length
		
		//to copy keyword into the first section of cipher array
		for(int j = 0; j<len; j++)
			{cipher[j] = keyword.charAt(j);
			}
		
		//to copy rest of alphabet in the remaining section of cipher array
		//loops through all alphabet 
			for(int i = SIZE-1; i>=0; i--)
			{	
				//loops each letter through first section of cipher to check if it's there
				for(int x = 0; x<len; x++)
					
					if(alphabet[i] == cipher[x])
						
						rep++;//if it's there increment this variable
				    
					if(rep==0)//if not repeated add to second section of cipher
					{
					cipher[len] = alphabet[i];
					len++;
					}
					
					else
						rep = 0;
			}
			
			//to print alphabet and cipher arrays as requested in AE2 description 
			for(int i = 0; i<SIZE; i++)
				System.err.print(alphabet[i]);
			System.err.println();
			
			for(int i = 0; i<SIZE; i++)
				System.err.print(cipher[i]);
			System.err.println();

		
		// create first part of cipher from keyword
		// create remainder of cipher from the remaining characters of the alphabet
		// print cipher array for testing and tutors
	}
	
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */
	public char encode(char ch)
	{
		int loc;//location of letter on alphabet array
		int isValid=0;//incremented when ch is an upper case letter, stays 0 if it is space or punctuation
		
		for(int i = 0; i<SIZE; i++)
			{
            if( alphabet[i] == ch) 
            	isValid++;
			}
		if(isValid==0)//when space or punctuation return as is
            return ch;
            
            
		else//else, return encoded letter
			{
		
			  loc =  ch - 'A';
			  return cipher[loc];
			}//end of else 
	}//end of encode

	/**
	 * Decode a character
	 * @param ch the character to be encoded
	 * @return the decoded character
	 */
	public char decode(char ch)
	{
		int isValid=0;//incremented when ch is an upper case letter, stays 0 if it is space or punctuation
		int foundAt = 0;
		
		for(int i = 0; i<SIZE; i++)
		{
            if( alphabet[i] == ch) 
            	isValid++;
            	
		}
		if(isValid==0)//when space or punctuation return as is
            return ch;
            
            
		else//else, return decoded letter letter
			{
			for(int i = 0; i<SIZE; i++)
				{
				if(cipher[i] ==ch)//find where letter is located in cipher and save it's place
					{
						foundAt = i;
						break;
					}
				}
			return alphabet[foundAt];//return equivalent letter in alphabet 
	
			}//end of else 
		}//end of decode
}//end of class
