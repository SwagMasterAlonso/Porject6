import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;

/**
Alonso
 */
public class XxBayesianNetworkxX {


	public static void main(String[] args) {
		String fileName = null;
		//get command line arguments, there should only be 2
		if(args.length==1){
			String inputString = args[0];
			fileName = inputString;
		} else {
			System.exit(0);
		}


		Network BayesNet = new Network();

		createNodes(fileName,BayesNet);
		populateNodes(fileName,BayesNet);


		System.out.println("FinalN Nodes Are");
		System.out.println(BayesNet.getBayesNetNodes());
	}










	public static void createNodes(String fileName,Network bayesNet){
		String line;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {

				String[] fields = line.split(" ");
				fields = line.split(":");
				//System.out.println(fields);
				String ex = fields[0];
				//System.out.println(ex);

				//create new node here
				Node node = new Node(fields[0]);

				//Add our node to our network
				bayesNet.getBayesNetNodes().add(node);


			}
		}catch(Exception e){};
	}


	public static void populateNodes(String fileName,Network bayesNet){

	//	System.out.println("In populate");
		String line;
		BufferedReader br = null;
		try {

			System.out.println("Trying");
			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
			//	System.out.println("Redoing");
				String[] fields = line.split(" ");
				fields = line.split(":");
				String parent = getParentData(fields[1]);
			//	System.out.println(parent);
				String[] parents = parent.split(" ");
				//System.out.println(parent);


				for(String s:parents){


					System.out.println("In for loop: "+s);

					if(!s.equals("")){
				getNode(s,bayesNet).getEdges().add(getNode(fields[0],bayesNet));
					}
				}

				System.out.println("Out of for loop");
				System.out.println("Children are");
				System.out.println(getNode(fields[0],bayesNet));

			}
		}catch(Exception e){};










	}

	 public static String getParentData(String string){
	        return string.substring(string.indexOf('[')+1,string.indexOf(']'));
	    }

	 public static Node getNode(String nodeString,Network bayesNet){

		 for(Node node: bayesNet.getBayesNetNodes()){
			 if(nodeString.equals(node.getName())){
				// System.out.println("Worked");
				 return node;

			 }
		 }
		// System.out.println("Returning null");
		return null;




	 }

	 public static void assignStatus(String fileName, Network bNet) {
		 String line;
		 BufferedReader br = null;
		 Node temp;
		 int i = 0;
		 try {
			 br = new BufferedReader(new FileReader(fileName));
			 while ((line = br.readLine()) != null) {
				 String[] fields = line.split(",");
				 for (Iterator<Node> it = bNet.getBayesNetNodes().iterator(); it.hasNext() && i < fields.length; i++) {
					 temp = it.next();
					 switch (fields[i]) {
						 case "t":
							 temp.setType(VariableType.EVIDENCE);
							 temp.setObservedVal(true);
							 continue;
						 case "f":
							 temp.setType(VariableType.EVIDENCE);
							 temp.setObservedVal(false);
							 continue;
						 case "-":
							 temp.setType(VariableType.UNKNOWN);
							 temp.setObservedVal(null);
							 continue;
						 case "q":
							 temp.setType(VariableType.QUERY);
							 temp.setObservedVal(null);
							 continue;
					 }
				 }
			 }
		 }catch(Exception e){
			 System.err.println("Exception"+e);
		 };

	 }

}
