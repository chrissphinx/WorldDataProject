/* PROJECT: WorldDataProject (Java)         CLASS: RawDataRecord
 * AUTHOR: Colin MacCreery
 * DESCRIPTION: Convenience class to avoid writing redundant code since
 * 				INSERT lines in both RawData and TransData files need to be
 * 				parsed correctly. Parses generally, returning an array of
 * 				the data contained in the INSERT line.
 ******************************************************************************/

package sharedClassLibrary;

public class RawDataRecord
{
    /**************************** PRIVATE DECLARATIONS ************************/
	private String code;
	private String name;
	private String continent;
	private int surfaceArea;
	private int yearOfIndep;
	private int population;
	private float lifeExp;
	private int gnp;

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

	public int getPopulation() {
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

	public void setPopulation(int population) {
		this.population = population;
	}

	public void setLifeExp(float lifeExp) {
		this.lifeExp = lifeExp;
	}

	public void setGnp(int gnp) {
		this.gnp = gnp;
	}

	/**************************** PRIVATE METHODS *****************************/
	public void parse(String line) {
		line = line.substring(line.indexOf('(') + 1, line.length() - 2);
		String[] data = line.split(","); // pull values from inside ()'s and split
		for(int i = 0; i < data.length; i++) {
			// remove annoying quotes
			if(data[i].startsWith("'"))
			   data[i] = data[i].substring(1, data[i].length() - 1);
			if("NULL".compareTo(data[i]) == 0)
			   data[i] = "0";
		}
		this.code = data[0];
		this.name = data[1];
		this.continent = data[2];
		this.surfaceArea = Integer.parseInt(data[4]);
		this.yearOfIndep = Integer.parseInt(data[5]);
		this.population = Integer.parseInt(data[6]);
		this.lifeExp = Float.parseFloat(data[7]);
		this.gnp = Integer.parseInt(data[8]);
	}
}