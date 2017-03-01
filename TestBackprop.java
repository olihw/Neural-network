import java.lang.Math;
import java.util.*;
import java.io.*;
public class TestBackprop {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Network test = new Network();

        // test.updatePerceptronsBias(1.00, -6.00);
        // test.updateU3Links(1,3,2,4,5,2); 
        // test.updateimport java.io.*;U4Links(1,6,2,5,5,4); 

       
        // // System.out.println(test.u3.bias);
        // // test.printWeights(test.java.util.Random.nextIntu3);
        // // test.printWeights(test.u4);

        // for(int i=0; i<1000000; i++) {
        //     double outputp = test.output;
        //     test.forwardPass();
        //     System.out.println(test.output - outputp+"    "+ test.output);
        //     test.backpass();
        // }

        Input input = new Input();

        input.getData();

        // System.out.println(test.randomiser(8));

        int numOfPerceptrons = 5;
        int numOfInputs = 2;
        test.createPerceptrons(numOfPerceptrons, numOfInputs);

        int counter = 0;

        //test.printPerceptrons();
        while(counter < 3000) {
            for(int i =0; i<input.csv.size(); i++) {
                test.forwardPass(input.currentRound(i));
                if(counter == 2999) {
                    System.out.println("output: "+ Math.abs(input.currentRound(i)[2] - test.outputSigmoid) + "    PREDICTAND: "+ input.currentRound(i)[2]);
                }
                test.backpass(input.currentRound(i)); 
            }
            counter++;    
        }
        test.printPerceptrons();
    }

}