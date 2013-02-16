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

public class AutoTesterUtility
{
    /**************************** MAIN ****************************************/
    public static void main(String[] args) {
        deleteFile("Log.txt");
        deleteFile("MainData.bin");
        deleteFile("NameIndexBackup.bin");

        SetupProgram.main(new String[] { "Tester" });
        PrettyPrintUtility.main(new String[] {});
        for (int i = 1; i < 4; i++) {
            UserApp.main(new String[] { Integer.toString(i) });
        }
        PrettyPrintUtility.main(new String[] {});
    }

    /**************************** PRIVATE METHOD ******************************/
    private static boolean deleteFile(String file) {
        boolean delete = false;
        File f = new File(file);
        if (f.exists()) {
            delete = f.delete();
        }
        return delete;
    }  
}
