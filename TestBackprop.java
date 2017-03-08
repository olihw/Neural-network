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
        while(counter < 3000) {
            for(int i =0; i<input.csv.size()*0.6; i++) {
                test.forwardPass(input.currentRound(i));
                if(counter == 2999) {
                    //System.out.println("output: "+ Math.abs(input.currentRound(i)[8] - test.outputSigmoid) + "    PREDICTAND: "+ input.currentRound(i)[8]);
                }
                test.backpass(input.currentRound(i)); 
            }
            counter++;    
        }

        for(int i = (int)Math.ceil(input.csv.size()*0.6); i<input.csv.size(); i++) {
            test.forwardPass(input.currentRound(i));
            writer.append(String.valueOf(test.outputSigmoid));
            writer.append(",");
            writer.append(String.valueOf(input.currentRound(i)[8]));
            writer.append("\n");
        }
        //test.printPerceptrons();
        writer.flush();
        writer.close();
    }

}