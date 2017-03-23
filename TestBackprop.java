import java.lang.Math;
import java.util.*;
import java.io.*;
public class TestBackprop {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Network test = new Network();
        Input input = new Input();

        input.getData();
        System.out.println(input.min);

        // // System.out.println(test.randomiser(8));

        int numOfPerceptrons = 10;
        int numOfInputs = 8;
        test.createPerceptrons(numOfPerceptrons, numOfInputs);

        int counter = 0;

        String csvFile = "newtestoutput.csv";
        FileWriter writer = new FileWriter(csvFile);

        //test.printPerceptrons();
        double trainingError = 0;
        double validationErrorSum = 0;
        double validationError = 0;
        double previousValidationError = 0;
        int validationCounter = 0;
        int epochs = 5000;
        while(counter < epochs) {
            for(int i =0; i<input.trainingData.size(); i++) {
                if(i == 0) {
                    trainingError = 0;
                }

                test.forwardPass(input.trainingData.get(i));
                trainingError += (input.trainingData.get(i)[8] - test.outputSigmoid)*(input.trainingData.get(i)[8] - test.outputSigmoid);
                test.backpass(input.trainingData.get(i)); 
            }

            for(int i =0; i<input.validationData.size(); i++) {
                if(i == 0) {
                    validationErrorSum = 0;
                }
                test.forwardPass(input.validationData.get(i));
                validationErrorSum += (input.validationData.get(i)[8] - test.outputSigmoid)*(input.validationData.get(i)[8] - test.outputSigmoid);
            }

            counter++; 
            trainingError = Math.sqrt(trainingError/input.trainingData.size());
            previousValidationError = validationError;
            validationError = Math.sqrt(validationErrorSum/input.validationData.size());   
            System.out.println(validationError + ", " + previousValidationError);

            // if (counter == epochs*0.5) {
            //     test.p = test.p * 0.5;
            // }
            // if (counter == epochs*0.6) {
            //     test.p = test.p * 0.5;
            // }
            // if (counter == epochs*0.7) {
            //     test.p = test.p * 0.5;
            // }
            // if (counter == epochs*0.8) {
            //     test.p = test.p * 0.5;
            // }


            if(validationError > previousValidationError && counter != 1) {
                validationCounter++;
            } else {
                validationCounter = 0;
            }
            if(validationCounter > 500) {
                System.out.println(counter+ "////////////////////////////////////");
                System.out.println(validationError + ", " + trainingError);
                break;
            }
        }

        
        double testError = 0;
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
        System.out.println(testError);

        String csvFile2 = "newtestoutputtr.csv";
        FileWriter writer2 = new FileWriter(csvFile2);
        for(int i = 0; i<input.trainingData.size(); i++) {
            test.forwardPass(input.trainingData.get(i));
            writer2.append(String.valueOf(input.destandardiseFunction(test.outputSigmoid)));
            writer2.append(",");
            writer2.append(String.valueOf(input.destandardiseFunction(input.trainingData.get(i)[8])));
            writer2.append("\n");
            testError += Math.abs(input.trainingData.get(i)[8] - test.outputSigmoid);
        }
        //test.printPerceptrons();
        writer2.flush();
        writer2.close();

    }

}