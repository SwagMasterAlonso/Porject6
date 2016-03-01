import java.util.ArrayList;
import java.util.List;
/**
 * This class represents a node in the Bayesian Network that holds the list of
 * parent nodes, the cpt values, and the variable type.
 * @author jameschow, amartinez
 *
 */
public class Node {
	/**The name of the node.*/
	String name;
	/**The list of parent nodes.*/
	List<Node> edges;
	/**Conditional Probability table.*/
	double[][] cpt;
	/**The variable type of the */
	VariableType type;
	Boolean observedVal;


	//constructor for node class, takes a string which is its name
	Node(String nameObj) {
		this.edges = new ArrayList<Node>(); //sets edges to a new array list
		this.name = nameObj; //name to name obj
		this.cpt = null; //the cpt to null initially until we set it
		this.type = null; //type of variable to null until we set it
		this.observedVal = null; //set the observed val ( true or false ) to null until we set it
	}


	//getter for observed val
	public Boolean getObservedVal() {
		return observedVal;
	}


	//setter for observed val
	public void setObservedVal(Boolean observedVal) {
		this.observedVal = observedVal;
	}

	//gettter for variable type
	public VariableType getType() {
		return type;
	}


	//setter for the type
	public void setType(VariableType type) {
		this.type = type;
	}


	//getter for the node name
	public String getName() {
		return name;
	}


	//getter for the list of edges
	public List<Node> getEdges() {
		return edges;
	}

	//getter for the cpt
	public double[][] getCpt() {
		return cpt;
	}


	//gets a specific val for the cpt 
	public double getCPTVal(int row, int col) {
		return this.cpt[row][col];
	}


	//adds a parent to our list of edgess
	public void addParent(Node node) {
		this.edges.add(node);
	}


	//creates the cpt table with the given probabilities in a 2d array
	public void createCPT(String[] probs) {
		int size = probs.length;
		int counter = 0;

		switch(size) {
		case 2:
			this.cpt = new double[1][2];
			this.cpt[0][0] = Double.parseDouble(probs[0]);
			this.cpt[0][1] = Double.parseDouble(probs[1]);
			break;
		case 4:
			this.cpt = new double[2][2];
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					this.cpt[i][j] = Double.parseDouble(probs[counter]);
					counter++;
				}
			}
			break;
		case 8:
			this.cpt = new double[4][2];
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 2; j++) {
					this.cpt[i][j] = Double.parseDouble(probs[counter]);
					counter++;				}
			}
			break;
		default:
			System.out.println("This array is not valid size.");
			break;
		}
	}

	public String toString(){
		return this.name+this.edges;
	}
}
