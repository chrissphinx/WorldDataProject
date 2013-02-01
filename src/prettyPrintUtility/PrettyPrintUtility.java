/* PROJECT: WorldDataProject (Java)         PROGRAM: PrettyPrintUtility
 * AUTHOR: Colin MacCreery
 * OOP CLASSES USED:  none (this does not use the OOP paradigm)
 * FILES ACCESSED:  (all files handled DIRECTLY by THIS program)
 *      INPUT:   NameIndexBackup*.txt 
 *      OUTPUT:  Log.txt
 *      PLUS FOR FUTURE ASGN:
 *          INPUT: MainData*.bin (& "INPUT" to check for "empty locations")
 *          OUTPUT: CodeIndexBackup*.bin
 *          AND NameIndexBackup*.bin will be a BINARY file, not TEXT
 *      where * is the appropriate fileNameSuffix.
 * DESCRIPTION:  This is a utility program for the developer.  As such, it's
 *      just a quick non-OOP program which accesses the files DIRECTLY.
 *      It pretty-prints (SEE SPECS) the NameIndexBackup file to the Log file.
 *      PLUS FOR FUTURE ASGN:
 *          Also prints MainData*.bin file and CodeIndexBackup*.bin file.
 *          AND, NameIndexBackup*.bin will be a BINARY file. 
 * CONTROLLER ALGORITHM:  Traditional sequential file processing . . .
 ******************************************************************************/

package prettyPrintUtility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;

public class PrettyPrintUtility 
{
	/**************************** PRIVATE DECLARATIONS ************************/
    private File file = new File("NameIndexBackup.txt");
    private PrintWriter writer;
    private DecimalFormat fmt = new DecimalFormat("'0'00;'-'00");
    private Scanner in;

	/**************************** MAIN ****************************************/
    public PrettyPrintUtility() {
    	try {
			in = new Scanner(file); // open files
			writer = new PrintWriter(new FileWriter(new File("Log.txt"), true));
		} catch (IOException e) {}

    	in.useDelimiter(",|\\n"); // delimit input, print header
    	writer.println("\nN is " + in.next() + ", MaxID is " + in.next()
    					   + ", RootPtr is " + in.next());
    	writer.println("[SUB]\t- - - Name - - - - - - - - -\tDRP\tLCh\tRCh");

    	int i = 0;
    	do { // print each record
    		String name = in.next(); // cut names that are too long
    		if(name.length() > 28) name = name.substring(0, 28);

    		writer.print("[" + fmt.format(i++) + "]\t" + name);
    		for(int j = 0; j < 4 - (name.length() / 8); j++) {
    			writer.print("\t"); // print correct # of tabs
    		}
    		writer.println(fmt.format(in.nextInt()) + "\t"
    					   + fmt.format(in.nextInt()) + "\t"
    					   + fmt.format(in.nextInt()));
    	} while(in.hasNext());

    	writer.println("@ @ @ @ @ @ @ @ @ END OF FILE @ @ @ @ @ @ @ @");
    	writer.close(); // close files
    	in.close();
    }
    
    public static void main(String[] args) {
    	new PrettyPrintUtility(); // remove annoying non-static error msgs
    }
}