/* PROJECT: WorldDataProject (Java)         CLASS: NameIndex
 * AUTHOR: Colin MacCreery
 * FILES ACCESSED: NameIndexBackup.txt
 * INTERNAL INDEX STRUCTURE: ArrayList
 * FILE STRUCTURE: Text CSV File
 * DESCRIPTION: Provides methods to open and close the NameIndexBackup.txt
 * 				file as well as all insert/search/alphabetize logic for the
 * 				binary search tree. Header present for deletion by name
 * 				but not yet implemented. Returns for insert/search/alphabetize
 * 				methods are terrible, but multiple pieces of data are
 * 				required by UserApp so it is able to log to the file correctly.
 * 				If this class was allowed to use a UserInterface object this
 * 				code would look a lot cleaner.
 * 
 * 				All three methods are recursive and initiated by a public
 * 				method, but the recursion is done by a private method with the
 * 				same name.
 ******************************************************************************/

package sharedClassLibrary;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class NameIndex 
{
    /**************************** PRIVATE DECLARATIONS ************************/
    private File file;
    private BufferedReader buffer;
    private PrintWriter writer;
    private ArrayList<Country> countries;
    private DecimalFormat fmt = new DecimalFormat("000");
    private int visited;
    private ArrayList<Country> alphaList;
   
    /**************************** PUBLIC CONSTRUCTOR(S) ***********************/
    public NameIndex() {
    	file = new File("NameIndexBackup.txt");
    	try {
			file.createNewFile();
		} catch (IOException e) {}
		countries = new ArrayList<Country>();
    }

    /**************************** PUBLIC GET/SET METHODS **********************/
    public String[] insert(String name) {
    	if(countries.isEmpty()) { // if no entries yet, insert first one
    		countries.add(new Country(name, countries.size() + 1));
    		return new String[] {Integer.toString(countries.size() + 1), "0"};
    	} else {
    		visited = 0; // method must return inserted country ID and nodes visited
    		return insert(name, 0);
    	}
    }
    
    public String[] search(String name) {
    	if(countries.isEmpty()) return new String[] {"", "0"};
    	visited = 0; // method must return name & id as well as nodes visited
    	name = name.toLowerCase(); // need uniform formatting for string comparison
    	return search(name, 0);
    }
    
    public String[][] getAlphabetical() {
    	String[][] results = new String[countries.size()][2];
    	alphaList = new ArrayList<Country>();
    	getAlphabetical(0); // method returns id and name in a 2D array
    	
    	int i = 0; // but need to use temporary ArrayList to alphabetize
    	for(Country e : alphaList) {
    		results[i][0] = fmt.format(e.getId());
    		results[i++][1] = e.getName();
    	}
    	
    	return results;
    }
    
    public void delete(String name) {
    	// TODO: Implement delete by country name
    }

    /**************************** PUBLIC SERVICE METHODS **********************/
    public void open() {
    	String line = ""; String[] data = {};

		try { // open NameIndexBackup and, if data present, load it
			buffer = new BufferedReader(new FileReader(file));
			buffer.readLine();
			while ((line = buffer.readLine()) != null) {
				data = line.split(",");
				countries.add(new Country(data[0], Integer.parseInt(data[1]),
							  Integer.parseInt(data[2]),
							  Integer.parseInt(data[3])));
			}
			buffer.close();
		} catch (IOException e) {}
    }
    
    public void close() {
		try { // write out contents of countries ArrayList before closing
			writer = new PrintWriter(new FileWriter(file));
		} catch (IOException e) {}

    	writer.println(countries.size() + "," + countries.size() + "," + "0");
    	for(Country e : countries) { // print header and then each entry
    		writer.println(e.getName() + "," + e.getId() + ","
    					   + e.getLeftChild() + "," + e.getRightChild());
    	}
    	writer.close();
    }
    
    /**************************** PRIVATE METHODS *****************************/
	private String[] insert(String name, int parent) {
		visited++; // visited this node

		if(name.compareTo(countries.get(parent).getName()) < 0) {
			if(countries.get(parent).getLeftChild() == -1) {
				countries.get(parent).setLeftChild(countries.size());
				countries.add(new Country(name, countries.size() + 1));
				return new String[] {Integer.toString(countries.size()),
									 Integer.toString(visited)};
			} else { // add node to the LEFT of parent, return {id, nodes visited}
				return insert(name, countries.get(parent).getLeftChild());
			} // otherwise continue looking to the LEFT

		} else {
			if(countries.get(parent).getRightChild() == -1) {
				countries.get(parent).setRightChild(countries.size());
				countries.add(new Country(name, countries.size() + 1));
				return new String[] {Integer.toString(countries.size()),
									 Integer.toString(visited)};
			} else { // add node to the RIGHT of parent, return {id, nodes visited}
				return insert(name, countries.get(parent).getRightChild());
			} // otherwise continue looking to the RIGHT
		}
	}
	
	private String[] search(String name, int parent) {
		visited++; // visited this node

		if(name.compareTo(countries.get(parent).getName().toLowerCase()) < 0) {
			if(countries.get(parent).getLeftChild() == -1) {
				return new String[] {"", Integer.toString(visited)};
			} else { // stop on -1 or continue searching to the LEFT of this node
				return search(name, countries.get(parent).getLeftChild());
			}

		} else if(name.compareTo(countries.get(parent).getName().toLowerCase()) >0){
			if(countries.get(parent).getRightChild() == -1) {
				return new String[] {"", Integer.toString(visited)};
			} else { // stop on -1 or continue searching to the RIGHT of this node
				return search(name, countries.get(parent).getRightChild());
			}
		// strings must match so return array {name & id, nodes visited}
		} else return new String[] {countries.get(parent).getName() + " "
									+ fmt.format(countries.get(parent).getId()),
									Integer.toString(visited)};
	}

	private void getAlphabetical(int parent) {
		if(countries.get(parent).getLeftChild() != -1)				// left node
			getAlphabetical(countries.get(parent).getLeftChild());
		alphaList.add(countries.get(parent)); // in order traversal	// root node
		if(countries.get(parent).getRightChild() != -1)
			getAlphabetical(countries.get(parent).getRightChild());	// right node
		return;
	}
}