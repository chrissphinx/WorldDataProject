/* PROJECT: WorldDataProject (Java)         CLASS: RawDataRecord
 * AUTHOR: Colin MacCreery
 * DESCRIPTION: This was my Parser class from the first project, just reworked it
 *				into a data structure. Only needs the one method parse() but does
 *				not return a value anymore, just stores the appropriate values into
 *				fields. getRecord() method provided because specs say that the
 *				SetupProgram has to call insert() methods on both NameIndex AND
 *				MainData classes. Otherwise could probably call MainData.insert()
 *				from this class.
 ******************************************************************************/

package sharedClassLibrary;

import java.text.DecimalFormat;

public class RawDataRecord
{
    /**************************** PRIVATE DECLARATIONS ************************/
	private String code;
	private String name;
	private String continent;
	private int surfaceArea;
	private int yearOfIndep;
	private long population;
	private float lifeExp;
	private int gnp;
	private DecimalFormat fmt = new DecimalFormat("##,###,###,##0");

    /**************************** PUBLIC GET/SET METHODS **********************/
	public RawDataRecord getRecord() {
		return this;
	}
	
    public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getContinent() {
		return continent;
	}

	public int getSurfaceArea() {
		return surfaceArea;
	}

	public int getYearOfIndep() {
		return yearOfIndep;
	}

	public long getPopulation() {
		return population;
	}

	public float getLifeExp() {
		return lifeExp;
	}

	public int getGnp() {
		return gnp;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public void setSurfaceArea(int surfaceArea) {
		this.surfaceArea = surfaceArea;
	}

	public void setYearOfIndep(int yearOfIndep) {
		this.yearOfIndep = yearOfIndep;
	}

	public void setPopulation(long population) {
		this.population = population;
	}

	public void setLifeExp(float lifeExp) {
		this.lifeExp = lifeExp;
	}

	public void setGnp(int gnp) {
		this.gnp = gnp;
	}

	@Override
	public String toString() {
		return code
       + " " + name
       + " " + continent
       + " " + String.format("%10s", fmt.format(surfaceArea))
       + " " + String.format("%5s", yearOfIndep)
       + " " + String.format("%13s", fmt.format(population))
       + " " + String.format("%4s", lifeExp)
       + " " + String.format("%9s", fmt.format(gnp));
	}

    /**************************** PUBLIC SERVICE METHODS **********************/
	public void parse(String line) {
		line = line.substring(line.indexOf('(') + 1, line.length() - 2);
		String[] data = line.split(","); // pull values from inside ()'s and split
		for(int i = 0; i < data.length; i++) {
			// remove annoying quotes
			if(data[i].startsWith("'"))
			   data[i] = data[i].substring(1, data[i].length() - 1);
			if("NULL".compareTo(data[i]) == 0)
			   data[i] = "0"; // convert NULL values to zero
		}
		this.code = data[0]; // truncate names longer than 15 characters
		this.name = data[1].length() > 15 ? data[1].substring(0, 15) : data[1];
		this.continent = data[2]; // and store remaining of data
		this.surfaceArea = Integer.parseInt(data[4]);
		this.yearOfIndep = Integer.parseInt(data[5]);
		this.population = Long.parseLong(data[6]);
		this.lifeExp = Float.parseFloat(data[7]);
		this.gnp = Integer.parseInt(data[8]);
	}
}