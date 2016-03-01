import java.util.ArrayList;
import java.util.List;

/**
Alonso Martinez and James Chow 
*/

//class to hold Network data
public class Network {

	
	//List of nodes for the bayesian network
	private List<Node> BayesNetNodes = new ArrayList<Node>();

	
	//getter for bayes net nodes
	public List<Node> getBayesNetNodes() {
		return BayesNetNodes;
	}

	
	//sets the bayes net nodes
	public void setBayesNetNodes(List<Node> bayesNetNodes) {
		BayesNetNodes = bayesNetNodes;
	}
	
	
	
	
}
