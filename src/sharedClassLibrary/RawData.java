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

public class RawData
{
    /**************************** PRIVATE DECLARATIONS ************************/
	private File file;
	private BufferedReader buffer;
	private RawDataRecord data = new RawDataRecord();
    
    /**************************** PUBLIC CONSTRUCTOR(S) ***********************/
	public RawData() {
		file = new File("RawData.csv");
	}

	public RawData(String[] suffix) { // user provided file suffix
		file = new File("RawData" + suffix[0] + ".csv");
	}

	/**************************** PUBLIC GET/SET METHODS **********************/
	public RawDataRecord nextName() {
		String line = ""; RawDataRecord record = null;

		try {
			if ((line = buffer.readLine()) != null) {
				data.parse(line); // parse input
				record = data.getRecord(); // get record from RawDataRecord
			} else {
				record = null; // return null to calling class
			}
		} catch (IOException e) {}
 		// this method now returns a RawDataRecord object
		return record;
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
			buffer.close(); // close the file
		} catch (IOException e) {}
	}
}