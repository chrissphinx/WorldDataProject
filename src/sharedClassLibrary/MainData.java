/* PROJECT: WorldDataProject (Java)         CLASS: MainData
 * AUTHOR: Colin MacCreery
 * FILES ACCESSED: MainData.bin
 * FILE STRUCTURE: Binary File
 * DESCRIPTION: 
 ******************************************************************************/

package sharedClassLibrary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MainData 
{
	/**************************** PRIVATE DECLARATIONS ************************/
    private File file;
    private RandomAccessFile ram;
    private RawDataRecord record;
    private static final int HEAD_SIZE = 2;
	private static final int REC_SIZE = 55;
   
    /**************************** PUBLIC CONSTRUCTOR(S) ***********************/
    public MainData() {
    	file = new File("MainData.bin");
    	try {
			file.createNewFile();
		} catch (IOException e) {}
    }

    /**************************** PUBLIC GET/SET METHODS **********************/
    public void write(int id, RawDataRecord record) {
    	try {
			ram.seek(0);
			ram.writeShort(id);
			ram.seek((id - 1) * REC_SIZE + HEAD_SIZE);
			ram.writeShort(id);
			ram.writeBytes(record.getCode());
			ram.writeBytes(String.format("%-15s", record.getName()));
			ram.writeBytes(String.format("%-13s", record.getContinent()));
			ram.writeInt(record.getSurfaceArea());
			ram.writeShort(record.getYearOfIndep());
			ram.writeLong(record.getPopulation());
			ram.writeFloat(record.getLifeExp());
			ram.writeInt(record.getGnp());
			ram.seek(HEAD_SIZE);
		} catch (IOException e) {}
	}
    
    public RawDataRecord read(int id) {
    	try {
			ram.seek((id - 1) * REC_SIZE + HEAD_SIZE);
			read();
			ram.seek(HEAD_SIZE);
		} catch (IOException e) {}
    	return record;
    }

    public RawDataRecord read() {
    	record = new RawDataRecord();
    	try {
    		ram.readShort();

			byte[] code = new byte[3];
			ram.read(code);
			record.setCode(new String(code));
			
			byte[] name = new byte[15];
			ram.read(name);
			record.setName(new String(name));
			
			byte[] continent = new byte[13];
			ram.read(continent);
			record.setContinent(new String(continent));
			
			record.setSurfaceArea(ram.readInt());
			record.setYearOfIndep(ram.readShort());
			record.setPopulation(ram.readLong());
			record.setLifeExp(ram.readFloat());
			record.setGnp(ram.readInt());
    	} catch (IOException e) {}
    	return record;
    }
    
    public void delete(String name) {
    	// TODO: Implement delete by record ID
    }

    /**************************** PUBLIC SERVICE METHODS **********************/
    public void open() {
    	try {
			ram = new RandomAccessFile(file, "rw");
		} catch (FileNotFoundException e) {}
    }
    
    public void close() {
    	try {
			ram.close();
		} catch (IOException e) {}
    }
}