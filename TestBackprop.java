import java.lang.Math;
import java.util.*;
import java.io.*;
public class TestBackprop {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Network test = new Network();
        Input input = new Input();

        input.getData();

        // System.out.println(test.randomiser(8));

        int numOfPerceptrons = 5;
        int numOfInputs = 8;
        test.createPerceptrons(numOfPerceptrons, numOfInputs);

        int counter = 0;

        String csvFile = "newtestoutput.csv";
        FileWriter writer = new FileWriter(csvFile);

        //test.printPerceptrons();
        double trainingError = 0;
        System.out.println(Math.round(input.csv.size()*0.6));
        while(counter < 2000) {
            for(int i =0; i<Math.round(input.csv.size()*0.6); i++) { //0.6
                test.forwardPass(input.currentRound(i));
                // if(counter == 2999) {
                //     trainingError += (input.currentRound(i)[8] - test.outputSigmoid)*(input.currentRound(i)[8] - test.outputSigmoid);
                //     //System.out.println("output: "+ Math.abs(input.currentRound(i)[8] - test.outputSigmoid) + "    PREDICTAND: "+ input.currentRound(i)[8]);
                // }
                test.backpass(input.currentRound(i)); 
            }
            counter++;    
        }

        //trainingError = trainingError/Math.round(input.csv.size()*0.6);
        double testError = 0;
        for(int i = (int)Math.round(input.csv.size()*0.6); i<input.csv.size(); i++) {
            test.forwardPass(input.currentRound(i));
            writer.append(String.valueOf(test.outputSigmoid));
            writer.append(",");
            writer.append(String.valueOf(input.currentRound(i)[8]));
            writer.append("\n");
            testError += Math.abs(input.currentRound(i)[8] - test.outputSigmoid);
        }
        //test.printPerceptrons();
        writer.flush();
        writer.close();
        testError = testError/(input.csv.size() - Math.round(input.csv.size()*0.6));
        System.out.println(testError);

    }

}