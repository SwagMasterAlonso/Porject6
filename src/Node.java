import java.util.ArrayList;
import java.util.List;

/**
Alonso
*/
public class Node {
	String name;
	List<Node> edges;
	double[][] cpt;

	Node(String nameObj, double[][] cpt) {
		this.edges = new ArrayList<Node>();
		this.name = nameObj;
		this.cpt = cpt;
	}
}
