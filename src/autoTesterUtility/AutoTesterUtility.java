/* PROJECT:  WorldDataProject (Java)            PROGRAM: AutoTesterUtility
 * AUTHOR:  Kaminski/CS3310
 * PROGRAMS ACCESSED:  SetupProgram, UserApp, PrettyPrintUtility
 * OOP CLASSES USED:  none - this program is just a developer utility program
 *      and doesn't use the OOP paradigm
 * FILES ACCESSED:  4 output files from this project (using suffix for *):
 *      Log.txt, MainData*.bin, NameIndexBackup*.bin, codeIndexBackup*.bin
 * DESCRIPTION:  Utility program which aids developer to automate testing of the
 *      project with various test data sets.  It deletes output files from
 *      previous runs and executes the 3 programs with various test files
 *      (and N's) as parameters when calling those programs' Main methods.
 ******************************************************************************/
package autoTesterUtility;

import java.io.File;
import setupProgram.SetupProgram;
import userApp.UserApp;
import prettyPrintUtility.PrettyPrintUtility;

public class AutoTesterUtility {

    public static void main(String[] args) {
        SetupProgram.main(new String[] {});
        PrettyPrintUtility.main(new String[] {});
        UserApp.main(new String[] {});
        PrettyPrintUtility.main(new String[] {});

        // System.out.println("OK, starting AutoTesterUtilty");

        // // The 3 parallel arrays (all strings, including the N's) with
        // //      - hard-coded SUFFIX values to designate which files to use
        // //      - N's to limit how many records to display during testing
        // // The dataFileSuffix is used for RawData*.csv, MainData*.bin,
        // //      NameIndexBackup*.bin, CodeIndexBackup*.bin

        // String dataFileSuffix[]  = {"Tester", ""    };
        // String transFileSuffix[] = {"",       "All" };
        // String nRecToShow[]      = {"All",    "60"  };

        // //Delete the SINGLE output Log.txt file (if it exists)
        // DeleteFile("Log.txt");

        // for (int i = 0; i < dataFileSuffix.length; i++) {
        //     //Delete 3 other output files (if they exist)
        //     DeleteFile("MainData" + dataFileSuffix[i] + ".bin");
        //     DeleteFile("NameIndexBackup" + dataFileSuffix[i] + ".bin");
        //     DeleteFile("NameIndexBackup" + dataFileSuffix[i] + ".bin");
            
        //     //Run the other 3 programs
        //     SetupProgram.main(new String[]{dataFileSuffix[i]});
        //     UserApp.main(new String[]{dataFileSuffix[i],
        //                 transFileSuffix[i]});
        //     PrettyPrintUtility.main(new String[]{dataFileSuffix[i],
        //                 nRecToShow[i]});
        // }
    }
    //**************************** PRIVATE METHOD ******************************
    private static boolean DeleteFile(String fileName) {
        boolean delete = false;
        File f = new File(fileName);
        if (f.exists()) {
            delete = f.delete();
        }
        return delete;
    }  
}
