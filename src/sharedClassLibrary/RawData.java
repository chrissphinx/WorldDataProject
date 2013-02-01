/* PROJECT: WorldDataProject (Java)         CLASS: RawData
 * AUTHOR: Colin MacCreery
 * FILES ACCESSED: RawDataTester.csv
 * FILE STRUCTURE: Raw SQL CSV File
 * DESCRIPTION: Provides methods to open and close the RawDataTester.csv
 * 				file. Also provides a nextName() method to return the
 * 				next country's name or null if the end of the file has
 * 				been reached.
 ******************************************************************************/

package sharedClassLibrary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class RawData extends Parser
{
    /**************************** PRIVATE DECLARATIONS ************************/
	private File file;
	private BufferedReader buffer;
    
    /**************************** PUBLIC CONSTRUCTOR(S) ***********************/
	public RawData() {
		file = new File("RawDataTester.csv");
	}

	/**************************** PUBLIC GET/SET METHODS **********************/
	public String nextName() {
		String line = ""; String name = "";

		try {
			if ((line = buffer.readLine()) != null) {
				String[] data = parse(line); // from Parser class
				name = data[1]; // name is in second position
			} else {
				name = null; // return null to calling class
			}
		} catch (IOException e) {}
 
		return name;
	}

    /**************************** PUBLIC SERVICE METHODS **********************/
	public void open() {
		try {
			buffer = new BufferedReader(new FileReader(file));
			buffer.readLine(); // eat first line
		} catch(IOException e) {}
	}

	public void close() {
		try {
			buffer.close();
		} catch (IOException e) {}
	}
}