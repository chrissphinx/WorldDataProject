/* PROJECT: WorldDataProject (Java)         CLASS: Country
 * AUTHOR: Colin MacCreery
 * DESCRIPTION: Data object class representing entries into the array
 * 				and their associated binary tree pointers. Provides
 * 				multiple constructors to simplify creation when needed.
 ******************************************************************************/

package sharedClassLibrary;

public class Country
{
    /**************************** PRIVATE DECLARATIONS ************************/
	private String name;
	private int id, leftChild, rightChild;
	
    /**************************** PUBLIC CONSTRUCTOR(S) ***********************/
	public Country(String name, int id) {
		this.name = name;
		this.id = id;
		this.leftChild = -1;
		this.rightChild = -1;
	}
	
	public Country(String name, int id, int leftChild, int rightChild) {
		this.name = name;
		this.id = id;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	/**************************** PUBLIC GET/SET METHODS **********************/
	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public int getLeftChild() {
		return leftChild;
	}

	public int getRightChild() {
		return rightChild;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLeftChild(int leftChild) {
		this.leftChild = leftChild;
	}

	public void setRightChild(int rightChild) {
		this.rightChild = rightChild;
	}
}