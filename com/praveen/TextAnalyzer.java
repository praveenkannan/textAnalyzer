package com.praveen;

/**
* All the following imports are for getting input 
* from a file
*/
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.io.FileNotFoundException;

 /**
 * A text analyzer that processes text and provides information about
 * its word contents. One thing it should be able to support is the 
 * ability to create a report that shows a count of how many times 
 * each word occurs in the text. The report should be sorted, with a 
 * primary sort of word length, and a secondary ASCII sort.
 *
 */
public class TextAnalyzer {
 
    /**
     * This is a override function for the sort to follow the given constraint
     * 
     * Constraint:
     * ==========
     * The report should be sorted, with a primary sort of word length, and a 
     * secondary ASCII sort.
     *
     * @param string1 String first word.
     * @param string2 String second word.
     * 
     * @return output of comparison between two words.
     */
    public static int compareString(String string1, String string2) 
    {
        if (string1 == string2) 
            return 0;
        if (string1 == null && string2 != null) 
            return 1;
        if (string1 != null && string2 == null) 
            return -1;
        
        int wordLengthDiff = string1.length() - string2.length();
        
        if (wordLengthDiff == 0) 
            return string1.compareTo(string2);
        
        return wordLengthDiff;
    }
    
    private static void merge(String[] data, int first, int n1, int n2)
    {
        String[] temp = new String[n1 + n2];
        int copied = 0;
        int copied1 = 0;
        int copied2 = 0;

        while ((copied1 < n1) && (copied2 < n2))
        {
            if (compareString(data[first + copied1],(data[first + n1 + copied2])) < 0)
                temp[copied++] = data[first + (copied1++)];
            else
                temp[copied++] = data[first + n1 + (copied2++)];
        }

        while (copied1 < n1)
            temp[copied++] = data[first + (copied1++)];
        while (copied2 < n2)
            temp[copied++] = data[first + n1 + (copied2++)];

        for (int i = 0; i < copied; i++)
            data[first + i] = temp[i];
    }
    
    public static void mergesort(String[] data, int first, int last) 
    {
	    int n1 = 0;
	    int n2 = 0;
	
	    if(last > 1) 
	    {
	        n1 = last/2;
	        n2 = last-n1;
	
	        mergesort(data, first, n1);
	        mergesort(data, first + n1, n2);
	    }
	
	    merge(data, first, n1, n2);
	}
 
	/**
	* Read data from file
	*/
	public static String readFile(String path, Charset encoding) 
	  throws IOException 
	{
	  byte[] encoded = Files.readAllBytes(Paths.get(path));
	  return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}
 
    /**
     * Text analyzer that processes text and provides information about its word contents.
     * The implementation assumes that the strings to be analyzed are passed
     *   as separate string array arguments as in {@code "Hello" "World"}
     *   instead of single string {@code "Hello World"}.
     *
     *  Standard usage example:
     *  <pre class="code">
     *      javac -d . TextAnalyzer.java
	 *		java -classpath . com.praveen.TextAnalyzer SmallFile.txt
     *  </pre>
     */
    public static void main(String[] fileName) 
    {
        if (fileName == null || fileName.length == 0) 
        {
            System.out.println("There were no input file passed");
            return;
        }        
                        
        try
        {
        	/**
        	* Read the contents of the file passed and store the contents into a string array
        	*/
	       	String words[] = readFile(fileName[0] , Charset.defaultCharset()).split(" |\n|\r");      
	        
			// Sorting the string array
	        // Complexity: O(n log n)
	        mergesort(words, 0, words.length);
	        
	        /**
	         *  Find the number of occurrences of every word.
	         *  
	         *  Notes on Design:
	         *  ===============
	         *  
	         *  1] This could be done better if we have options of using
	         *  collections, specifically using hashMap for storing count
	         *  But, the problem statement specifies not to use collections
	         *  so we will try alternatives.
	         *
	         *  2] The following loop would be computationally intensive 
	         *  as one has to loop through the entire document for each 
	         *  string to get count. There is no other alternative to this
	         *  as one has to make compromise on storage to gain performance
	         *  which in this case I would like assume that storage is very 
	         *  expensive particularly when one has loads several million 
	         *  records of data its better not to store them to make count.
	         *  
	         *  Complexity: O(n)
	         */
			System.out.println("Output");
			System.out.println("======");
	        int iter=0;
	        for (; iter < (words.length -1) ; iter++) 
	        {
	        	// initialize wordCount for current word: word[iter]
	            int wordCount = 1;
	            
	            // Check for the number of occurrences of current word: word[iter]
	            while (iter < (words.length -1) &&
	            				//speed fast for all repeat words 
	                		   ((words[iter] != null && words[iter].equals(words[iter + 1])) ||
						   			(words[iter] == null && words[iter + 1] == null))) 
			    {
	                wordCount ++;
	                iter ++;
	            }
	            
	            // Print count for current word
	            System.out.println(wordCount + " " + words[iter]);
	        }
	        
	        // At this stage, the index can have a minimum value of words.length - 1
	        // and the maximum value of words.length.
	        if (iter < words.length) 
	        {
	            System.out.println(1  + " " + words[iter]);
	        }
	    }
        catch(FileNotFoundException fnfe)
	    {
	
	        System.out.println(fnfe.getMessage());
	
	    }
	    catch(Exception ge)
	    {
	
	        System.out.println(ge.getMessage());
	
	    }
    }
}
