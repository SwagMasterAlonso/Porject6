import java.util.ArrayList;
import java.util.List;

/**
Alonso
 */
public class Node {
	String name;
	List<Node> edges;
	double[][] cpt;
	VariableType type;
	Boolean observedVal;

	Node(String nameObj) {
		this.edges = new ArrayList<Node>();
		this.name = nameObj;
		this.cpt = null;
		this.type = null;
		this.observedVal = null;
	}

	public Boolean getObservedVal() {
		return observedVal;
	}

	public void setObservedVal(Boolean observedVal) {
		this.observedVal = observedVal;
	}
	public VariableType getType() {
		return type;
	}

	public void setType(VariableType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public List<Node> getEdges() {
		return edges;
	}

	public double[][] getCpt() {
		return cpt;
	}

	public double getCPTVal(int row, int col) {
		return this.cpt[row][col];
	}

	public void addParent(Node node) {
		this.edges.add(node);
	}

	public void createCPT(String[] probs) {
		int size = probs.length;
	//	System.out.println("Size is: " + size);
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
//					for(int k = 0; k < size;k++){
		//				System.out.println("Setting prob to at: " +i+" "+" "+j+" "+probs[counter]);
						this.cpt[i][j] = Double.parseDouble(probs[counter]);
						counter++;
//					}
				}
			}
			break;
		case 8:
			this.cpt = new double[4][2];
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 2; j++) {
	//				System.out.println("Setting prob to at: " +i+" "+" "+j+" "+probs[counter]);
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







	public void printCPT(String[] probs) {
		int size = probs.length;
	//	System.out.println("Size is: " + size);

		System.out.println("Printing");
		switch(size) {
		case 2:
			System.out.print(this.cpt[0][0] +" ");
			System.out.print(this.cpt[0][1]);
			System.out.println("");
			break;
		case 4:
		//	System.out.println("in 4");
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					System.out.print(this.cpt[i][j] +" ");

				}
				System.out.println("");
			}
	//		System.out.println("out 4");

			break;
		case 8:
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 2; j++) {
						System.out.print(this.cpt[i][j]+" ");
					}
				System.out.println("");
			}
			break;
		default:
			System.out.println("This array is not valid size.");
			break;
		}
	}










}
