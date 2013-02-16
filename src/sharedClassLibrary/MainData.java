/* PROJECT: WorldDataProject (Java)         CLASS: MainData
 * AUTHOR: Colin MacCreery
 * DESCRIPTION: 
 ******************************************************************************/

package sharedClassLibrary;

public class MainData
{
	public void printRec(int id, RawDataRecord record) {
		System.out.println(id + " " + record.getCode() + " " + record.getName() + " "
						   + record.getContinent() + " " + record.getSurfaceArea()
						   + " " + record.getYearOfIndep() + " " +
						   + record.getPopulation() + " " + record.getLifeExp()
						   + " " + record.getGnp());
	}
}