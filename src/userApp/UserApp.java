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
import sharedClassLibrary.MainData;
import java.io.File;
import java.text.DecimalFormat;

public class UserApp
{
	/**************************** PRIVATE DECLARATIONS ************************/
	private NameIndex NameIndex = new NameIndex();
	private UserInterface UserInterface = new UserInterface();
    private RawDataRecord record;
    private MainData MainData = new MainData();
    private DecimalFormat fmt = new DecimalFormat("000");

	/**************************** MAIN ****************************************/
    public UserApp(String[] args) {
        for (String n : args) {
        	UserInterface.openLog();
        	UserInterface.log("\n>> opened Log FILE\n");
        	UserInterface.log(">> started UserApp\n");
        	NameIndex.open(); // log a bunch of crap
        	UserInterface.log(">> opened NameIndexBackup FILE\n");
        	MainData.open();
        	UserInterface.log(">> opened MainData FILE\n");
        	UserInterface.openTrans(new File("TransData" + n + ".txt"));
        	UserInterface.log(">> opened TransData FILE\n");

        	UserInterface.log(">> parsing file ...\n\n");
        	String[] trans = new String[2]; // locals for parsing
        	String[] result; int numItems = 0;
            int id;
        	while((trans = UserInterface.nextTrans()) != null) {
        		numItems++; // increment processed items and echo request
        		if(!"IN".equals(trans[0]))
        			UserInterface.log(trans[0] + " " + trans[1] + "\n");

        		switch(trans[0]) { // check request type
        		case "QN":			// query by name
        			UserInterface.log("  ");
        			id = NameIndex.search(trans[1]);
        			if(id != -1) {
        				UserInterface.log(fmt.format(id) + " "
                                          + MainData.read(id).toString() + "\n");
        			} else UserInterface.log("ERROR, not a valid country name\n");
        			break;
                case "QI":          // query by id
                    UserInterface.log("  ");
                    id = Integer.parseInt(trans[1]);
                    if ((record = MainData.read(id)).getCode() != null) {
                        UserInterface.log(fmt.format(id) + " " + record + "\n");
                    } else UserInterface.log("ERROR, not a valid country ID\n");
                    break;
        		case "LN":			// list alphabetically by name
                    UserInterface.log("  ID CODE NAME----------- CONTINENT---- "
                                      + "------AREA INDEP ---POPULATION L.EX "
                                      + "------GNP\n");
        			for(int e : NameIndex.getAlphabetical()) {
        				UserInterface.log("  " + fmt.format(e) + " "
                                          + MainData.read(e) + "\n");
                    }
        			break;
                case "LI":          // list by id
                    UserInterface.log("  ID CODE NAME----------- CONTINENT---- "
                                      + "------AREA INDEP ---POPULATION L.EX "
                                      + "------GNP\n");
                    int i = 1;
                    while ((record = MainData.read()).getCode() != null) {
                        UserInterface.log("  " + fmt.format(i++) + " "
                                          + record + "\n");
                    }
                    break;
        		case "IN":			// insert a new country
                    record = new RawDataRecord();
        			record.parse(trans[1]); // from Parser class
        			result = NameIndex.insert(record.getName());
                    MainData.write(Integer.parseInt(result[0]), record);
                    UserInterface.log("IN " + record.getName() + "\n");
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
                case "DI":          // delete by id
                    UserInterface.log("  SORRY, deleting by ID"
                                      + " not yet operational\n");
                    break;
        		}
        	}
        	UserInterface.log("@ @ @ @ @ @ @ @ @ THE END @ @ @ @ @ @ @ @\n");

        	UserInterface.log("\n>> closed TransData FILE\n");
        	UserInterface.closeTrans();
        	UserInterface.log(">> closed MainData FILE\n");
        	MainData.close();
        	UserInterface.log(">> closed NameIndexBackup FILE\n");
        	NameIndex.close(); // log a bunch of crap again
        	UserInterface.log(">> ended UserApp - " + numItems
        					  + " transactions processed\n");
        	UserInterface.log(">> closed Log FILE\n\n");
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