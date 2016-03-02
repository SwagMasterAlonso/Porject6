/**
 * Class to hold both a sample and its associated weight.
 * @author jameschow, amartinez
 *
 */

//class to hold the weighted sample
public class WeightedSample {
	Boolean[] sample; //sample event for the weight
	double weight; //weight for the event

	
	//constructor for the sample
	WeightedSample(Boolean[] event, double weight) {
		this.sample = event;
		this.weight = weight;
	}

	//function gets the sample
	public Boolean[] getSample() {
		return sample;
	}

	//functions for the weight
	public double getWeight() {
		return weight;
	}


}
