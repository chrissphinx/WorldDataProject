/* PROJECT:  WorldDataProject (Java)            PROGRAM: SetupProgram
 * AUTHOR: Colin MacCreery
 * OOP CLASSES USED (for Asgn 1):  RawData, UserInterface, NameIndex
 *      PLUS FOR FUTURE ASGN:  DataStorage, CodeIndex
 * FILES ACCESSED: (only INDIRECTLY through the OOP classes)
 *      INPUT:   RawData*.csv           (handled by RawData class)
 *      OUTPUT:  Log.txt                (handled by UserInterface class)
 *      OUTPUT:  NameIndexBackup*.txt   (handled by NameIndex class)
 *      PLUS FOR FUTURE ASGN:
 *          OUTPUT: MainData*.bin (& "INPUT" to check for "empty locations")
 *                                      (handled by DataStorage class)
 *          OUTPUT: CodeIndexBackup*.bin (handled by CodeIndex class)
 *          AND NameIndexBackup*.bin will be a BINARY file, not TEXT
 *      where * is the appropriate fileNameSuffix.
 * DESCRIPTION:  The program itself is just the CONTROLLER which UTILIZES
 *      the SERVICES (public methods) of various OOP classes.
 *      It creates an internal NameIndex from data in the RawData file,
 *      saving the index to a file to port it to UserApp.
 *      Status messages are sent to the Log file.
 *      PLUS FOR FUTURE ASGN:
 *          Creates a random access MainData file and an internal CodeIndex,
 *          again saving the index to a file to port it to UserApp.
 * CONTROLLER ALGORITHM:  Traditional sequential-stream processing - i.e., 
 *      loop until done with RawData
 *      {   input 1 data set from RawData
 *          use that data to construct an entry for NameIndex (calling
 *                  appropriate service method in that class)
 *      }
 *      finish up with RawData
 *      finish up with NameIndex
 *      PLUS FOR FUTURE ASGN: "use that data" would also include writing
 *          a MainData record and constructing an entry for CodeIndex
 *          (Similarly, finish up would need to. . .)
 * CAUTION:  The program code below DOES NOT DEAL DIRECTLY WITH
 *      RawData or NameIndex or Log.  Appropriate OOP classes handle those.   
 ******************************************************************************/

package setupProgram;

import sharedClassLibrary.NameIndex;
import sharedClassLibrary.RawData;
import sharedClassLibrary.UserInterface;
import sharedClassLibrary.RawDataRecord;
import sharedClassLibrary.MainData;
import java.text.DecimalFormat;

public class SetupProgram
{
    /**************************** PRIVATE DECLARATIONS ************************/
	private NameIndex NameIndex = new NameIndex();
	private RawData RawData;
	private UserInterface UserInterface = new UserInterface();
    private MainData MainData = new MainData();
    private DecimalFormat fmt = new DecimalFormat("000");
	
    /**************************** MAIN ****************************************/
    public SetupProgram(String[] args) {
        RawData = new RawData(args);
    	UserInterface.openLog();
    	UserInterface.log(">> opened Log FILE\n");
    	UserInterface.log(">> started SetupPogram\n");
    	RawData.open(); // log a bunch of crap
    	UserInterface.log(">> opened RawDataTester FILE\n");
    	NameIndex.open();
    	UserInterface.log(">> opened NameIndexBackup FILE\n");
    	MainData.open();
    	UserInterface.log(">> opened MainData FILE\n");

    	UserInterface.log(">> parsing file ...\n");
    	RawDataRecord record = null; int numItems = 0;
    	while((record = RawData.nextName()) != null) {
    		String[] result = NameIndex.insert(record.getName());
            MainData.write(Integer.parseInt(result[0]), record);
            RawDataRecord recRead = MainData.read(Integer.parseInt(result[0]));
            System.out.println(fmt.format(Integer.parseInt(result[0])) + " " + recRead);      
            numItems++;
    	}
    	UserInterface.log(">> finished!\n");

    	UserInterface.log(">> closed MainData FILE\n");
    	MainData.close();
        UserInterface.log(">> closed NameIndexBackup FILE\n");
        NameIndex.close();
        UserInterface.log(">> closed RawDataTester FILE\n");
        RawData.close(); // log a bunch of crap again
    	UserInterface.log(">> ended SetupPogram - " + numItems
    					  + " data items processed\n");
    	UserInterface.log(">> closed Log FILE\n\n");
    	UserInterface.closeLog();
    }
    
    public static void main(String[] args) {
    	new SetupProgram(args); // remove annoying non-static error msgs
    }
}