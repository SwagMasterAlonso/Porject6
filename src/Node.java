import java.util.ArrayList;
import java.util.List;

/**
Alonso
*/
public class Node {
	int id;
	List<Node> edges;
	double[][] cpt;

	Node(int id, double[][] cpt) {
		this.edges = new ArrayList<Node>();
		this.id = id;
		this.cpt = cpt;
	}
}
