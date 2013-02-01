/* PROJECT: WorldDataProject (Java)         CLASS: Parser
 * AUTHOR: Colin MacCreery
 * DESCRIPTION: Convenience class to avoid writing redundant code since
 * 				INSERT lines in both RawData and TransData files need to be
 * 				parsed correctly. Parses generally, returning an array of
 * 				the data contained in the INSERT line.
 ******************************************************************************/

package sharedClassLibrary;

public class Parser
{
	public String[] parse(String line) {
		line = line.substring(line.indexOf('(') + 1, line.length() - 2);
		String[] data = line.split(","); // pull values from inside ()'s and split
		for(int i = 0; i < data.length; i++) {
			// remove annoying quotes
			if(data[i].startsWith("'"))
			   data[i] = data[i].substring(1, data[i].length() - 1);
		}
		return data;
	}
}