import java.io.BufferedReader;
import java.io.FileReader;

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
	
		
		
		fileParsing(fileName);

	

	}
	
	
	
	
	
	
	
	
	
	
	public void fileParsing(String fileName){
		String line;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				
				String[] fields = line.split(" ");
				
				//create new node here
				//Node node = new Node(fields[0]);
				
				
				
			}
		}catch(Exception e){};
		
		
		
		
	}
		
}
