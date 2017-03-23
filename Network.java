import java.lang.Math;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
class Network {

	double outputBias = 0; 
	double previousOutputBias = 0;
	double outputSigmoid = 0; 
	double outputDelta = 0;  
	ArrayList<Perceptron> perceptronArray = new ArrayList<Perceptron>();

	double p = 0.5; //learning parameter
	double a = 0.9; // for momentum

	double randomiser(double numInputs) {
		return ThreadLocalRandom.current().nextDouble((-2/numInputs), (2/numInputs)); //random number generator
	}

	void createPerceptrons(int numToCreate, int numInputs) {
		for(int i = 0; i<numToCreate; i++) {
			Perceptron perceptron = new Perceptron();
			perceptron.bias = randomiser(numInputs);
			perceptron.createLinks(numInputs);
			perceptronArray.add(perceptron);
			setOutput(numInputs);
		}
	}

	void setOutput(double numInputs) {
		outputBias = randomiser(numInputs);
	}

	void forwardPass(Double[] inputs) {
		for(Perceptron perceptron: perceptronArray) {
			perceptron.sigmoidSolution(inputs);
		}

		sigmoidSolutionOutput();
	}

	void sigmoidSolutionOutput() {
		double total = 0;

		for(Perceptron perceptron: perceptronArray) {
			total += perceptron.outputLink.weight * perceptron.sigmoid; 
		}

		total += outputBias;
		outputSigmoid = sigmoidFunction(total);
	}

	double sigmoidFunction(double total) {
		return 1/(1+Math.pow(Math.E, -total));
	}

	void backpass(Double[] inputs) {
		deltaOutput(inputs);

		for(Perceptron perceptron: perceptronArray) {
			perceptron.delta(outputDelta);
		}

		previousOutputBias = outputBias;
		outputBias = outputBias + (p * outputDelta);
		outputBias += a*(outputBias - previousOutputBias);

		for(Perceptron perceptron: perceptronArray) {
			perceptron.updateAllWeights(p, inputs, outputDelta);
		}
	}

	double sigmoidDifferentialOutput() {
		return outputSigmoid * (1 - outputSigmoid);
	}

	void deltaOutput(Double[] inputs) {
		outputDelta = (inputs[(inputs.length-1)] - outputSigmoid) * sigmoidDifferentialOutput();
	}
}