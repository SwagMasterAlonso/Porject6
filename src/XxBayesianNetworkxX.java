import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Random;

/**
Alonso
 */
public class XxBayesianNetworkxX {

	public static void main(String[] args) {

		//params for arguments, where filename is the network option, query file is the query, and the sampl
		//holds the number of samples to run
		String fileName = null;
		String queryFile = null;
		int samples =0;

		//get command line arguments, there should only be 2
		if(args.length==3){
			String inputString = args[0];
			queryFile = args[1];
			samples = Integer.parseInt(args[2]);
			fileName = inputString;
		} else {
			System.out.println("Not enough arguments");
			System.exit(0);
		}


		//doubles to hold the means from the tests
		double meanRej = 0.000;
		double meanLike = 0.000;

		//doubles to hold the variances from the tests
		double meanRejV = 0.000;
		double meanLikeV = 0.000;

		//create our new Bayesian Network
		Network BayesNet = new Network();

		//creates all of the nodes from the file and adds them to our Bayesian Net
		createNodes(fileName,BayesNet);

		//adds all of the cpts to the nodes in our file
		populateNodes(fileName,BayesNet);

		//assigns the type (query, evidence, neither) to our nodes
		assignStatus(queryFile, BayesNet);

		double prevProbR = 0.00, prevProbL = 0.00, currentProbR = 0.00, currentProbL = 0.00;
		double diffR = 0.0, diffL = 0.0;
		for(int i = 0; i < 100000; i++){

			currentProbR = rejectionSampling(i,BayesNet);
			diffR = currentProbR - prevProbR;
			double answerR = diffR / (Math.abs(currentProbR));
			if (Math.abs(answerR) < 0.00001) {
				System.out.println("Rejection sampling converges at "+i);
				break;
			}

			prevProbR = currentProbR;

		}
		for(int i = 0; i < 100000; i++){

			currentProbL =likelihood_weighting(i, BayesNet);
			diffL = currentProbL - prevProbL;
			double answerL = diffL/(Math.abs(currentProbL));
			if (Math.abs(answerL) < 0.00001) {
				System.out.println("Likelihood weighting converges at "+i);
				break;
			}
			prevProbL = currentProbL;


		}

////CODE TO RUN TESTS FOR THE GIVEN SAMPLES
//		for(int i = 0; i < 10; i++){
//
//			meanRej += rejectionSampling(samples,BayesNet);
//			System.out.println(meanRej);
//
//			meanLike +=likelihood_weighting(samples, BayesNet);
//
//		}
//
//		meanRej/=10;
//		meanLike/=10;
//
//		for(int i = 0; i < 10;i++){
//			meanRejV+= (rejectionSampling(samples,BayesNet)-meanRej)*(rejectionSampling(samples,BayesNet)-meanRej);
//			meanLikeV+= (likelihood_weighting(samples,BayesNet)-meanLike)*(likelihood_weighting(samples,BayesNet)-meanLike);
//		}
//
//		meanRejV/=10;
//		meanLikeV/=10;
//
//
//
//		System.out.println("Mean of Rejection sampling is: "+meanRej);
//		System.out.println("Mean of Likelihood weighting is: "+meanLike);
//		System.out.println("Variance of Rejection sampling is: "+meanRejV);
//		System.out.println("Variance of Likelihood weighting is: "+meanLikeV);



	}









//function generates all of then nodes in the given file and adds them to our bayesian network
	public static void createNodes(String fileName,Network bayesNet){
		String line;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {

				String[] fields = line.split(" ");
				fields = line.split(":");

				String ex = fields[0];
				//System.out.println(ex);

				//create new node here
				Node node = new Node(fields[0]);

				//Add our node to our network
				bayesNet.getBayesNetNodes().add(node);


			}
		}catch(Exception e){};
	}



	//takes our
	public static void populateNodes(String fileName,Network bayesNet){

		//	System.out.println("In populate");
		String line;
		BufferedReader br = null;
		try {

			//		System.out.println("Trying");
			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				String[] fields = line.split(" ");
				fields = line.split(":");
				String parent = getParentData(fields[1]);
				String[] parents = parent.split(" ");
				String[] probabilities = getProbs(line).split(" ");
				for(String s:parents){
					if(!s.equals("")){
						getNode(fields[0],bayesNet).getEdges().add(getNode(s,bayesNet));
					}
				}
				getNode(fields[0],bayesNet).createCPT(probabilities);
			}
		}catch(Exception e){};









	}


	public static double[][] getCPT(int size,String[] probs){

		double[][] cpt = null;

		switch(size) {
		case 2:
			cpt = new double[1][2];
			cpt[0][0] = Double.parseDouble(probs[0]);
			cpt[0][1] = Double.parseDouble(probs[1]);
		case 4:
			cpt = new double[2][2];
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					for(int k = 0; k < size;k++){
						cpt[i][j] = Double.parseDouble(probs[k]);
					}
				}
			}
		case 8:
			cpt = new double[4][2];
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 2; j++) {
					for(int k = 0; k < size;k++){
						cpt[i][j] = Double.parseDouble(probs[k]);
					}
				}
			}
		default:
			System.out.println("This array is not valid size.");
			break;
		}

		return cpt;
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


	public static void printProbabilities(String fileName,Network bNet){
		String line;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				//System.out.println("Reading");
				//String[] fields = line.split(" ");
				//System.out.println(fields[2]);
				System.out.println("Line is: "+line);

				String[] probabilities = getProbs(line).split(" ");



				//System.out.println("Probs are " +probabilities);

			}
		}catch(Exception e){};
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
					case "?":
						temp.setType(VariableType.QUERY);
						temp.setObservedVal(null);
						continue;
					default:
						System.out.println("Unknown character was read in.");
					}
				}
			}
		}catch(Exception e){
			System.err.println("Exception"+e);
		};

	}

	public static String getProbs(String string){
		//	System.out.println("Here");
		string = string + "*";
		string = string.replace(".", ",");
		//	System.out.println(string);
		int place1 = string.indexOf(",")-1;
		//	System.out.println("Place 1 is: "+place1);
		int place2 = string.indexOf("*")-1;
		//	System.out.println("Place 2 is: "+place2);

		String string2 = string.substring(place1, place2);
		string2 = string2.replace(",", ".");

		//System.out.println("SubString is " + string2);
		return string2;
	}


	public static int queryIndex(Boolean[] event,Network bNet){

		for(int i = 0; i < bNet.getBayesNetNodes().size();i++){


			if(bNet.getBayesNetNodes().get(i).getType() == VariableType.QUERY){
				return i;

			}
		}
		return -1;
	}


	public static double rejectionSampling(int numSamples, Network bNet) {

		int nonRejected = 0;
		double[] vector = {0,0};

		for(int i = 0; i < numSamples;i++){

			Boolean[] event = new Boolean[bNet.getBayesNetNodes().size()];

			event = prior_sample(bNet);


			if(checkConsistency(event,bNet)){
				nonRejected++;

				int queryInd = queryIndex(event,bNet);

				if(queryInd!= -1){

					if(event[queryInd]){
						//vector at 0 is true, vector at 1 is false
						vector[0] +=1;
					} else {
						vector[1] +=1;
					}
				}
			}

		}

		vector[0] /= nonRejected;
		vector[1] /= nonRejected;
		//		System.out.println("Non Rejected is: " +nonRejected);
		//		System.out.println("Vector 0 is: "+vector[0]);
		//		System.out.println("Vector 1 is: "+vector[1]);
		return vector[0];
	}


	public static double likelihood_weighting (int numSamples, Network bNet) {
		double sumAllWeights = 0;
		double[] vector = {0,0};
		WeightedSample sample;
		for(int i = 0; i < numSamples;i++){
			sample = weighted_sample(bNet);
			int queryInd = queryIndex(sample.getSample(),bNet);
			if(queryInd!= -1) {
				if(sample.getSample()[queryInd]){
					//vector at 0 is true, vector at 1 is false
					vector[0] += sample.getWeight();
					sumAllWeights += sample.getWeight();
				} else {
					vector[1] += sample.getWeight();
					sumAllWeights += sample.getWeight();
				}
			}
		}

		vector[0] /= sumAllWeights;
		vector[1] /= sumAllWeights;
		//		System.out.println("Sum is: " +sumAllWeights);
		//		System.out.println("Vector 0 is: "+vector[0]);
		//		System.out.println("Vector 1 is: "+vector[1]);
		return vector[0];
	}

	private static Boolean checkConsistency(Boolean[] event, Network bNet){
		for(int i = 0; i < event.length;i++){
			if(bNet.getBayesNetNodes().get(i).getType() == VariableType.EVIDENCE){
				if(bNet.getBayesNetNodes().get(i).getObservedVal() != event[i]){
					return false;
				}
			}
		}

		return true;
	}


	private static Boolean[] prior_sample (Network bNet) {
		Boolean[] event = new Boolean[bNet.getBayesNetNodes().size()];
		double rndNum = 0.00;
		Random seed = new Random();
		Node temp;
		Boolean parent1, parent2;
		int index = 0;
		for (Node n: bNet.getBayesNetNodes()) {

			//	System.out.println("The random number is "+rndNum);
			if (n.getEdges().size() == 2) {
				rndNum = seed.nextDouble();

				parent1 = computeParent(seed, n.getEdges().get(0));
				parent2 = computeParent(seed, n.getEdges().get(1));
				if (!parent1 && !parent2) {
					if (rndNum >= 0 && rndNum <= n.getCPTVal(0, 0)) {
						event[index] = false;
						index++;
						continue;
					} else {
						event[index] = true;
						index++;
						continue;
					}
				} else if (!parent1 && parent2) {
					if (rndNum >= 0 && rndNum <= n.getCPTVal(1, 0)) {
						event[index] = false;
						index++;
						continue;
					} else {
						event[index] = true;
						index++;
						continue;
					}
				} else if (parent1 && !parent2) {
					if (rndNum >= 0 && rndNum <= n.getCPTVal(2, 0)) {
						event[index] = false;
						index++;
						continue;
					} else {
						event[index] = true;
						index++;
						continue;
					}
				} else {
					if (rndNum >= 0 && rndNum <= n.getCPTVal(3, 0)) {
						event[index] = false;
						index++;
						continue;
					} else {
						event[index] = true;
						index++;
						continue;
					}
				}
			} else if (n.getEdges().size() == 1) {
				rndNum = seed.nextDouble();
				parent1 = computeParent(seed, n.getEdges().get(0));
				if (parent1) {
					if (rndNum >= 0 && rndNum <= n.getCPTVal(1, 0)) {
						event[index] = false;
						index++;
						continue;
					} else {
						event[index] = true;
						index++;
						continue;
					}
				} else {
					if (rndNum >= 0 && rndNum <= n.getCPTVal(0, 0)) {
						event[index] = false;
						index++;
						continue;
					} else {
						event[index] = true;
						index++;
						continue;
					}
				}
			} else {
				rndNum = seed.nextDouble();
				if (rndNum >= 0 && rndNum <= n.getCPTVal(0, 0)) {
					event[index] = false;
					index++;
					continue;
				} else {
					event[index] = true;
					index++;
					continue;
				}
			}

		}

		return event;
	}

	private static Boolean computeParent(Random rnd, Node n) {
		double random = 0.00;
		Boolean result, result2;
		if (n.getEdges().size() == 0) {
			random = rnd.nextDouble();
			if (random >= 0 && random <= n.getCPTVal(0, 0)) {
				return false;
			} else {
				return true;
			}
		} else if (n.getEdges().size() == 1) {
			random = rnd.nextDouble();
			result = computeParent(rnd, n.getEdges().get(0));
			if (result) {
				if (random >= 0 && random <= n.getCPTVal(1, 0)) {
					return false;
				} else {
					return true;
				}
			} else {
				if (random >= 0 && random <= n.getCPTVal(0, 0)) {
					return false;
				} else {
					return true;
				}
			}
		} else {
			random = rnd.nextDouble();
			result = computeParent(rnd, n.getEdges().get(0));
			result2 = computeParent(rnd, n.getEdges().get(1));
			if (!result && !result2) {
				if (random >= 0 && random <= n.getCPTVal(0, 0)) {
					return false;
				} else {
					return true;
				}
			} else if (!result && result2) {
				if (random >= 0 && random <= n.getCPTVal(1, 0)) {
					return false;
				} else {
					return true;
				}
			} else if (result && !result2) {
				if (random >= 0 && random <= n.getCPTVal(2, 0)) {
					return false;
				} else {
					return true;
				}
			} else {
				if (random >= 0 && random <= n.getCPTVal(3, 0)) {
					return false;
				} else {
					return true;
				}
			}
		}
	}

	public static WeightedSample weighted_sample(Network bNet) {
		Random rnd = new Random();
		Boolean[] event = new Boolean[bNet.getBayesNetNodes().size()];
		WeightedSample sample;
		Node n;
		double weight = 1;
		event = initializeEvent(event, bNet);

		for (int i = 0; i < bNet.getBayesNetNodes().size(); i++) {
			n = bNet.getBayesNetNodes().get(i);
			if (n.getType() == VariableType.EVIDENCE) {
				weight *= computeProb(rnd, n);
			} else {
				event = sample(rnd, event, n, i);
			}
		}
		sample = new WeightedSample(event, weight);
		return sample;
	}

	private static Boolean[] sample (Random seed, Boolean[] event, Node n, int index) {
		double rndNum = 0.00;
		Node temp;
		Boolean parent1, parent2;

		//	System.out.println("The random number is "+rndNum);
		if (n.getEdges().size() == 2) {
			rndNum = seed.nextDouble();

			parent1 = computeParent(seed, n.getEdges().get(0));
			parent2 = computeParent(seed, n.getEdges().get(1));
			if (!parent1 && !parent2) {
				if (rndNum >= 0 && rndNum <= n.getCPTVal(0, 0)) {
					event[index] = false;

				} else {
					event[index] = true;
				}
			} else if (!parent1 && parent2) {
				if (rndNum >= 0 && rndNum <= n.getCPTVal(1, 0)) {
					event[index] = false;
				} else {
					event[index] = true;
				}
			} else if (parent1 && !parent2) {
				if (rndNum >= 0 && rndNum <= n.getCPTVal(2, 0)) {
					event[index] = false;
				} else {
					event[index] = true;
				}
			} else {
				if (rndNum >= 0 && rndNum <= n.getCPTVal(3, 0)) {
					event[index] = false;
				} else {
					event[index] = true;
				}
			}
		} else if (n.getEdges().size() == 1) {
			rndNum = seed.nextDouble();
			parent1 = computeParent(seed, n.getEdges().get(0));
			if (parent1) {
				if (rndNum >= 0 && rndNum <= n.getCPTVal(1, 0)) {
					event[index] = false;
				} else {
					event[index] = true;
				}
			} else {
				if (rndNum >= 0 && rndNum <= n.getCPTVal(0, 0)) {
					event[index] = false;
				} else {
					event[index] = true;
				}
			}
		} else {
			rndNum = seed.nextDouble();
			if (rndNum >= 0 && rndNum <= n.getCPTVal(0, 0)) {
				event[index] = false;
			} else {
				event[index] = true;
			}
		}
		return event;
	}


	private static double computeProb (Random seed, Node n) {
		Boolean parent1, parent2;

		if (n.getEdges().size() == 2) {
			parent1 = computeParent(seed, n.getEdges().get(0));
			parent2 = computeParent(seed, n.getEdges().get(1));
			if (!parent1 && !parent2) {
				if (n.getObservedVal()) {
					return n.getCPTVal(0, 1);
				} else {
					return n.getCPTVal(0, 0);
				}
			} else if (!parent1 && parent2) {
				if (n.getObservedVal()) {
					return n.getCPTVal(1, 1);
				} else {
					return n.getCPTVal(1, 0);
				}
			} else if (parent1 && !parent2) {
				if (n.getObservedVal()) {
					return n.getCPTVal(2, 1);
				} else {
					return n.getCPTVal(2, 0);
				}
			} else {
				if (n.getObservedVal()) {
					return n.getCPTVal(3, 1);
				} else {
					return n.getCPTVal(3, 0);
				}
			}
		} else if (n.getEdges().size() == 1) {
			parent1 = computeParent(seed, n.getEdges().get(0));
			if (parent1) {
				if (n.getObservedVal()) {
					return n.getCPTVal(1, 1);
				} else {
					return n.getCPTVal(1, 0);
				}
			} else {
				if (n.getObservedVal()) {
					return n.getCPTVal(0, 1);
				} else {
					return n.getCPTVal(0, 0);
				}
			}
		} else {
			if (n.getObservedVal()) {
				return n.getCPTVal(0, 1);
			} else {
				return n.getCPTVal(0, 0);
			}
		}
	}
	private static Boolean[] initializeEvent(Boolean[] event, Network bNet) {
		for (int i = 0; i < bNet.getBayesNetNodes().size(); i++) {
			if (bNet.getBayesNetNodes().get(i).getType() == VariableType.EVIDENCE) {
				event[i] = bNet.getBayesNetNodes().get(i).getObservedVal();
			}
		}
		return event;
	}


}
