import java.lang.Math;
import java.util.*;
import java.io.*;
public class NeuralNetwork {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Network test = new Network();
        Input input = new Input();

        input.getData(); //inputs data

        int numOfPerceptrons = 5; // sets number of perceptrons
        int numOfInputs = 8; // sets number of inputs
        test.createPerceptrons(numOfPerceptrons, numOfInputs); //creates networks with numbers above

        int counter = 0;
        String csvFile = "testDataResults.csv"; //file test dataset results go to
        FileWriter writer = new FileWriter(csvFile);

        double trainingError = 0;
        double validationErrorSum = 0;
        double validationError = 0;
        double previousValidationError = 0;
        int validationCounter = 0;

        //loop that starts going through training data
        while(counter < 5000) {
            for(int i =0; i<input.trainingData.size(); i++) {
                if(i == 0) { // reset training error
                    trainingError = 0;
                }

                test.forwardPass(input.trainingData.get(i)); // forward pass

                trainingError += (input.trainingData.get(i)[8] - test.outputSigmoid)*(input.trainingData.get(i)[8] - test.outputSigmoid);

                test.backpass(input.trainingData.get(i)); // backwards pass
            }

            for(int i =0; i<input.validationData.size(); i++) {
                if(i == 0) {
                    validationErrorSum = 0;
                }
                test.forwardPass(input.validationData.get(i));
                validationErrorSum += (input.validationData.get(i)[8] - test.outputSigmoid)*(input.validationData.get(i)[8] - test.outputSigmoid);
            }

            counter++; // keep count of number of epochs

            trainingError = Math.sqrt(trainingError/input.trainingData.size()); //error for RMSE
            previousValidationError = validationError;
            validationError = Math.sqrt(validationErrorSum/input.validationData.size());   

            if(validationError > previousValidationError && counter != 1) {
                validationCounter++;
            } else {
                validationCounter = 0;
            }
            if(validationCounter > 100) {
                break; // if overtrained break from loop
            }

            //annealing
            if (counter == epochs*0.5) {
                test.p = test.p * 0.5;
            }
            if (counter == epochs*0.6) {
                test.p = test.p * 0.5;
            }
            if (counter == epochs*0.7) {
                test.p = test.p * 0.5;
            }
            if (counter == epochs*0.8) {
                test.p = test.p * 0.5;
            }
        }

        
        double testError = 0; // writes results to csv.
        for(int i = 0; i<input.testData.size(); i++) {
            test.forwardPass(input.testData.get(i));
            writer.append(String.valueOf(input.destandardiseFunction(test.outputSigmoid)));
            writer.append(",");
            writer.append(String.valueOf(input.destandardiseFunction(input.testData.get(i)[8])));
            writer.append("\n");
            testError += Math.abs(input.testData.get(i)[8] - test.outputSigmoid);
        }
        //test.printPerceptrons();
        writer.flush();
        writer.close();
        testError = Math.sqrt(testError/input.testData.size());

    }
}