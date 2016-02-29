/**
 * Class to hold both a sample and its associated weight.
 * @author jameschow, amartinez
 *
 */
public class WeightedSample {
	Boolean[] sample;
	double weight;

	WeightedSample(Boolean[] event, double weight) {
		this.sample = event;
		this.weight = weight;
	}

	public Boolean[] getSample() {
		return sample;
	}

	public double getWeight() {
		return weight;
	}


}
