/* PROJECT: WorldDataProject (Java)         PROGRAM: UserApp
 * AUTHOR: Colin MacCreery
 * OOP CLASSES USED (for Asgn 1):  UserInterface, NameIndex
 *      PLUS FOR FUTURE ASGN:  DataStorage, CodeIndex
 * FILES ACCESSED: (only INDIRECTLY through the OOP classes)
 *      INPUT:   TransData*.txt         (handled by UserInterface class)
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
 *      It processes the transaction requests in TransData file, using
 *      NameIndex to facilitate efficient processing.  It sends the request
 *      and the result to the Log file.  [NameIndex will initially need to
 *      be loaded from the backup file back into the internal structure].
 *      creates an internal NameIndex from data in the RawData file,
 *      PLUS FOR FUTURE ASGN:
 *          The ACTUAL MainData will be provided to user (in Log file)
 *          rather than just the DataRecordPtr.  Also, transaction requests
 *          based on Id and Code are handled using CodeIndex and the random
 *          access MainData file.
 * CONTROLLER ALGORITHM:  Traditional sequential-stream processing - i.e., 
 *      loop til done with TransData
 *      {   input 1 transaction request from TransData
 *          switch to use that data to call appropriate service in NameIndex
 *                  class to handle request
 *      }
 *      finish up with TransData
 *      finish up with NameIndex
 *      PLUS FOR FUTURE ASGN: expanded range of services to handled additional
 *          Id and Code based requests
 *          (Similarly, finish up would need to. . .)
 * CAUTION:  The program code below DOES NOT DEAL DIRECTLY WITH
 *      TransData or NameIndex or Log.  Appropriate OOP classes handle those.  
 ******************************************************************************/

package userApp;

import sharedClassLibrary.NameIndex;
import sharedClassLibrary.RawDataRecord;
import sharedClassLibrary.UserInterface;
import java.io.File;

public class UserApp
{
	/**************************** PRIVATE DECLARATIONS ************************/
	private NameIndex NameIndex = new NameIndex();
	private UserInterface UserInterface = new UserInterface();
    private RawDataRecord record = new RawDataRecord();

	/**************************** MAIN ****************************************/
    public UserApp(String[] args) {
        for (String n : args) {
        	UserInterface.openLog();
        	UserInterface.log(">> opened Log FILE\n");
        	UserInterface.log(">> started UserApp\n");
        	NameIndex.open(); // log a bunch of crap
        	UserInterface.log(">> opened NameIndexBackup FILE\n");
        	UserInterface.openTrans(new File("TransData" + n + ".txt"));
        	UserInterface.log(">> opened TransData FILE\n");

        	UserInterface.log(">> parsing file ...\n\n");
        	String[] trans = new String[2]; // locals for parsing
        	String[] result; int numItems = 0;
        	while((trans = UserInterface.nextTrans()) != null) {
        		numItems++; // increment processed items and echo request
        		if(!"IN".equals(trans[0]))
        			UserInterface.log(trans[0] + " " + trans[1] + "\n");

        		switch(trans[0]) { // check request type
        		case "QN":			// query by name
        			UserInterface.log("  ");
        			result = NameIndex.search(trans[1]);
        			if(!"".equals(result[0])) {
        				UserInterface.log(result[0]);
        			} else UserInterface.log("ERROR, not a valid country name");
        			UserInterface.log(" >> " + result[1] + " nodes visited\n");
        			break;
        		case "LN":			// list alphabetically by name
        			for(String[] e : NameIndex.getAlphabetical()) {
        				UserInterface.log("  " + e[0] + " " + e[1] + "\n");
        			}
        			break;
        		case "IN":			// insert a new country
        			record.parse(trans[1]); // from Parser class
        			result = NameIndex.insert(record.getName());
                    UserInterface.log("IN " + record.getName() + "\n"); // scrub request
                    if("-1".equals(result[0])) {
                        UserInterface.log("  ERROR, redundant entry >> " + result[1]
                                          + " nodes visited\n");
                    } else {
                        UserInterface.log("  OK, inserted with ID " + result[0]
                                      + " >> " + result[1] + " nodes visited\n");
                    }
        			break;
        		case "DN":			// delete by name
        			UserInterface.log("  SORRY, deleting by name"
        							  + " not yet operational\n");
        			break;
        		}
        	}
        	UserInterface.log("@ @ @ @ @ @ @ @ @ THE END @ @ @ @ @ @ @ @\n");

        	UserInterface.log("\n>> closed TransData FILE\n");
        	UserInterface.closeTrans();
        	UserInterface.log(">> closed NameIndexBackup FILE\n");
        	NameIndex.close(); // log a bunch of crap again
        	UserInterface.log(">> ended UserApp - " + numItems
        					  + " transactions processed\n");
        	UserInterface.log(">> closed Log FILE\n");
        	UserInterface.closeLog();
        }
    }
    
    public UserApp() {
        this(null);
    }

    public static void main(String[] args) {
        if (args == null) {
            new UserApp();
        } else new UserApp(args); // remove annoying non-static error msgs
    }
}