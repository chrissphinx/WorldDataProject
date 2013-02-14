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
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.EOFException;

public class PrettyPrintUtility 
{
	/**************************** PRIVATE DECLARATIONS ************************/
    private File file = new File("NameIndexBackup.bin");
    private PrintWriter writer;
    private DecimalFormat fmt = new DecimalFormat("'0'00;'-'00");
    private DataInputStream inFile;

	/**************************** MAIN ****************************************/
    public PrettyPrintUtility() {
    	try {
			inFile = new DataInputStream(new BufferedInputStream(new FileInputStream(file))); // open files
			writer = new PrintWriter(new FileWriter(new File("Log.txt"), true));
		} catch (IOException e) {}

        try {
        	writer.println("\nN is " + inFile.readShort() + ", MaxID is " + inFile.readShort()
        					   + ", RootPtr is " + inFile.readShort());
        	writer.println("[SUB]\t- - - Name - - - - - - - - -\tDRP\tLCh\tRCh");
        } catch (IOException e) {}

    	int i = 0;
        try {
        	try { // print each record
                while (true) {
            		String name = inFile.readUTF(); // cut names that are too long
            		if(name.length() > 28) name = name.substring(0, 28);

            		writer.print("[" + fmt.format(i++) + "]\t" + name);
            		for(int j = 0; j < 4 - (name.length() / 8); j++) {
            			writer.print("\t"); // print correct # of tabs
            		}
            		writer.println(fmt.format(inFile.readShort()) + "\t"
            					   + fmt.format(inFile.readShort()) + "\t"
            					   + fmt.format(inFile.readShort()));
                }
        	} catch (EOFException e) { inFile.close(); }
        } catch (IOException e) {}

    	writer.println("@ @ @ @ @ @ @ @ @ END OF FILE @ @ @ @ @ @ @ @\n");
    	writer.close(); // close files
    }
    
    public static void main(String[] args) {
    	new PrettyPrintUtility(); // remove annoying non-static error msgs
    }
}