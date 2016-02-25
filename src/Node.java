import java.util.ArrayList;
import java.util.List;

/**
Alonso
*/
public class Node {
	String name;
	List<Node> edges;
	double[][] cpt;

	Node(String nameObj, double[] arrayVal) {
		this.edges = new ArrayList<Node>();
		this.name = nameObj;
		createCPT(arrayVal);
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

	public void createCPT(double[] array) {
		int size = array.length;

		switch(size) {
			case 2:
				this.cpt = new double[1][2];
				this.cpt[0][0] = array[0];
				this.cpt[0][1] = array[1];
			case 4:
				this.cpt = new double[2][2];
				for (int i = 0; i < 2; i++) {
					for (int j = 0; j < 2; j++) {
						this.cpt[i][j] = array[j];
					}
				}
			case 8:
				this.cpt = new double[4][2];
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 2; j++) {
						this.cpt[i][j] = array[j];
					}
				}
			default:
				System.out.println("This array is not valid size.");
				break;
		}
	}
}
