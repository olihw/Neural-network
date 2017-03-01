import java.util.*;
import java.lang.Math;
class Perceptron {

	double bias = 0;     
	ArrayList<Link> linkArray = new ArrayList<Link>();
	double sigmoid = 0;
	double delta = 0;
	Link outputLink = new Link();
	

	void createLinks(int numInputs) {
		Network network = new Network();
		for(int i=0; i<numInputs; i++) {
			Link link = new Link();
			link.input = i;
			link.weight = network.randomiser(numInputs);

			linkArray.add(link);
		}

		outputLink.weight = network.randomiser(numInputs);
	}

	void sigmoidSolution(Double[] inputs) {
		double total = 0;
		for(int i=0; i<linkArray.size();i++) {
			total += (linkArray.get(i).weight*inputs[i]);
		}
		
		total += bias; // possible error if problems with results then check

		sigmoid = sigmoidFunction(total);
		//System.out.println("Sigmoid: " + sigmoid);
	}

	double sigmoidFunction(double total) {
		return 1/(1+Math.pow(Math.E, -total));
	}

	double sigmoidDifferential() {
		return sigmoid * (1 - sigmoid);
	}

	void delta(double deltaOutput) {
		delta = (outputLink.weight * deltaOutput) * sigmoidDifferential();
		//System.out.println("Delta: " +delta);
	}

	void updateOutputLinkWeight(double p, double outputDelta) {
		outputLink.weight += (p * outputDelta * sigmoid); //changed to outputDelta instead of delta
		//System.out.println("output link: " + outputLink.weight);
	}

	void updateWeights(double p, Double[] inputs) {
		for(Link link: linkArray) {
			link.weight += (p*delta*inputs[link.input]);
			//System.out.println("link: " + link.input + " weight: "+ link.weight + " input: " + inputs[link.input]);
		}
	}

	void updateBias(double p) {
		bias += (p*delta*1);
		//System.out.println("perceptron bias: " + bias);

	}

	void updateAllWeights(double p, Double[] inputs, double outputDelta) {
		updateBias(p);
		updateWeights(p, inputs);
		updateOutputLinkWeight(p, outputDelta);

	}
}