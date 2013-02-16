/* PROJECT: WorldDataProject (Java)         CLASS: UserInterface
 * AUTHOR: Colin MacCreery
 * FILES ACCESSED: TransData.txt Log.txt
 * DESCRIPTION: Provides methods to open and close both the TransData.txt
 * 				and Log.txt files. Simple log() method to print to log file
 * 				and nextTrans() method to fetch the next transaction request
 * 				from the TransData.txt file if there is another one.
 ******************************************************************************/

package sharedClassLibrary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class UserInterface
{
    /**************************** PRIVATE DECLARATIONS ************************/
	private File transFile, logFile;
    private BufferedReader buffer;
    private PrintWriter writer;

    /**************************** PUBLIC CONSTRUCTOR(S) ***********************/
    public UserInterface() {
    	logFile = new File("Log.txt");
    	transFile = new File("TransData.txt");
    	try {
			logFile.createNewFile();
		} catch (IOException e) {}
    }
    
    /**************************** PUBLIC GET/SET METHODS **********************/
    public String[] nextTrans() {
    	String line = ""; String[] data = new String[2];

		try {
			if ((line = buffer.readLine()) != null) {
				data[0] = line.substring(0, 2); // scrub input
				data[1] = line.substring(2, line.length()).trim();
			} else {
				data = null; // return null to calling class
			}
		} catch (IOException e) {}
 
		return data;
    }
    
    public void log(String data) {
    	writer.print(data);
    }
    
    /**************************** PUBLIC SERVICE METHODS **********************/
    public void openLog() {
    	try {
			writer = new PrintWriter(new FileWriter(logFile, true));
		} catch (IOException e) {}
    }
    
    public void openTrans() {
    	try {
			buffer = new BufferedReader(new FileReader(transFile));
		} catch(IOException e) {}
    }

    public void openTrans(File file) {
        try {
            buffer = new BufferedReader(new FileReader(file));
        } catch(IOException e) {}
    }
    
    public void closeLog() {
    	writer.close();
    }
    
    public void closeTrans() {
    	try {
			buffer.close();
		} catch (IOException e) {}
    }
}