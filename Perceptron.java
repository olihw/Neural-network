import java.util.*;
import java.lang.Math;
class Perceptron {

	double bias = 0;     
	double previousBias = 0;
	ArrayList<Link> linkArray = new ArrayList<Link>();
	double sigmoid = 0;
	double delta = 0;
	Link outputLink = new Link();
	double a = 0.9;

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
		outputLink.previousWeight = outputLink.weight;
		outputLink.weight += (p * outputDelta * sigmoid); //changed to outputDelta instead of delta
		outputLink.weight += a*(outputLink.weight - outputLink.previousWeight);
		//System.out.println("output link: " + outputLink.weight);
	}

	void updateWeights(double p, Double[] inputs) {
		for(Link link: linkArray) {
			link.previousWeight = link.weight;
			link.weight += (p*delta*inputs[link.input]);
			link.weight += a*(link.weight - link.previousWeight);
			//System.out.println("link: " + link.input + " weight: "+ link.weight + " input: " + inputs[link.input]);
		}
	}

	void updateBias(double p) { //momentum added
		previousBias = bias;
		bias += (p*delta*1);
		bias += a*(bias-previousBias);
		//System.out.println("perceptron bias: " + bias);

	}

	void updateAllWeights(double p, Double[] inputs, double outputDelta) {
		updateBias(p);
		updateWeights(p, inputs);
		updateOutputLinkWeight(p, outputDelta);

	}
}