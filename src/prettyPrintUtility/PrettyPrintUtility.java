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
import sharedClassLibrary.RawDataRecord;

public class PrettyPrintUtility 
{
	/**************************** PRIVATE DECLARATIONS ************************/
    private File names = new File("NameIndexBackup.bin");
    private File recs = new File("MainData.bin");
    private PrintWriter writer;
    private DecimalFormat fmt = new DecimalFormat("'0'00;'-'00");
    private DataInputStream namesIn;
    private DataInputStream recsIn;
    private RawDataRecord record;
    private int id;

	/**************************** MAIN ****************************************/
    public PrettyPrintUtility() {
    	try {
			writer = new PrintWriter(new FileWriter(new File("Log.txt"), true));
            writer.println(">> opened Log FILE");
            writer.println(">> started PrettyPrintUtility");
            namesIn = new DataInputStream(new BufferedInputStream(
                                          new FileInputStream(names)));
            writer.println(">> opened NameIndexBackup FILE");
            recsIn = new DataInputStream(new BufferedInputStream(
                                         new FileInputStream(recs)));
            writer.println(">> opened MainData FILE\n");
		} catch (IOException e) {}

        try {
            writer.println("MAIN DATA FILE");
            writer.println("N is " + recsIn.readShort());
            writer.println("RRN>ID CODE NAME----------- CONTINENT---- ------AREA"
                          + " INDEP ---POPULATION L.EX ------GNP");
            try {
                while (true) {
                    record = new RawDataRecord();

                    id = recsIn.readShort();
                    writer.print(fmt.format(id) + ">" + fmt.format(id) + " ");

                    byte[] code = new byte[3];
                    recsIn.read(code);
                    record.setCode(new String(code));
                    
                    byte[] name = new byte[15];
                    recsIn.read(name);
                    record.setName(new String(name));
                    
                    byte[] continent = new byte[13];
                    recsIn.read(continent);
                    record.setContinent(new String(continent));
                    
                    record.setSurfaceArea(recsIn.readInt());
                    record.setYearOfIndep(recsIn.readShort());
                    record.setPopulation(recsIn.readLong());
                    record.setLifeExp(recsIn.readFloat());
                    record.setGnp(recsIn.readInt());

                    writer.println(record);
                }
            } catch (EOFException e) {
                writer.println("@ @ @ @ @ @ @ @ @ END OF FILE @ @ @ @ @ @ @ @\n");
                writer.println(">> closed MainData FILE");
                recsIn.close();
            }
        } catch (IOException e) {}

        try {
            writer.println("\nNAME INDEX");
        	writer.println("N is " + namesIn.readShort() + ", MaxID is "
                           + namesIn.readShort() + ", RootPtr is "
                           + namesIn.readShort());
        	writer.println("[SUB]\t- - - Name - - - - - - - - -\tDRP\tLCh\tRCh");

            int i = 0;
        	try { // print each record
                while (true) {
            		String name = namesIn.readUTF(); // cut names that are too long
            		if(name.length() > 28) name = name.substring(0, 28);

            		writer.print("[" + fmt.format(i++) + "]\t" + name);
            		for(int j = 0; j < 4 - (name.length() / 8); j++) {
            			writer.print("\t"); // print correct # of tabs
            		}
            		writer.println(fmt.format(namesIn.readShort()) + "\t"
            					   + fmt.format(namesIn.readShort()) + "\t"
            					   + fmt.format(namesIn.readShort()));
                }
        	} catch (EOFException e) {
                writer.println("@ @ @ @ @ @ @ @ @ END OF FILE @ @ @ @ @ @ @ @\n");
                writer.println(">> closed NameIndexBackup FILE");
                namesIn.close();
            }
        } catch (IOException e) {}

        writer.println(">> ended PrettyPrintUtility");
        writer.println(">> closed Log FILE");
    	writer.close(); // close files
    }
    
    public static void main(String[] args) {
    	new PrettyPrintUtility(); // remove annoying non-static error msgs
    }
}